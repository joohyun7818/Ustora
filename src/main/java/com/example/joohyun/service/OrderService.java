// OrderService.java (예시)
package com.example.joohyun.service;

import com.example.joohyun.dto.OrderDTO;
import com.example.joohyun.dto.OrderItemDTO;
import com.example.joohyun.entity.Cart;
import com.example.joohyun.entity.Order;
import com.example.joohyun.entity.OrderDetail;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.CartRepository;
import com.example.joohyun.repository.OrderDetailRepository;
import com.example.joohyun.repository.OrderRepository;
import com.example.joohyun.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    @Transactional
    public Order createOrder(User user, String orderCurrency) {
        List<Cart> userCarts = cartRepository.findByUser(user);

        if (userCarts.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setCurrencyCode(orderCurrency);

        Order savedOrder = orderRepository.save(newOrder);

        for (Cart cartItem : userCarts) {
            OrderDetail orderDetail = new OrderDetail(
                    savedOrder,
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );
            savedOrder.addOrderDetail(orderDetail);
        }
        orderDetailRepository.saveAll(savedOrder.getOrderDetails());

        cartRepository.deleteAll(userCarts);

        return savedOrder;
    }

    @Transactional
    public List<OrderDTO> getOrderListByUserEmail(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userEmail));

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);

        return orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO processRefund(Long orderId, String userEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다: " + orderId));

        if (!order.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("해당 주문에 대한 권한이 없습니다.");
        }

        OrderDTO orderDTO = OrderDTO.fromEntity(order);
        order.getOrderDetails().forEach(orderDetail -> {
            cartService.addItemCart(userEmail, orderDetail.getProduct().getPid(), orderDetail.getQuantity());
        });
        orderRepository.delete(order);

        return orderDTO;
    }

}