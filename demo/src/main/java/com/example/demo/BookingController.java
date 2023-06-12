package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

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

    private int nights; // 宿泊日数

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
							@RequestParam(value = "adult", defaultValue = "1") String adults,
							@RequestParam(value = "child", defaultValue = "0") String children, 
							@ModelAttribute UserData userData, RoomData roomData,
							BindingResult bindingResult, Model model) {

        if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            model.addAttribute("formError", "チェックイン日またはチェックアウト日を入力してください。");
            return "selectDate";
        }

        try {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);
            if (checkOut.isBefore(checkIn)) {
                model.addAttribute("formError", "チェックアウト日はチェックイン日よりも後に設定してください。");
                return "selectDate";
            }

            Integer adult = Integer.parseInt(adults);
            Integer child = Integer.parseInt(children);

            // 宿泊日数の計算
            this.nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

            // DBに登録
            userData.setCheckIn(checkInDate);
            userData.setCheckOut(checkOutDate);
            userData.setAdult(adult);
            userData.setChild(child);

            // 次のページで表示する各客室の値段を取得
            model.addAttribute("suiteRoomPrice", roomData.getPrice("Suite"));
            model.addAttribute("deluxeRoomPrice", roomData.getPrice("Deluxe"));
            model.addAttribute("SuperiorRoomPrice", roomData.getPrice("Superior"));
            model.addAttribute("standardRoomPrice", roomData.getPrice("Standard"));
            model.addAttribute("economyRoomPrice", roomData.getPrice("Economy"));
            model.addAttribute("userData", userData);
            return "selectRoom";

        } catch (DateTimeParseException e) {
            model.addAttribute("formError", "日付の形式が正しくありません");
            return "redirect:/select/dateSelect";
        }
    }


    /* -------------------
        部屋の選択
    -------------------- */
    @PostMapping("/select/roomSelect")
	public String selectRoom(@RequestParam("roomSelect") String room,
							UserData userData, RoomData roomData, Model model) {
        int roomPrice = roomData.getPrice(room);
        int totalPrice = roomPrice * nights;

        userData.setRoom(room);
        userData.setPrice(totalPrice);
        
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
		return "completed";
	}
}
