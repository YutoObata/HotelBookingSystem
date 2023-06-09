package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {
    
    /* -------------------
        ローカルホスト
    -------------------- */
    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

    /* -------------------
        メニュー画面
    -------------------- */
    @PostMapping("/select")
    public String selectMode(@RequestParam("mode") String mode, Model model) {
        if (mode.equals("gotoBooking")) {
            // 予約画面へ(日程・人数選択，部屋の選択，個人情報入力)
            return "selectDate";
        } else if (mode.equals("gotoManage")) {
            // 管理者画面へ
            String username = (String) model.getAttribute("username");
            if (username != null) {
                return "redirect:/manager";
            } else {
                return "login";
            }
        } else {
            return "menu";
        }
    }

    /* -------------------
        ログイン画面
    -------------------- */
    @PostMapping("/dashboard")
    public String login(String username, String password, Model model) {
        // ログイン処理
        if (username.equals("user") && password.equals("1234")) {
            model.addAttribute("username", username);
            return "redirect:/manager";
        } else {
            model.addAttribute("errorMessage", "Username or password is incorrect.");
            return "login";
        }
    }
}
