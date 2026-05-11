package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.PaymentRequestDTO;
import com.ecommerce.project.dto.response.PaymentResponseDTO;
import com.ecommerce.project.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public PaymentResponseDTO makePayment(@RequestBody @Valid PaymentRequestDTO request) {
        return paymentService.makePayment(request);
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isOwner(#orderId, authentication)")
    public PaymentResponseDTO getPayment(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrder(orderId);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
