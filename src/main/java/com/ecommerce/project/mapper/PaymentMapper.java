package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.PaymentRequestDTO;
import com.ecommerce.project.dto.response.PaymentResponseDTO;
import com.ecommerce.project.entity.Payment;

public class PaymentMapper {
    public static PaymentResponseDTO toDto(Payment payment){
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .orderId(payment.getOrder() != null ? payment.getOrder().getId() : null)
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
