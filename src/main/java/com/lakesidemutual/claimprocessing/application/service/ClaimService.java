package com.lakesidemutual.claimprocessing.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakesidemutual.claimprocessing.application.dto.ClaimDTO;
import com.lakesidemutual.claimprocessing.application.dto.ClaimStatusUpdateDTO;
import com.lakesidemutual.claimprocessing.domain.event.ClaimStatusChangedEvent;
import com.lakesidemutual.claimprocessing.domain.event.ClaimSubmittedEvent;
import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;
import com.lakesidemutual.claimprocessing.domain.repository.ClaimRepository;
import com.lakesidemutual.claimprocessing.domain.repository.PolicyHolderRepository;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyHolderRepository policyHolderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ClaimService(ClaimRepository claimRepository, 
                         PolicyHolderRepository policyHolderRepository,
                         ApplicationEventPublisher eventPublisher) {
        this.claimRepository = claimRepository;
        this.policyHolderRepository = policyHolderRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(String id) {
        return claimRepository.findById(id);
    }
    
    public List<Claim> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status);
    }
    
    public List<Claim> getClaimsByPolicyHolder(String policyHolderId) {
        return policyHolderRepository.findById(policyHolderId)
                .map(claimRepository::findByPolicyHolder)
                .orElse(List.of());
    }

    @Transactional
    public Optional<Claim> createClaim(ClaimDTO claimDTO) {
        return policyHolderRepository.findById(claimDTO.getPolicyHolderId()).map(policyHolder -> {
            Claim claim = new Claim(
                policyHolder,
                claimDTO.getPolicyNumber(),
                claimDTO.getDescription(),
                claimDTO.getIncidentDate(),
                claimDTO.getClaimAmount()
            );
            
            Claim savedClaim = claimRepository.save(claim);
            
            // Publish domain event
            eventPublisher.publishEvent(new ClaimSubmittedEvent(this, savedClaim));
            
            return savedClaim;
        });
    }

    @Transactional
    public Optional<Claim> updateClaimStatus(String id, ClaimStatusUpdateDTO statusUpdateDTO) {
        return claimRepository.findById(id).map(claim -> {
            ClaimStatus previousStatus = claim.getStatus();
            
            switch (statusUpdateDTO.getStatus()) {
                case UNDER_REVIEW:
                    claim.markUnderReview();
                    break;
                case APPROVED:
                    claim.approve();
                    break;
                case REJECTED:
                    claim.reject(statusUpdateDTO.getReason());
                    break;
                case PAID:
                    claim.markAsPaid();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid status update");
            }
            
            Claim savedClaim = claimRepository.save(claim);
            
            // Publish domain event
            eventPublisher.publishEvent(new ClaimStatusChangedEvent(this, savedClaim, previousStatus));
            
            return savedClaim;
        });
    }
}
