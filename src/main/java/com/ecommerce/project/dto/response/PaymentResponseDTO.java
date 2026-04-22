package com.ecommerce.project.dto.response;

import com.ecommerce.project.entity.PaymentMethod;
import com.ecommerce.project.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String transactionId;
}
