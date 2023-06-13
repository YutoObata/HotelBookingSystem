package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomData roomData;
    
    public ManagerController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.roomData = new RoomData();
    }
    
    @GetMapping("/manager")
    public String manager(Model model) {
        List<UserData> userDataList = userRepository.findAll();
        model.addAttribute("userDataList", userDataList);
        return "manager";
    }

    @PostMapping("/roomDetail")
    public String booking(@ModelAttribute @Validated UserData userData,
						@RequestParam("roomName") String roomName,
                        @RequestParam(value = "roomPrice", defaultValue = "0") int roomPrice,
                        @RequestParam(value = "roomCapacity", defaultValue = "0") int roomCapacity,
                        @RequestParam(value = "roomNum", defaultValue = "0") int roomNum,
                        Model model) {
        if(roomName.equals("00")) {
            model.addAttribute("error", "客室を選択してください。");
            return "manager";
        }
        System.out.println(roomData.getPrice("Suite"));
        switch (roomName) {
            case "01" :
                roomData.setPrice("Suite", roomPrice);
        }
        System.out.println("->" + roomPrice);
        System.out.println(roomData.getPrice("Suite"));
        
        return "manager";
    }
}
