package com.example.joohyun.service;

import com.example.joohyun.dto.CartDTO;
import com.example.joohyun.entity.Cart;
import com.example.joohyun.entity.Product;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.CartRepository;
import com.example.joohyun.repository.ProductRepository;
import com.example.joohyun.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart addItemCart(String userEmail, Long productId, int quantity){
        Cart result = null;
        if(userEmail == null || productId == null ){ return result; }
        Cart newCart;
        User user = userRepository.findById(userEmail).orElse(null);
        if(user != null){
            Product product = productRepository.findById(productId).orElse(null);
            Optional<Cart> existing = cartRepository.findByUserAndProduct(user, product);
            if(existing.isPresent()){
                newCart = existing.get();
                int newQuantity = Math.max((newCart.getQuantity() + quantity), 0);
                newCart.setQuantity(newQuantity);
            }else {
                newCart = new Cart(user, product, quantity);
            }
            result = cartRepository.save(newCart);
        }
        return result;
    }

    public void removeItemCart(Long cartId){
        Cart target = cartRepository.findById(cartId).orElse(null);
        if(target != null){
            cartRepository.delete(target);
        }
    }

    public List<Cart> userCartList(String userEmail){
        if(userEmail == null){
            return null;
        }
        User user = userRepository.findById(userEmail).orElse(null);
        List<Cart> myCartList = cartRepository.findByUser(user);
        return myCartList;
    }

    public int cartSubtotal(String userEmail){
        if(userEmail == null){
            return 0;
        }
        List<Cart> cartList = userCartList(userEmail);
        int subtotal = 0;
        for(Cart cart : cartList){
            subtotal += cart.getTotalPrice();
        }
        return subtotal;
    }

    public int cartCount(String userEmail){
        if(userEmail == null){
            return 0;
        }
        List<Cart> cartList = userCartList(userEmail);
        int count = 0;
        for(Cart cart : userCartList(userEmail)){
            count += cart.getQuantity() > 0 ? cart.getQuantity() : 0;
        }
        return count;
    }

}
