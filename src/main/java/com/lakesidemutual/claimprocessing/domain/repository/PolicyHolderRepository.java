package com.lakesidemutual.claimprocessing.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lakesidemutual.claimprocessing.domain.model.PolicyHolder;

@Repository
public interface PolicyHolderRepository extends JpaRepository<PolicyHolder, String> {
    Optional<PolicyHolder> findByEmail(String email);
    List<PolicyHolder> findByLastNameContainingIgnoreCase(String lastName);
}
