package com.example.joohyun.controller;

import com.example.joohyun.dto.UserDTO;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        model.addAttribute("title", "LoginPage- ");
        return "loginPage";
    }

    @GetMapping("/signupPage")
    public String signipPage(Model model) {
        model.addAttribute("title", "SignupPage- ");
        return "/signupPage";
    }

    @PostMapping("/login")
    public String login(UserDTO userDto, HttpSession session, HttpServletRequest request) {
        User user = userRepository.findById(userDto.getEmail()).orElse(null);
        String referer = request.getHeader("Referer");
        if(user != null && user.getPassword().equals(userDto.getPassword())) {
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            if (referer.contains("loginPage")){
                return "redirect:/?loginSuccess=True";
            }
            if(referer.contains("?")){
                return "redirect:" + referer + "loginSuccess=True";
            }
        }
        return "loginPage";
    }

    @PostMapping("/signup")
    public String signup(UserDTO userDto) {
        User user = userDto.toEntity();
        userRepository.save(user);
        return "redirect:/loginPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
