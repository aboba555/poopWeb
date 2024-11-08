package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.PoopRepository;
import com.poopProject.poopweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainPageController {
    private UserRepository userRepository;

    private PoopRepository poopRepository;


    @GetMapping("/home")
    public String showHomePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Poop lastPoop = poopRepository.findTopByUserOrderByCreatedAtDesc(user);


        String base64Image = null;
        if (user.getProfilePicture() != null) {
            base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
        }
        model.addAttribute("profilePicture",base64Image);

        if (lastPoop != null) {
            LocalDateTime lastPoopTime = lastPoop.getCreatedAt();
            Duration duration = Duration.between(lastPoopTime, LocalDateTime.now());
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            String timeAgo = hours + " hours " + minutes + " minutes ago";
            model.addAttribute("lastPoopDuration", timeAgo);
        } else {
            model.addAttribute("lastPoopDuration", "No records found");
        }

        return "home";
    }
}
