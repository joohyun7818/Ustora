package com.example.joohyun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.joohyun.entity.Address;
import com.example.joohyun.entity.User;
import com.example.joohyun.service.AddressService;
import com.example.joohyun.service.UserService;

import lombok.RequiredArgsConstructor;

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
                           @RequestParam(name = "redirectUrl", required = false, defaultValue = "/orderList") String redirectUrl) {
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
    public String setDefaultAddress(@PathVariable(name = "addressId") Long addressId, 
                                  @SessionAttribute(value = "userEmail", required = false) String userEmail,
                                  @RequestParam(name = "redirectUrl", required = false, defaultValue = "/orderList") String redirectUrl) {
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
    public String deleteAddress(@PathVariable(name = "addressId") Long addressId, 
                              @SessionAttribute(value = "userEmail", required = false) String userEmail,
                              @RequestParam(name = "redirectUrl", required = false, defaultValue = "/orderList") String redirectUrl,
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