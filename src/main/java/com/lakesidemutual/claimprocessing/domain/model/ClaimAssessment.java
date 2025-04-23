package com.lakesidemutual.claimprocessing.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Claim claim;
    
    private LocalDateTime assessmentDate;
    private boolean insuranceCoverageConfirmed;
    private boolean documentationComplete;
    private String assessorId;
    private String notes;
}