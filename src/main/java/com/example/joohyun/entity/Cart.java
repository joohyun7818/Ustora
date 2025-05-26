package com.example.joohyun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product;

    @Column
    private int quantity;

    @Column
    private int totalPrice;

    public Cart(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;

    }
    private void calculateTotalPrice() {
        if(this.product != null){
            this.totalPrice = this.product.getPrice() * this.quantity;
        }else{
            this.totalPrice = 0;
        }
    }

    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        calculateTotalPrice();
    }
}
