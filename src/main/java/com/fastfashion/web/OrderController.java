package com.fastfashion.web;

import com.fastfashion.domain.Order;
import com.fastfashion.domain.User;
import com.fastfashion.repository.UserRepository;
import com.fastfashion.security.JwtService;
import com.fastfashion.service.OrderService;
import com.fastfashion.web.dto.OrderDtos.OrderItemReq;
import com.fastfashion.web.dto.OrderDtos.PlaceOrderReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Order> place(@AuthenticationPrincipal UserDetails principal, @RequestBody PlaceOrderReq req) {
        User u = userRepository.findByEmail(principal.getUsername());
        List<Long> pids = req.items.stream().map(i -> i.productId).toList();
        List<Integer> qtys = req.items.stream().map(i -> i.quantity).toList();
        Order order = orderService.placeOrder(u, req.shopId, req.deliveryAddress, pids, qtys);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Order> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
