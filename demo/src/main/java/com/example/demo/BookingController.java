package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        Repository
    -------------------- */
	private final UserRepository userRepository;

	public BookingController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/* -------------------
        日程の選択
    -------------------- */
	@PostMapping("/select/dateSelect")
	public String selectDate(@RequestParam("checkInDate") String checkInDate,
							@RequestParam("checkOutDate") String checkOutDate,
							@RequestParam("adult") int adults,
							@RequestParam("child") int children,
							@ModelAttribute @Validated UserData userData,
							Model model) {

		// チェックイン日とチェックアウト日が未入力の場合はエラーとする
		if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
			model.addAttribute("formError", "チェックアウト日またはチェックイン日を入力してください");
			return "selectDate";
		}
		try {
			// チェックアウト日がチェックイン日よりも後の場合はエラーとする
			LocalDate checkIn = LocalDate.parse(checkInDate);
			LocalDate checkOut = LocalDate.parse(checkOutDate);
			if (checkOut.isBefore(checkIn)) {
				model.addAttribute("formError", "チェックアウト日はチェックイン日よりも後に設定してください。");
				return "selectDate";
			}
		} catch (DateTimeParseException e) {
			model.addAttribute("formError", "日付の形式が正しくありません");
			return "redirect/:selectDate";
		}

		// DBに格納
		UserData existingData = userRepository.findByName(userData.getName());
		if (existingData != null) {
			existingData.setAdult(adults); // 変更点：existingDataに対してセットする
			existingData.setChild(children); // 変更点：existingDataに対してセットする
			userRepository.save(existingData);
		} else {
			userData.setAdult(adults); // 変更点：userDataに対してセットする
			userData.setChild(children); // 変更点：userDataに対してセットする
			userRepository.save(userData);
		}

		initializeFromDatabase();
		return "selectRoom";
	}


	/* -------------------
        部屋の選択
    -------------------- */
	@PostMapping("/select/roomSelect")
	public String selectRoom(@RequestParam("roomSelect") String room) {
		if (room.equals("economy")) {
			// 
			return "booking";
		} else if (room.equals("standard")) {
			// 
			return "booking";
		} else {
			return "selectRoom"; // roomSelect.html
		}
	}


	/* -------------------
        個人情報入力
    -------------------- */
	@PostMapping("/booking")
	public String booking(@ModelAttribute @Validated UserData userData,
							BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			// エラーがある場合は入力画面に戻る
			return "booking";
		}

		// DBに格納
		UserData existingData = userRepository.findByName(userData.getName());
		if (existingData != null) {
			existingData.setTel(userData.getTel());
			// existingData.setAdult(userData.getAdult());
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
