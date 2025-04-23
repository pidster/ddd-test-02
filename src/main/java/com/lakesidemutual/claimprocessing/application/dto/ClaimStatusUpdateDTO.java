package com.lakesidemutual.claimprocessing.application.dto;

import jakarta.validation.constraints.NotNull;

import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;

public class ClaimStatusUpdateDTO {
    
    @NotNull(message = "Status is required")
    private ClaimStatus status;
    
    private String reason;

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
