package com.example.joohyun.entity;

import com.example.joohyun.dto.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false) // 외래키: order_id
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pid", nullable = false) // 외래키: product_pid
    private Product product;

    @Column(nullable = false)
    private int quantity; // 주문 당시의 상품 수량

    @Column(nullable = false)
    private int priceAtOrder; // 주문 당시의 상품 단가

    public OrderDetail(Order order, Product product, int quantity, int priceAtOrder) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }
}