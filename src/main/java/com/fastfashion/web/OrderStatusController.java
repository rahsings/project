package com.fastfashion.web;

import com.fastfashion.domain.Order;
import com.fastfashion.domain.OrderStatus;
import com.fastfashion.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderStatusController {
    private final OrderService orderService;

    public OrderStatusController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{id}/out-for-delivery")
    public ResponseEntity<Order> outForDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.OUT_FOR_DELIVERY));
    }

    @PostMapping("/{id}/delivered")
    public ResponseEntity<Order> delivered(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.DELIVERED));
    }
}
