package com.example.joohyun.controller;

import com.example.joohyun.dto.AddressDTO;
import com.example.joohyun.dto.UserDTO;
import com.example.joohyun.entity.*;
import com.example.joohyun.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;

    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("userEmail") != null;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "");
        return "index";
    }

    @GetMapping("/shop")
    public String shop(
            Model model,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "pid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        model.addAttribute("title", "Shop - ");

        Page<Product> productPage = productService.getAllProducts(pageNum, pageSize, sortBy, sortDir);

        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent());

        model.addAttribute("currentPage", productPage.getNumber() + 1);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        List<PageInfo> pageNumbers = new ArrayList<>();
        for (int i = 0; i < productPage.getTotalPages(); i++) {
            pageNumbers.add(new PageInfo(i, i == productPage.getNumber()));
        }
        model.addAttribute("pageNumbers", pageNumbers);

        model.addAttribute("nextPageNum", productPage.getNumber() + 1);
        model.addAttribute("previousPageNum", productPage.getNumber() - 1);

        return "shop";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/loginPage";
        }
        model.addAttribute("title", "Checkout- ");

        String userEmail = (String) session.getAttribute("userEmail");
        List<Cart> userCart = cartService.userCartList(userEmail);
        model.addAttribute("cartList", userCart);
        int cartSubtotal = cartService.cartSubtotal(userEmail);
        model.addAttribute("cartSubtotal", cartSubtotal);

        UserDTO userDTO = userService.convertToDTO(userService.findByEmail(userEmail));
        model.addAttribute("user", userDTO);
        model.addAttribute("orderList", orderService.getOrderListByUserEmail(userEmail));

        AddressDTO userDefaultAddress = addressService.getDefaultAddress(userEmail);
        if (userDefaultAddress != null) {
            model.addAttribute("userDefaultAddress", userDefaultAddress);
        }else{
            model.addAttribute("userDefaultAddress", false);
        }

        return "checkout";
    }

    @GetMapping("/checkoutPage")
    public String checkoutPage(Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/loginPage";
        }

        return "redirect:/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/loginPage";
        }

        int subtotal = (int) model.getAttribute("cartSubtotal");
        if (subtotal == 0) {
            model.addAttribute("emptyCart", "yes");
            return "cart";
        }

        AddressDTO userDefaultAddress = addressService.getDefaultAddress((String) session.getAttribute("userEmail"));
        if (userDefaultAddress == null) {
            redirectAttributes.addFlashAttribute("noDefaultAddress", "Please set your default address.");
            return "redirect:/checkout";
        }
        
        return "paymentPage";
    }

    @PostMapping("/payment")
    public String payment(Model model, HttpSession session) {
        session.setAttribute("orderComplete", "complete");
        return "redirect:/thankyou";
    }

    @GetMapping("/thankyou")
    public String thankyou(Model model, HttpSession session) {
        String orderComplete = (String) session.getAttribute("orderComplete");
        if (orderComplete == null || orderComplete.isEmpty()) {
            return "redirect:/checkout";
        }
        session.removeAttribute("orderComplete");

        if (!isUserLoggedIn(session)) {
            return "redirect:/loginPage";
        }

        String userEmail = (String) session.getAttribute("userEmail");
        String orderCurrency = (String) model.getAttribute("currency");
        Order order = orderService.createOrder(userService.loggedinUser(userEmail), orderCurrency);
        AddressDTO addressAtOrder = addressService.getDefaultAddress(userEmail);
        order.setShippingAddress(addressAtOrder);
        model.addAttribute("order", order);
        return "thankyou";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        model.addAttribute("title", "Cart- ");
        if (!isUserLoggedIn(session)) {
            return "redirect:/loginPage";
        }
        return "cart";
    }

    @GetMapping("/single-product")
    public String singleproduct(Model model, HttpSession session) {
        Product product = productService.findRandomByCount(1).getFirst();
        model.addAttribute("product", product);
        model.addAttribute("title", "Single Product- ");
        return "single-product";
    }

    @GetMapping("/single-product/{productId}")
    public String singleProduct(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("title", product.getPname() + " - ");
            model.addAttribute("product", product);
            return "single-product";
        } else {
            return "error/404";
        }
    }


    public static class PageInfo {
        private int pageNumber; // 0-based
        private boolean active;

        public PageInfo(int pageNumber, boolean active) {
            this.pageNumber = pageNumber;
            this.active = active;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public boolean isActive() {
            return active;
        }

        public int getDisplayPageNumber() {
            return pageNumber + 1;
        }
    }
}
