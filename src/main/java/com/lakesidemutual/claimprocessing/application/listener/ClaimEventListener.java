package com.lakesidemutual.claimprocessing.application.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.lakesidemutual.claimprocessing.domain.event.ClaimStatusChangedEvent;
import com.lakesidemutual.claimprocessing.domain.event.ClaimSubmittedEvent;

@Component
public class ClaimEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ClaimEventListener.class);

    @Async
    @EventListener
    public void handleClaimSubmitted(ClaimSubmittedEvent event) {
        logger.info("New claim submitted: {}, status: {}, at: {}", 
                event.getClaimId(), event.getStatus(), event.getTimestamp());
        
        // In a real application, we might:
        // 1. Send notification emails to claim processors
        // 2. Update dashboards
        // 3. Trigger initial risk assessment
    }

    @Async
    @EventListener
    public void handleClaimStatusChanged(ClaimStatusChangedEvent event) {
        logger.info("Claim status changed: {} from {} to {}, at: {}", 
                event.getClaimId(), event.getPreviousStatus(), event.getStatus(), event.getTimestamp());
        
        // In a real application, we might:
        // 1. Send notification to policy holder
        // 2. Trigger payment process if approved
        // 3. Update reporting statistics
    }
}
