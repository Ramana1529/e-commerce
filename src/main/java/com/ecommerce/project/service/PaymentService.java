package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.PaymentRequestDTO;
import com.ecommerce.project.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO makePayment(PaymentRequestDTO request);
    PaymentResponseDTO getPaymentByOrder(Long orderId);

    List<PaymentResponseDTO> getAllPayments();
}
