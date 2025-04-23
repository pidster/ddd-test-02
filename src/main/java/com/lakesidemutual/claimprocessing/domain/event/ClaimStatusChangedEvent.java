package com.lakesidemutual.claimprocessing.domain.event;

import com.lakesidemutual.claimprocessing.domain.model.Claim;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;

public class ClaimStatusChangedEvent extends ClaimEvent {
    private final ClaimStatus previousStatus;

    public ClaimStatusChangedEvent(Object source, Claim claim, ClaimStatus previousStatus) {
        super(source, claim);
        this.previousStatus = previousStatus;
    }

    public ClaimStatus getPreviousStatus() {
        return previousStatus;
    }
}
