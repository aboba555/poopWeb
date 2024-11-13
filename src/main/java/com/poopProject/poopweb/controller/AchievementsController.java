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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class AchievementsController {

    UserRepository userRepository;
    PoopService poopService;

    @GetMapping("/achievements")
    public String showAchievementsStats(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("User not found"));
        Long countPoop = poopService.countByUser(user);

        model.addAttribute("countPoop", countPoop);
        model.addAttribute("achievements", poopService.getAchievements(user));
        return "achievements_stats";
    }
}
