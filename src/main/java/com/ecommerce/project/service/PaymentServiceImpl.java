package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.PaymentRequestDTO;
import com.ecommerce.project.dto.response.PaymentResponseDTO;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderStatus;
import com.ecommerce.project.entity.Payment;
import com.ecommerce.project.entity.PaymentStatus;
import com.ecommerce.project.entity.PaymentMethod;
import com.ecommerce.project.exception.BadRequestException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.PaymentMapper;
import com.ecommerce.project.repository.OrderRepository;
import com.ecommerce.project.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));


        paymentRepository.findByOrderId(order.getId())
                .ifPresent(p -> {
                    throw new BadRequestException("Payment already exists for this order");
                });


        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Cannot pay for cancelled order");
        }

        PaymentStatus status;
        if (request.getPaymentMethod() == PaymentMethod.COD) {
            status = PaymentStatus.PENDING;
        } else {
            status = PaymentStatus.SUCCESS; // simulate success
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(status)
                .amount(order.getTotalPrice())
                .transactionId(UUID.randomUUID().toString())
                .paymentGateway("RAZORPAY")
                .paymentDate(LocalDateTime.now())
                .build();

        if (status == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.PAID);
        }

        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toDto(saved);
    }

    @Override
    public PaymentResponseDTO getPaymentByOrder(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return PaymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }
}
