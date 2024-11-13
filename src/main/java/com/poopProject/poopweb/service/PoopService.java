package com.poopProject.poopweb.service;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.PoopRepository;
import com.poopProject.poopweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class PoopService {
    private PoopRepository repository;
    private UserRepository userRepository;

    public void addPoop(Poop poop) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        poop.setUser(user);
        repository.save(poop);
    }


    public Long countByUser(User user) {
        return repository.countByUser(user);
    }

    public Map<String, String> getAchievements(User user) {

        Map<String, String> achievements = new HashMap<>();

        Long countPoop = countByUser(user);

        achievements.put("One Poop", countPoop >= 1 ? "Achieved" : "Locked");
        achievements.put("Five Poops", countPoop >= 5 ? "Achieved" : "Locked");
        achievements.put("Ten Poops", countPoop >= 10 ? "Achieved" : "Locked");
        achievements.put("Master Pooper", countPoop >= 20 ? "Achieved" : "Locked");

        return achievements;
    }
}
