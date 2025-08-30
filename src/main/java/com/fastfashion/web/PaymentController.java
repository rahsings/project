package com.fastfashion.web;

import com.fastfashion.domain.Payment;
import com.fastfashion.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Payment> pay(@PathVariable Long orderId, @RequestParam(defaultValue = "SIMULATED") String provider) {
        return ResponseEntity.ok(paymentService.pay(orderId, provider));
    }

    @PostMapping("/{orderId}/fail")
    public ResponseEntity<Payment> fail(@PathVariable Long orderId, @RequestParam(required = false) String provider, @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(paymentService.fail(orderId, provider, reason));
    }

    @PostMapping("/{orderId}/refund")
    public ResponseEntity<Payment> refund(@PathVariable Long orderId, @RequestParam(required = false) String provider, @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(paymentService.refund(orderId, provider, reason));
    }
}

