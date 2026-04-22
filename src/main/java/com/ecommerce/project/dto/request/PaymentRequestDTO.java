package com.ecommerce.project.dto.request;

import com.ecommerce.project.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDTO {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
