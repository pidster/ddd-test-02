package com.lakesidemutual.claimprocessing.domain.event;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEvent;

import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;

public abstract class ClaimEvent extends ApplicationEvent {
    private final String claimId;
    private final ClaimStatus status;
    private final LocalDateTime timestamp;

    public ClaimEvent(Object source, Claim claim) {
        super(source);
        this.claimId = claim.getId();
        this.status = claim.getStatus();
        this.timestamp = LocalDateTime.now();
    }

    public String getClaimId() {
        return claimId;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
