package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.PoopRepository;
import com.poopProject.poopweb.repository.UserRepository;
import com.poopProject.poopweb.service.PoopService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class AchievementsController {

    UserRepository userRepository;
    PoopService poopService;
    PoopRepository poopRepository;

    @GetMapping("/achievements")
    public String showAchievementsStats(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("User not found"));
        Long countPoop = poopService.countByUser(user);
        Optional<Poop> firstPoop = poopRepository.findFirstByUserOrderByCreatedAtAsc(user);

        if (firstPoop.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            String formattedDate = firstPoop.get().getCreatedAt().format(formatter);
            model.addAttribute("firstPoop", formattedDate);
        } else {
            model.addAttribute("firstPoop", "You haven't pooped yet!");
        }


        model.addAttribute("countPoop", countPoop);
        model.addAttribute("achievements", poopService.getAchievements(user));

        return "achievements_stats";
    }
}
