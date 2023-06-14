package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final UserRepository userRepository;

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

        // 各客室の情報を取得してモデルに追加
        String[] roomTypes = {"Suite", "Deluxe", "Superior", "Standard", "Economy"};
        for (String roomType : roomTypes) {
            model.addAttribute(roomType + "RoomPrice", roomData.getPrice(roomType));
            model.addAttribute(roomType + "RoomText", roomData.getRoomText(roomType));
            model.addAttribute(roomType + "RoomCapacity", roomData.getRoomCapacity(roomType));
            model.addAttribute(roomType + "RoomNum", roomData.getRoomNum(roomType));
        }

        return "manager";
    }

    @PostMapping("/roomDetail")
    public String booking(@ModelAttribute @Validated UserData userData,
						@RequestParam("roomName") String roomName,
                        @RequestParam(value = "roomPrice", defaultValue = "0") int roomPrice,
                        @RequestParam(value = "roomCapacity", defaultValue = "0") int roomCapacity,
                        @RequestParam(value = "roomNum", defaultValue = "0") int roomNum,
                        Model model) {
                            
        if (roomName.equals("00")) {
            model.addAttribute("error", "客室を選択してください。");
            return "redirect:/manager";
        }

        Map<String, String> roomMap = new HashMap<>();
        roomMap.put("01", "Suite");
        roomMap.put("02", "Deluxe");
        roomMap.put("03", "Superior");
        roomMap.put("04", "Standard");
        roomMap.put("05", "Economy");

        String selectedRoom = roomMap.get(roomName);

        if (selectedRoom != null) {
            updateRoomData(selectedRoom, roomPrice, roomCapacity, roomNum);
        }

        return "redirect:/manager";
    }

    private void updateRoomData(String roomType, int roomPrice, int roomCapacity, int roomNum) {
        if (roomPrice != 0) {
            roomData.setPrice(roomType, roomPrice);
        }
        if (roomCapacity != 0) {
            roomData.setCapacity(roomType, roomCapacity);
        }
        if (roomNum != 0) {
            roomData.setRoomNum(roomType, roomNum);
        }
    }
}
