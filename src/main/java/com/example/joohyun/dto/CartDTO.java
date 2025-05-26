package com.example.joohyun.dto;

import com.example.joohyun.entity.Cart;
import com.example.joohyun.entity.Product;
import com.example.joohyun.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;

    private User user;

    private Product product;

    private int quantity;

    public Cart toEntity(){
        return new Cart(user, product, quantity);
    }
}
