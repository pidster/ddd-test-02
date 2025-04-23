package com.lakesidemutual.claimprocessing.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;
import com.lakesidemutual.claimprocessing.domain.model.PolicyHolder;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {
    List<Claim> findByPolicyHolder(PolicyHolder policyHolder);
    List<Claim> findByStatus(ClaimStatus status);
    List<Claim> findByPolicyNumber(String policyNumber);
}
