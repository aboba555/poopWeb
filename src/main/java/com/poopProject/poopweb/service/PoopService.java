package com.poopProject.poopweb.service;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.PoopRepository;
import com.poopProject.poopweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public Long countByUser(User user){
        return repository.countByUser(user);
    }
}
