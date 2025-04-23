package com.lakesidemutual.claimprocessing.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakesidemutual.claimprocessing.application.dto.PolicyHolderDTO;
import com.lakesidemutual.claimprocessing.domain.model.Address;
import com.lakesidemutual.claimprocessing.domain.model.PolicyHolder;
import com.lakesidemutual.claimprocessing.domain.repository.PolicyHolderRepository;

@Service
public class PolicyHolderService {

    private final PolicyHolderRepository policyHolderRepository;

    @Autowired
    public PolicyHolderService(PolicyHolderRepository policyHolderRepository) {
        this.policyHolderRepository = policyHolderRepository;
    }

    public List<PolicyHolder> getAllPolicyHolders() {
        return policyHolderRepository.findAll();
    }

    public Optional<PolicyHolder> getPolicyHolderById(String id) {
        return policyHolderRepository.findById(id);
    }
    
    public List<PolicyHolder> searchPolicyHoldersByLastName(String lastName) {
        return policyHolderRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Transactional
    public PolicyHolder createPolicyHolder(PolicyHolderDTO policyHolderDTO) {
        Address address = new Address(
            policyHolderDTO.getStreet(),
            policyHolderDTO.getCity(),
            policyHolderDTO.getState(),
            policyHolderDTO.getZipCode(),
            policyHolderDTO.getCountry()
        );
        
        PolicyHolder policyHolder = new PolicyHolder(
            policyHolderDTO.getFirstName(),
            policyHolderDTO.getLastName(),
            policyHolderDTO.getEmail(),
            policyHolderDTO.getPhoneNumber(),
            address
        );
        
        return policyHolderRepository.save(policyHolder);
    }

    @Transactional
    public Optional<PolicyHolder> updatePolicyHolder(String id, PolicyHolderDTO policyHolderDTO) {
        return policyHolderRepository.findById(id).map(policyHolder -> {
            Address address = new Address(
                policyHolderDTO.getStreet(),
                policyHolderDTO.getCity(),
                policyHolderDTO.getState(),
                policyHolderDTO.getZipCode(),
                policyHolderDTO.getCountry()
            );
            
            policyHolder.updateContactInfo(policyHolderDTO.getEmail(), policyHolderDTO.getPhoneNumber());
            policyHolder.updateAddress(address);
            
            return policyHolderRepository.save(policyHolder);
        });
    }
}
