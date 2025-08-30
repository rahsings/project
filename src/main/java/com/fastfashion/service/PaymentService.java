package com.fastfashion.service;

import com.fastfashion.domain.Order;
import com.fastfashion.domain.OrderStatus;
import com.fastfashion.domain.Payment;
import com.fastfashion.repository.OrderRepository;
import com.fastfashion.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Payment pay(Long orderId, String provider) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setProvider(provider == null ? "SIMULATED" : provider);
        payment.setStatus("SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(Instant.now());
        Payment saved = paymentRepository.save(payment);
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        notificationService.notifyUser(order.getUser().getId(), "order-confirmed", order.getId());
        return saved;
    }

    @Transactional
    public Payment fail(Long orderId, String provider, String reason) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setProvider(provider == null ? "SIMULATED" : provider);
        payment.setStatus("FAILED: " + (reason == null ? "" : reason));
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(Instant.now());
        Payment saved = paymentRepository.save(payment);
        notificationService.notifyUser(order.getUser().getId(), "payment-failed", order.getId());
        return saved;
    }

    @Transactional
    public Payment refund(Long orderId, String provider, String reason) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setProvider(provider == null ? "SIMULATED" : provider);
        payment.setStatus("REFUNDED: " + (reason == null ? "" : reason));
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(Instant.now());
        Payment saved = paymentRepository.save(payment);
        notificationService.notifyUser(order.getUser().getId(), "payment-refunded", order.getId());
        return saved;
    }
}
