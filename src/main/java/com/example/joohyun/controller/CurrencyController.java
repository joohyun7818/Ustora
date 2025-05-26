package com.example.joohyun.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CurrencyController {
    @GetMapping("/currency/{value}")
    public String currency(@PathVariable String value, HttpSession session, HttpServletRequest request) {
        session.setAttribute("currency", value);
        return "redirect:/";
    }
}
