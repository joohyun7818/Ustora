package com.example.joohyun.controller;

import com.example.joohyun.entity.Address;
import com.example.joohyun.entity.User;
import com.example.joohyun.service.AddressService;
import com.example.joohyun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address, 
                           @SessionAttribute(value = "userEmail", required = false) String userEmail,
                           @RequestParam(required = false, defaultValue = "/orderList") String redirectUrl) {
        if (userEmail == null) {
            return "redirect:/loginPage";
        }
        try {
            User user = userService.findByEmail(userEmail);
            address.setUser(user);
            addressService.saveAddress(address);
            return "redirect:" + redirectUrl;
        } catch (IllegalArgumentException e) {
            return "redirect:/loginPage";
        }
    }

    @PostMapping("/set-default/{addressId}")
    public String setDefaultAddress(@PathVariable Long addressId, 
                                  @SessionAttribute(value = "userEmail", required = false) String userEmail,
                                  @RequestParam(required = false, defaultValue = "/orderList") String redirectUrl) {
        if (userEmail == null) {
            return "redirect:/loginPage";
        }
        try {
            addressService.setDefaultAddress(addressId, userEmail);
            return "redirect:" + redirectUrl;
        } catch (IllegalArgumentException e) {
            return "redirect:/loginPage";
        }
    }

    @PostMapping("/delete/{addressId}")
    public String deleteAddress(@PathVariable Long addressId, 
                              @SessionAttribute(value = "userEmail", required = false) String userEmail,
                              @RequestParam(required = false, defaultValue = "/orderList") String redirectUrl,
                                RedirectAttributes redirectAttributes) {
        if (userEmail == null) {
            return "redirect:/loginPage";
        }
        try {
            boolean success = addressService.deleteAddress(addressId);
            if (!success) {
                redirectAttributes.addFlashAttribute("delDefault", "Cannot delete default address");
            }
            return "redirect:" + redirectUrl;
        } catch (IllegalArgumentException e) {
            return "redirect:/loginPage";
        }
    }
} 