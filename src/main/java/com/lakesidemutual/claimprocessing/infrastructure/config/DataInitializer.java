package com.lakesidemutual.claimprocessing.infrastructure.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.lakesidemutual.claimprocessing.domain.model.Address;
import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.PolicyHolder;
import com.lakesidemutual.claimprocessing.domain.repository.ClaimRepository;
import com.lakesidemutual.claimprocessing.domain.repository.PolicyHolderRepository;

@Component
@Profile("!test") // Don't run during testing
public class DataInitializer implements CommandLineRunner {

    private final PolicyHolderRepository policyHolderRepository;
    private final ClaimRepository claimRepository;

    @Autowired
    public DataInitializer(PolicyHolderRepository policyHolderRepository, ClaimRepository claimRepository) {
        this.policyHolderRepository = policyHolderRepository;
        this.claimRepository = claimRepository;
    }

    @Override
    public void run(String... args) {
        if (policyHolderRepository.count() > 0) {
            return; // Data already initialized
        }

        // Create sample policy holders
        PolicyHolder john = new PolicyHolder(
            "John", 
            "Doe", 
            "john.doe@example.com", 
            "555-123-4567", 
            new Address("123 Main St", "Springfield", "IL", "62701", "USA")
        );
        
        PolicyHolder jane = new PolicyHolder(
            "Jane", 
            "Smith", 
            "jane.smith@example.com", 
            "555-987-6543", 
            new Address("456 Oak Ave", "Rivertown", "CA", "90210", "USA")
        );
        
        PolicyHolder bob = new PolicyHolder(
            "Bob", 
            "Johnson", 
            "bob.johnson@example.com", 
            "555-456-7890", 
            new Address("789 Pine Rd", "Lakeside", "WA", "98001", "USA")
        );
        
        policyHolderRepository.save(john);
        policyHolderRepository.save(jane);
        policyHolderRepository.save(bob);
        
        // Create sample claims
        Claim claim1 = new Claim(
            john, 
            "POL-12345", 
            "Water damage from burst pipe in kitchen", 
            LocalDateTime.now().minusDays(5), 
            new BigDecimal("2500.00")
        );
        
        Claim claim2 = new Claim(
            jane, 
            "POL-67890", 
            "Windshield crack from road debris", 
            LocalDateTime.now().minusDays(10), 
            new BigDecimal("450.00")
        );
        
        Claim claim3 = new Claim(
            bob, 
            "POL-24680", 
            "Roof damage from fallen tree branch", 
            LocalDateTime.now().minusDays(7), 
            new BigDecimal("4750.00")
        );
        
        // Update statuses of some claims
        claim2.markUnderReview();
        claim3.approve();
        
        claimRepository.save(claim1);
        claimRepository.save(claim2);
        claimRepository.save(claim3);
    }
}
