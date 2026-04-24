package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.PaymentRequestDTO;
import com.ecommerce.project.dto.response.PaymentResponseDTO;
import com.ecommerce.project.entity.Payment;

public class PaymentMapper {
    public static PaymentResponseDTO toDto(Payment payment) {
        if (payment == null) return null;
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .orderId(payment.getOrder()!= null ? payment.getOrder().getId() : null)
                .paymentMethod(payment.getPaymentMethod()!=null ? payment.getPaymentMethod().name():null)
                .paymentStatus(payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : null)
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .currency("INR")
                .build();
    }
}
