package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {
    
    // ローカルホストではmenu.htmlを開く
    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

    @PostMapping("/select")
	public String selectMode(@RequestParam("mode") String mode) {
		if (mode.equals("gotoBooking")) {
			// 予約画面へ
			return "login";
		} else if (mode.equals("gotoManage")) {
			// 管理者画面へ
			return "manager";
		} else {
			return "menu";
		}
	}

    // @GetMapping("/login")
    // public String showLoginPage() {
    //     return "login";
    // }

    @PostMapping("/dashboard")
    public String login(String username, String password) {
        // ログイン処理
        if (username.equals("user") && password.equals("1234")) {
            return "conpleted";
        } else {
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "conpleted";
    }
}
