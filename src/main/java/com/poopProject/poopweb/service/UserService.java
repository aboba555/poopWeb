package com.poopProject.poopweb.service;

import com.poopProject.poopweb.entity.Level;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void addUser(User user){
        user = new User(user.getFirstname(),user.getLastname(),user.getEmail(),passwordEncoder.encode(user.getPassword()));
        user.setLevel(Level.PIONEER);
        userRepository.save(user);
    }

}
