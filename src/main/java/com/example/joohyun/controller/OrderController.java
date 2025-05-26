package com.example.joohyun.controller;

import com.example.joohyun.dto.OrderDTO;
import com.example.joohyun.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orderList")
    public String getOrderList(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/loginPage";
        }
        try {
            List<OrderDTO> orderList = orderService.getOrderListByUserEmail(userEmail);
            model.addAttribute("orderList", orderList);
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