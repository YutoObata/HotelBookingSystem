package com.example.demo;

import java.util.List;

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
        List<UserData> userDataList = userRepository.findAll();
        model.addAttribute("userDataList", userDataList);
        return "manager";
    }
}
