package com.lakesidemutual.claimprocessing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Claim {
    
    @Id
    private String id;
    
    @ManyToOne
    private PolicyHolder policyHolder;
    
    private String policyNumber;
    private String description;
    private LocalDateTime claimDate;
    private LocalDateTime incidentDate;
    private BigDecimal claimAmount;
    
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
    
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Required by JPA
    protected Claim() {}
    
    public Claim(PolicyHolder policyHolder, String policyNumber, String description, 
            LocalDateTime incidentDate, BigDecimal claimAmount) {
        this.id = UUID.randomUUID().toString();
        this.policyHolder = policyHolder;
        this.policyNumber = policyNumber;
        this.description = description;
        this.claimDate = LocalDateTime.now();
        this.incidentDate = incidentDate;
        this.claimAmount = claimAmount;
        this.status = ClaimStatus.SUBMITTED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markUnderReview() {
        if (this.status != ClaimStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted claims can be marked under review");
        }
        this.status = ClaimStatus.UNDER_REVIEW;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void approve() {
        if (this.status != ClaimStatus.SUBMITTED && this.status != ClaimStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Cannot approve claim that is not submitted or under review");
        }
        this.status = ClaimStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void reject(String reason) {
        if (this.status != ClaimStatus.SUBMITTED && this.status != ClaimStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Cannot reject claim that is not submitted or under review");
        }
        this.status = ClaimStatus.REJECTED;
        this.rejectionReason = reason;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markAsPaid() {
        if (this.status != ClaimStatus.APPROVED) {
            throw new IllegalStateException("Cannot mark as paid if claim is not approved");
        }
        this.status = ClaimStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    public LocalDateTime getIncidentDate() {
        return incidentDate;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}