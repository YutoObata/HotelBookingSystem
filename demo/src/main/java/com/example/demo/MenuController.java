package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    
    // ローカルホストではmenu.htmlを開く
    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }
}
