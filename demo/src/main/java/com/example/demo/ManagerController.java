package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    
    private final UserRepository userRepository;
    
    public ManagerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/manager")
    public String manager(Model model) {
        model.addAttribute("userDataList", userRepository.findAll());
        return "manager";
    }
}



