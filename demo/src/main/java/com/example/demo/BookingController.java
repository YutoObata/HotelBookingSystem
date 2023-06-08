package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public BookingController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* -------------------
        日程の選択
    -------------------- */
    @PostMapping("/select/dateSelect")
    public String selectDate(@RequestParam("checkInDate") String checkInDate,
							@RequestParam("checkOutDate") String checkOutDate,
							@RequestParam("adult") Integer adults,
							@RequestParam("child") Integer children, 
							@ModelAttribute @Validated UserData userData, 
							BindingResult bindingResult, Model model) {

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

		// DBに登録
		userData.setCheckIn(checkInDate);
		userData.setCheckOut(checkOutDate);
		userData.setAdult(adults);
		userData.setChild(children);
		model.addAttribute("userData", userData);
		return "selectRoom";
    }

    @GetMapping("/increase")
    public String countNumber() {
        return "selectDate";
    }


    /* -------------------
        部屋の選択
    -------------------- */
    @PostMapping("/select/roomSelect")
	public String selectRoom(@RequestParam("roomSelect") String room,
							UserData userData, Model model) {
		userData.setRoom(room);
		model.addAttribute("userData", userData);
		return "booking";
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
			existingData.setRoom(userData.getRoom()); // Set the selected room value
			userRepository.save(existingData);
		} else {
			userData.setRoom(userData.getRoom()); // Set the selected room value
			userRepository.save(userData);
		}

		model.addAttribute("userDataList", userRepository.findAll());
		return "completed";
	}
}
