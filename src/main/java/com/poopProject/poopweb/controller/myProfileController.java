package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.UserRepository;
import com.poopProject.poopweb.service.PoopService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class myProfileController {
    PoopService poopService;
    UserRepository userRepository;

    @GetMapping("/profile")
    public String showMyProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Long countPoop = poopService.countByUser(user);

        model.addAttribute("user",user);
        model.addAttribute("countPoop", countPoop);

        return "myProfile-page";
    }

    @PostMapping("/profile/upload-profile-image")
    public String uploadProfilePicture(@RequestParam("profilePicture")MultipartFile multipartFile){
        try{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePicture(multipartFile.getBytes());
        userRepository.save(user);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/profile";

    }

}
