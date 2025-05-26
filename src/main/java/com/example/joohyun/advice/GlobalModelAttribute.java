package com.example.joohyun.advice;

import com.example.joohyun.entity.Product;
import com.example.joohyun.service.CartService;
import com.example.joohyun.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttribute {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void addUserNameToModel(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        String userEmail = (String) session.getAttribute("userEmail");
        if (userName != null) {
            model.addAttribute("userName", userName);
            model.addAttribute("userEmail", userEmail);
        }
    }

    @ModelAttribute
    public void addProductList(HttpSession session, Model model) {
        List<Product> top3List = (List<Product>) session.getAttribute("top3List");
        if (top3List != null) {
            model.addAttribute("top3List", top3List);
        } else if (top3List == null || top3List.isEmpty()) {
            top3List = productService.top3expensiveList();
            session.setAttribute("top3List", top3List);
            model.addAttribute("top3List", top3List);
        }

        List<Product> top4List = (List<Product>) session.getAttribute("top4List");
        if (top4List != null) {
            model.addAttribute("top4List", top4List);
        } else if (top4List == null || top4List.isEmpty()) {
            top4List = productService.top4productList();
            session.setAttribute("top4List", top4List);
            model.addAttribute("top4List", top4List);
        }

        List<Product> top5List = (List<Product>) session.getAttribute("top5List");
        if (top5List != null) {
            model.addAttribute("top5List", top5List);
        } else if (top5List == null || top5List.isEmpty()) {
            top5List = productService.top5recentList();
            session.setAttribute("top5List", top5List);
            model.addAttribute("top5List", top5List);
        }

        List<Product> top6List = (List<Product>) session.getAttribute("top6List");
        if (top6List != null) {
            model.addAttribute("top6List", top6List);
        } else if (top6List == null || top6List.isEmpty()) {
            top6List = productService.top6expensiveList();
            session.setAttribute("top6List", top6List);
            model.addAttribute("top6List", top6List);
        }

        List<Product> interestedProducts = new ArrayList<>();
        Product product1 = ((List<Product>) session.getAttribute("top5List")).get(0);
        Product product2 = ((List<Product>) session.getAttribute("top5List")).get(1);
        interestedProducts.add(product1);
        interestedProducts.add(product2);
        model.addAttribute("interestedProducts", interestedProducts);
    }

    @ModelAttribute
    public void addCurrencyAndCurrencySymbolToModel(HttpSession session, Model model) {
        String currency = (String) session.getAttribute("currency");
        if (currency == null) {
            currency = "KRW";
        }
        model.addAttribute("currency", currency);

        String currencySymbol = switch (currency) {
            case "KRW" -> "￦";
            case "USD" -> "$";
            case "INR" -> "₹";
            case "GBP" -> "£";
            default -> "?";
        };
        model.addAttribute("currencySymbol", currencySymbol);
    }

    @ModelAttribute
    public void addSubtotalAndCountToModel(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("cartList", cartService.userCartList(userEmail));
        model.addAttribute("cartSubtotal", cartService.cartSubtotal(userEmail));
        model.addAttribute("cartCount", cartService.cartCount(userEmail));
    }

}