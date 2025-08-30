package com.fastfashion.web.dto;

import java.util.List;

public class OrderDtos {
    public static class OrderItemReq { public Long productId; public int quantity; }
    public static class PlaceOrderReq { public Long shopId; public String deliveryAddress; public List<OrderItemReq> items; }
}
