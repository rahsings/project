package com.fastfashion.service;

import com.fastfashion.domain.*;
import com.fastfashion.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, ShopRepository shopRepository, InventoryService inventoryService, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Order placeOrder(User user, Long shopId, String address, List<Long> productIds, List<Integer> quantities) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        Order order = new Order();
        order.setUser(user);
        order.setShop(shop);
        order.setDeliveryAddress(address);
        order.setStatus(OrderStatus.PLACED);
        List<OrderItem> items = new ArrayList<>();
        int total = 0;
        for (int i=0;i<productIds.size();i++) {
            Long pid = productIds.get(i);
            int qty = quantities.get(i);
            Product p = productRepository.findById(pid).orElseThrow();
            if (!p.getShop().getId().equals(shopId)) throw new IllegalArgumentException("Product not in shop");
            inventoryService.reserve(pid, qty);
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(p);
            item.setQuantity(qty);
            item.setPriceCents(p.getPriceCents());
            items.add(item);
            total += p.getPriceCents() * qty;
        }
        order.setTotalCents(total);
        order.setItems(items);
        Order saved = orderRepository.save(order);
        for (OrderItem it : items) orderItemRepository.save(it);
        notificationService.notifyUser(user.getId(), "order-placed", saved.getId());
        return saved;
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) return order;
        for (OrderItem it : order.getItems()) {
            inventoryService.release(it.getProduct().getId(), it.getQuantity());
        }
        order.setStatus(OrderStatus.CANCELLED);
        Order saved = orderRepository.save(order);
        notificationService.notifyUser(order.getUser().getId(), "order-cancelled", saved.getId());
        return saved;
    }
}
