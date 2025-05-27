package com.example.joohyun.controller;

import com.example.joohyun.dto.OrderDTO;
import com.example.joohyun.dto.UserDTO;
import com.example.joohyun.entity.Address;
import com.example.joohyun.service.OrderService;
import com.example.joohyun.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/orderList")
    public String orderList(@SessionAttribute(value = "userEmail", required = false) String userEmail, Model model) {
        if (userEmail == null) {
            return "redirect:/loginPage";
        }
        try {
            UserDTO userDTO = userService.convertToDTO(userService.findByEmail(userEmail));
            model.addAttribute("user", userDTO);
            model.addAttribute("orderList", orderService.getOrderListByUserEmail(userEmail));
            return "orderDetail";
        } catch (IllegalArgumentException e) {
            return "redirect:/loginPage";
        }
    }

    @PostMapping("/order/refund")
    public String refundOrder(@RequestParam Long orderId, HttpSession session, RedirectAttributes redirectAttributes) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/loginPage";
        }

        try {
            OrderDTO refundDetail = orderService.processRefund(orderId, userEmail);
            if (refundDetail != null) {
                redirectAttributes.addFlashAttribute("refundMessage", "환불 요청이 성공적으로 접수되었습니다.");
                redirectAttributes.addFlashAttribute("refundDetail", refundDetail);
            } else {
                redirectAttributes.addFlashAttribute("refundMessage", "환불 요청 처리에 실패했습니다.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("refundMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("refundMessage", "환불 처리 중 오류가 발생했습니다.");
        }

        return "redirect:/refundPage";
    }

    @GetMapping("/refundPage")
    public String refund() {
        return "refundComplete";
    }
}