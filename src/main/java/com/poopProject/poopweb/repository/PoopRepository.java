package com.poopProject.poopweb.repository;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PoopRepository extends JpaRepository<Poop,Long> {
    Poop findTopByUserOrderByCreatedAtDesc(User user);
    Long countByUser (User user);
    Optional<Poop> findFirstByUserOrderByCreatedAtAsc(User user);

    List<Poop> findAllByUserOrderByCreatedAtAsc(User user);

}
