package com.lakesidemutual.claimprocessing.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Claim claim;
    
    private String paymentId;
    private BigDecimal amount;
    private LocalDateTime scheduledDate;
    private LocalDateTime performedDate;
    private boolean isPerformed;
    private String accountNumber;
    private String bankCode;
}