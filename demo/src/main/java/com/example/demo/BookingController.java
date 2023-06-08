package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {
	@Autowired
	private UserRepository userRepository;

    /* -------------------
        Repository
    -------------------- */
    // private final UserRepository userRepository;

    public BookingController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* -------------------
        日程の選択
    -------------------- */
    private Map<String, Integer> numberOfAdult;
    private Map<String, Integer> numberOfChild;

    @PostMapping("/select/dateSelect")
    public String selectDate(@RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate, @RequestParam("adult") int adults,
            @RequestParam("child") int children, @ModelAttribute @Validated UserData userData, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "selectDate";
        }

        if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            model.addAttribute("formError", "チェックアウト日またはチェックイン日を入力してください");
            return "selectDate";
        }

        try {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);
            if (checkOut.isBefore(checkIn)) {
                model.addAttribute("formError", "チェックアウト日はチェックイン日よりも後に設定してください。");
                return "selectDate";
            }
        } catch (DateTimeParseException e) {
            model.addAttribute("formError", "日付の形式が正しくありません");
            return "redirect:/select/dateSelect";
        }

        UserData existingData = userRepository.findByName(userData.getName());
        if (existingData != null) {
            existingData.setAdult(adults);
            existingData.setChild(children);
            userRepository.save(existingData);
        } else {
            existingData = new UserData();
            existingData.setName(userData.getName());
            existingData.setTel(userData.getTel());
            existingData.setAdult(adults);
            existingData.setChild(children);
            userRepository.save(existingData);
        }

        return "selectRoom";
    }


    /* -------------------
        部屋の選択
    -------------------- */
    @PostMapping("/select/roomSelect")
    public String selectRoom(@RequestParam("roomSelect") String room, Model model) {
        if (room.equals("economy")) {
            model.addAttribute("room", "economy");
            return "booking";
        } else if (room.equals("standard")) {
            model.addAttribute("room", "standard");
            return "booking";
        } else {
            return "selectRoom";
        }
    }


    /* -------------------
        個人情報入力
    -------------------- */
    @PostMapping("/booking")
    public String booking(@ModelAttribute @Validated UserData userData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "booking";
        }

        UserData existingData = userRepository.findByName(userData.getName());
        if (existingData != null) {
            existingData.setTel(userData.getTel());
            userRepository.save(existingData);
        } else {
            userRepository.save(userData);
        }

        model.addAttribute("userDataList", userRepository.findAll());
        return "completed";
    }
}
