package com.example.joohyun.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.joohyun.entity.Cart;
import com.example.joohyun.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart/add/{pid}")
    public String addToCart(@PathVariable(name = "pid") Long pid, Model model, HttpSession session, HttpServletRequest request) {
        return cartQuantity(pid, 1, session, request);

    }

    @GetMapping("/cart/add/{pid}/quantity")
    public String changeCartQuantity(@PathVariable(name = "pid") Long pid, Model model, HttpSession session, @RequestParam(name = "quantity") String quantity, HttpServletRequest request) {
        int num = 1;
        if (quantity != null) {
            num = Integer.parseInt(quantity);
        }
        return cartQuantity(pid, num, session, request);
    }

    @GetMapping("/cart/add/{pid}/{quantity}")
    public String cartQuantity(@PathVariable(name = "pid") Long pid, @PathVariable(name = "quantity") int quantity, HttpSession session, HttpServletRequest request) {
        String user = (String) session.getAttribute("userEmail");
        Cart addedCartItem = cartService.addItemCart(user, pid, quantity);

        String referer = request.getHeader("Referer");

        if (referer == null || referer.isEmpty()) {
            referer = "/";
        }

        String redirectUrl;
        if (addedCartItem != null && addedCartItem.getProduct() != null) {

            String encodedItemName = URLEncoder.encode(addedCartItem.getProduct().getPname(), StandardCharsets.UTF_8);
            String encodedBrand = URLEncoder.encode(addedCartItem.getProduct().getBrand(), StandardCharsets.UTF_8);
            String encodedCategory1 = URLEncoder.encode(addedCartItem.getProduct().getCategory1(), StandardCharsets.UTF_8);
            String encodedCategory2 = URLEncoder.encode(addedCartItem.getProduct().getCategory2(), StandardCharsets.UTF_8);

            redirectUrl = referer + (referer.contains("?") ? "&" : "?") + "cartStatus=added" +
                    "&addedPid=" + addedCartItem.getProduct().getPid() +
                    "&addedQuantity=" + addedCartItem.getQuantity() +
                    "&addedPrice=" + addedCartItem.getProduct().getPrice() +
                    "&addedItemName=" + encodedItemName +
                    "&addedBrand=" + encodedBrand +
                    "&addedCategory1=" + encodedCategory1 +
                    "&addedCategory2=" + encodedCategory2;
        } else {
            redirectUrl = "/loginPage" + "?cartStatus=failed";
        }

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/cart/del/{cid}")
    public String delCart(@PathVariable(name = "cid") Long cid, HttpSession session){
        cartService.removeItemCart(cid);
        return "redirect:/cart";
    }
}
