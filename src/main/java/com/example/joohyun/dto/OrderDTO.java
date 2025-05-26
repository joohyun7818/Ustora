package com.example.joohyun.dto;

import com.example.joohyun.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

// Order 엔티티의 정보를 담는 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String orderDate;
    private String userName;
    private int totalOrderAmount;
    private String currencyCode;

    private List<OrderItemDTO> orderCartList;

    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setUserName(order.getUser().getEmail());
        dto.setCurrencyCode(order.getCurrencyCode());

        List<OrderItemDTO> orderItems = order.getOrderDetails().stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setOrderCartList(orderItems);

        int totalAmount = orderItems.stream()
                .mapToInt(OrderItemDTO::getTotalPrice)
                .sum();

        dto.setTotalOrderAmount(totalAmount);

        return dto;
    }
}