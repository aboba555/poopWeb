package com.poopProject.poopweb.repository;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoopRepository extends JpaRepository<Poop,Long> {
    Poop findTopByUserOrderByCreatedAtDesc(User user);
    Long countByUser (User user);
}
