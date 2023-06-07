package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

	/* -------------------
        部屋の選択
    -------------------- */
	@PostMapping("/roomSelect")
	public String selectMode(@RequestParam("roomSelect") String room) {
		if (room.equals("economy")) {
			// 
			return "booking";
		} else if (room.equals("standard")) {
			// 
			return "booking";
		} else {
			return "roomSelect"; // roomSelect.html
		}
	}

	/* -------------------
        予約画面
    -------------------- */
    private final UserRepository userRepository;

	public BookingController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/booking")
	public String booking(@ModelAttribute @Validated UserData userData, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			// エラーがある場合は入力画面に戻る
			return "booking";
		}

		UserData existingData = userRepository.findByName(userData.getName());
		if (existingData != null) {
			existingData.setTel(userData.getTel());
			existingData.setAdult(userData.getAdult());
			userRepository.save(existingData);
		} else {
			userRepository.save(userData);
		}

		initializeFromDatabase();
		model.addAttribute("userDataList", userRepository.findAll());
		return "completed";
	}

	private void initializeFromDatabase() {
		// データベースから初期化する必要はありません
	}
}
