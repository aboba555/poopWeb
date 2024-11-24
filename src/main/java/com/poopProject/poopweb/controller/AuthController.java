package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("user")User user) {
        return "auth_form";
    }


    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user")User user) {
        return "auth_form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        if (user.getPassword().length() >= 6){
            try {
                userService.addUser(user);
            }catch (DataIntegrityViolationException e){
                return "redirect:/login?unknownEmail";
            }
            return "redirect:/login";
        }else {
            return "redirect:/login?passwordIsShort";
        }
    }
}