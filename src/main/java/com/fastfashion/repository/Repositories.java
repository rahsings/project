package com.fastfashion.repository;

import com.fastfashion.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByCity(String city);
}

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopId(Long shopId);
}

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductId(Long productId);
}

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}

public interface PaymentRepository extends JpaRepository<Payment, Long> {}

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {}
