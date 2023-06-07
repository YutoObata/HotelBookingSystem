package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {
    
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
}
