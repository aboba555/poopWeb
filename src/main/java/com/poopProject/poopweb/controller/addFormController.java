package com.poopProject.poopweb.controller;

import com.poopProject.poopweb.entity.Poop;
import com.poopProject.poopweb.service.PoopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class addFormController {
    private PoopService poopService;

    @GetMapping("/add-poop")
    public String showAddForm(@ModelAttribute("poop") Poop poop){
        return "addPoop-form";
    }

    @PostMapping("/add-poop")
    public String addPoop(@ModelAttribute("poop") Poop poop){
        poopService.addPoop(poop);
        return "redirect:/home";
    }
}
