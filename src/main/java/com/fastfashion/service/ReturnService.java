package com.fastfashion.service;

import com.fastfashion.domain.Order;
import com.fastfashion.domain.OrderStatus;
import com.fastfashion.domain.ReturnRequest;
import com.fastfashion.repository.OrderRepository;
import com.fastfashion.repository.ReturnRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReturnService {
    private final ReturnRequestRepository returnRequestRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public ReturnService(ReturnRequestRepository returnRequestRepository, OrderRepository orderRepository, NotificationService notificationService) {
        this.returnRequestRepository = returnRequestRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public ReturnRequest requestReturn(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.RETURN_REQUESTED);
        orderRepository.save(order);
        ReturnRequest rr = new ReturnRequest();
        rr.setOrder(order);
        rr.setReason(reason);
        rr.setStatus("REQUESTED");
        ReturnRequest saved = returnRequestRepository.save(rr);
        notificationService.notifyUser(order.getUser().getId(), "return-requested", order.getId());
        return saved;
    }

    @Transactional
    public ReturnRequest approve(Long id) {
        ReturnRequest rr = returnRequestRepository.findById(id).orElseThrow();
        rr.setStatus("APPROVED");
        notificationService.notifyUser(rr.getOrder().getUser().getId(), "return-approved", rr.getOrder().getId());
        return returnRequestRepository.save(rr);
    }

    @Transactional
    public ReturnRequest reject(Long id, String reason) {
        ReturnRequest rr = returnRequestRepository.findById(id).orElseThrow();
        rr.setStatus("REJECTED: " + (reason == null ? "" : reason));
        notificationService.notifyUser(rr.getOrder().getUser().getId(), "return-rejected", rr.getOrder().getId());
        return returnRequestRepository.save(rr);
    }

    @Transactional
    public ReturnRequest markPickedUp(Long id) {
        ReturnRequest rr = returnRequestRepository.findById(id).orElseThrow();
        rr.setStatus("PICKED_UP");
        Order order = rr.getOrder();
        order.setStatus(OrderStatus.RETURNED);
        orderRepository.save(order);
        notificationService.notifyUser(order.getUser().getId(), "return-picked-up", order.getId());
        return returnRequestRepository.save(rr);
    }
}
