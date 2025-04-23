package com.lakesidemutual.claimprocessing.domain.event;

import com.lakesidemutual.claimprocessing.domain.model.Claim;

public class ClaimSubmittedEvent extends ClaimEvent {
    public ClaimSubmittedEvent(Object source, Claim claim) {
        super(source, claim);
    }
}
