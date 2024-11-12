package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.ResetPassword;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.UserRepository;
import com.poopProject.poopweb.service.EmailService;
import com.poopProject.poopweb.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ForgotPasswordController {

    EmailService emailService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    ResetPasswordService resetPasswordService;


    @GetMapping("/forgot-password")
    public String showForgotPage() {
        return "forgot_password";
    }

    @PostMapping("/reset-password")
    public String processForgetPassword(@RequestParam("email") String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = resetPasswordService.createToken(user);
            String resetLink = "https://poopweb-production.up.railway.app/reset-password?token=" + token;
            emailService.sendResetPasswordEmail(email, resetLink);
            System.out.println("we send you email to: " + email);
        }
        return "redirect:/login";
    }


    @GetMapping("/reset-password")
    public String showNewPasswordPage(@RequestParam("token") String token, Model model) {
        Optional<ResetPassword> resetTokenOpt = resetPasswordService.findToken(token);
        if (resetTokenOpt.isPresent() && !resetPasswordService.isExpired(resetTokenOpt.get())) {
            model.addAttribute("token", token);
            return "new_password";
        } else {
            return "redirect:/forgot-password?error=invalidToken";
        }
    }



    @PostMapping("/change-password")
    public String changePassword(@RequestParam("token") String token,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("email") String email) {
        Optional<ResetPassword> resetTokenOpt = resetPasswordService.findToken(token);
        if (resetTokenOpt.isPresent() && !resetPasswordService.isExpired(resetTokenOpt.get())) {
            ResetPassword resetToken = resetTokenOpt.get();
            User user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            resetPasswordService.deleteToken(resetToken);

            System.out.println("Пароль успешно обновлен для пользователя: " + email);
            return "redirect:/login?passwordResetSuccess";
        } else {
            System.out.println("Некорректный токен или email.");
            return "redirect:/forgot-password?error=invalidToken";
        }
    }
}


