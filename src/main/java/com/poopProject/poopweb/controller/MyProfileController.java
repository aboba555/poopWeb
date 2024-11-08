package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.UserRepository;
import com.poopProject.poopweb.service.PoopService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class MyProfileController {

    PoopService poopService;
    UserRepository userRepository;

    @PostMapping("/profile/upload-profile-image")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

            if (!file.isEmpty()) {
                user.setProfilePicture(file.getBytes());
                userRepository.save(user);
                System.out.println("Profile picture uploaded successfully for user: " + username);
            } else {
                System.out.println("Failed to upload profile picture: The file is empty.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/home/profile";
    }


    @GetMapping("/profile")
    public String showMyProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Long countPoop = poopService.countByUser(user);

        String base64Image = null;
        if (user.getProfilePicture() != null) {
            base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
        }

        model.addAttribute("user", user);
        model.addAttribute("countPoop", countPoop);
        model.addAttribute("profilePicture", base64Image);

        return "my_profile_page";
    }


}
