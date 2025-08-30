package com.fastfashion;

import com.fastfashion.domain.Order;
import com.fastfashion.domain.User;
import com.fastfashion.repository.ProductRepository;
import com.fastfashion.repository.ShopRepository;
import com.fastfashion.repository.UserRepository;
import com.fastfashion.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderFlowTest {
    @Autowired OrderService orderService;
    @Autowired UserRepository userRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired ProductRepository productRepository;

    @Test
    void placeAndCancelOrderWorks() {
        User u = userRepository.findByEmail("demo@fastfashion.local");
        Long shopId = shopRepository.findAll().get(0).getId();
        Long pid = productRepository.findAll().get(0).getId();
        Order order = orderService.placeOrder(u, shopId, "Koramangala", List.of(pid), List.of(1));
        assertThat(order.getId()).isNotNull();
        assertThat(order.getItems()).hasSize(1);

        Order cancelled = orderService.cancelOrder(order.getId());
        assertThat(cancelled.getStatus().name()).isEqualTo("CANCELLED");
    }
}
