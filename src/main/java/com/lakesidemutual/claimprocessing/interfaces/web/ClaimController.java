package com.lakesidemutual.claimprocessing.interfaces.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lakesidemutual.claimprocessing.application.dto.ClaimDTO;
import com.lakesidemutual.claimprocessing.application.dto.ClaimStatusUpdateDTO;
import com.lakesidemutual.claimprocessing.application.service.ClaimService;
import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getClaims(
            @RequestParam(required = false) ClaimStatus status,
            @RequestParam(required = false) String policyHolderId) {
        
        if (status != null) {
            return ResponseEntity.ok(claimService.getClaimsByStatus(status));
        }
        
        if (policyHolderId != null) {
            return ResponseEntity.ok(claimService.getClaimsByPolicyHolder(policyHolderId));
        }
        
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable String id) {
        return claimService.getClaimById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody ClaimDTO claimDTO) {
        return claimService.createClaim(claimDTO)
                .map(claim -> ResponseEntity.status(HttpStatus.CREATED).body(claim))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Claim> updateClaimStatus(
            @PathVariable String id,
            @Valid @RequestBody ClaimStatusUpdateDTO statusUpdateDTO) {
        try {
            return claimService.updateClaimStatus(id, statusUpdateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
