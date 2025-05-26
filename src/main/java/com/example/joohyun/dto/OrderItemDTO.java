package com.example.joohyun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderDetailId;
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
    private int totalPrice;

    public static OrderItemDTO fromEntity(com.example.joohyun.entity.OrderDetail orderDetail) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderDetailId(orderDetail.getId());
        dto.setProductId(orderDetail.getProduct().getPid());
        dto.setProductName(orderDetail.getProduct().getPname());
        dto.setPrice(orderDetail.getPriceAtOrder());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setTotalPrice(orderDetail.getPriceAtOrder() * (orderDetail.getQuantity()));
        return dto;
    }
}