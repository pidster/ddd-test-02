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

import com.lakesidemutual.claimprocessing.application.dto.PolicyHolderDTO;
import com.lakesidemutual.claimprocessing.application.service.PolicyHolderService;
import com.lakesidemutual.claimprocessing.domain.model.PolicyHolder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/policyholders")
public class PolicyHolderController {

    private final PolicyHolderService policyHolderService;

    @Autowired
    public PolicyHolderController(PolicyHolderService policyHolderService) {
        this.policyHolderService = policyHolderService;
    }

    @GetMapping
    public ResponseEntity<List<PolicyHolder>> getAllPolicyHolders(
            @RequestParam(required = false) String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            return ResponseEntity.ok(policyHolderService.searchPolicyHoldersByLastName(lastName));
        }
        return ResponseEntity.ok(policyHolderService.getAllPolicyHolders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyHolder> getPolicyHolderById(@PathVariable String id) {
        return policyHolderService.getPolicyHolderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PolicyHolder> createPolicyHolder(@Valid @RequestBody PolicyHolderDTO policyHolderDTO) {
        PolicyHolder createdPolicyHolder = policyHolderService.createPolicyHolder(policyHolderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicyHolder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyHolder> updatePolicyHolder(
            @PathVariable String id,
            @Valid @RequestBody PolicyHolderDTO policyHolderDTO) {
        return policyHolderService.updatePolicyHolder(id, policyHolderDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
