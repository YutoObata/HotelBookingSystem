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
    private Map<String, Integer> userTel;
	private Map<String, Integer> userAdult;
	private UserRepository userRepository;

    public BookingController(UserRepository userRepository) {
        this.userRepository = userRepository;
        initializeFromDatabase(); // データベースの初期化
    }
    
    @PostMapping("/booking")
	public String booking(@ModelAttribute @Validated UserData userData, BindingResult bindingResult, Model model) {
		UserData existingData = userRepository.findByName(userData.getName());
		if(existingData != null) {
			existingData.setTel(userData.getTel());
			existingData.setAdult(userData.getAdult());
			userRepository.save(existingData);
			userRepository.flush();
		} else {
			userRepository.save(userData);
			userRepository.flush();
		}

		System.out.println(userData.getTel());
		System.out.println(userData.getName());
		initializeFromDatabase();
		model.addAttribute("userData", userRepository.findAll());
		return "completed";
	}

    private void initializeFromDatabase() {
		userTel = new HashMap<>();
		userAdult = new HashMap<>();
		Iterable<UserData> user = userRepository.findAll();
		for(UserData data : user) {
			userTel.put("s", 1);
			userAdult.put("s", 3);
		}
    }
}
