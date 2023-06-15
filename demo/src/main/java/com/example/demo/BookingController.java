package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
	UserRepository userRepository;
    @Autowired
    UserData userData;
    @Autowired
    RoomData roomData;

    /* -------------------
        Repository
    -------------------- */
    public BookingController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.roomData = new RoomData();
    }

    // 宿泊日数
    private int nights;

    List<LocalDate> checkInDateList = new ArrayList<LocalDate>();
    List<LocalDate> checkOutDateList = new ArrayList<LocalDate>();
    List<String> roomType = new ArrayList<String>();

    /* -------------------
        日程の選択
    -------------------- */
    @PostMapping("/select/dateSelect")
    public String selectDate(@RequestParam("checkInDate") String checkIn,
							@RequestParam("checkOutDate") String checkOut,
							@RequestParam(value = "adult", defaultValue = "1") String adults,
							@RequestParam(value = "child", defaultValue = "0") String children, 
							@ModelAttribute UserData userData,
							BindingResult bindingResult, Model model) {

        if (checkIn.isEmpty() || checkOut.isEmpty()) {
            model.addAttribute("formError", "チェックイン日またはチェックアウト日を入力してください。");
            return "selectDate";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if (checkOutDate.isBefore(checkInDate)) {
                model.addAttribute("formError", "チェックアウト日はチェックイン日よりも後に設定してください。");
                return "selectDate";
            }

            // 宿泊日数の計算
            this.nights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            // 日程をリストに格納
            checkInDateList.add(checkInDate);
            checkOutDateList.add(checkOutDate);
            // DBに登録
            userData.setCheckIn(checkIn);
            userData.setCheckOut(checkOut);
            userData.setCheckInDate(checkInDate);
            userData.setCheckOutDate(checkOutDate);
            userData.setAdult(Integer.parseInt(adults));
            userData.setChild(Integer.parseInt(children));

            // 日程と残数チェック
            for (int i = 0; i < checkInDateList.size(); i++) {
                // 今回のチェックイン日が、前の人のチェックイン日よりも前 かつ チェックアウト日が前の人のチェックイン日よりも前の場合
                if (checkInDate.isBefore(checkInDateList.get(i)) && checkOutDate.isAfter(checkInDateList.get(i))) {
                    roomData.setNowRoomNum(roomType.get(i), roomData.getNowRoomNum(roomType.get(i))-1);
                } // 今回のチェックイン日が、前の人の日程内(チェックイン日よりも後 かつ チェックアウト日よりも前)の場合
                else if (checkInDate.isAfter(checkInDateList.get(i)) && checkInDate.isBefore(checkOutDateList.get(i))) {
                    roomData.setNowRoomNum(roomType.get(i), roomData.getNowRoomNum(roomType.get(i))-1);
                }
            }

            if (roomData.getNowRoomNum("Suite") <= 0) {
                roomData.setNowRoomNum("Suite", 0);
            }

            // 各客室の情報を取得してモデルに追加
            String[] roomTypes = {"Suite", "Deluxe", "Superior", "Standard", "Economy"};
            for (String roomType : roomTypes) {
                model.addAttribute(roomType + "RoomPrice", roomData.getPrice(roomType));
                model.addAttribute(roomType + "RoomText", roomData.getRoomText(roomType));
                model.addAttribute(roomType + "RoomCapacity", roomData.getRoomCapacity(roomType));
                model.addAttribute(roomType + "RoomNum", roomData.getNowRoomNum(roomType));
            }

            model.addAttribute("nights", nights);
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
                            UserData userData, Model model) {
        int roomPrice = roomData.getPrice(room);
        int totalPrice = roomPrice * nights;
        userData.setRoom(room);
        userData.setPrice(totalPrice);
        roomType.add(room);

        int roomCapacity = roomData.getRoomCapacity(room);
        int stayAdult = userData.getAdult();
        int stayChild = userData.getChild();

        int SuiteRoomNum = roomData.getNowRoomNum("Suite");
        int DeluxeRoomNum = roomData.getNowRoomNum("Deluxe");
        int SuperiorRoomNum = roomData.getNowRoomNum("Superior");
        int StandardRoomNum = roomData.getNowRoomNum("Standard");
        int EconomyRoomNum = roomData.getNowRoomNum("Economy");

        if ( roomCapacity < stayAdult + stayChild ) {
            String[] roomTypes = {"Suite", "Deluxe", "Superior", "Standard", "Economy"};
            for (String roomType : roomTypes) {
                model.addAttribute(roomType + "RoomPrice", roomData.getPrice(roomType));
                model.addAttribute(roomType + "RoomText", roomData.getRoomText(roomType));
                model.addAttribute(roomType + "RoomCapacity", roomData.getRoomCapacity(roomType));
                model.addAttribute(roomType + "RoomNum", roomData.getNowRoomNum(roomType));
            }
            model.addAttribute("error", "宿泊者数が客室の定員を超えています。");
            return "selectRoom";
        }
        
        if ( SuiteRoomNum == 0 || DeluxeRoomNum == 0 || SuperiorRoomNum == 0 || StandardRoomNum == 0 || EconomyRoomNum == 0) {
            String[] roomTypes = {"Suite", "Deluxe", "Superior", "Standard", "Economy"};
            for (String roomType : roomTypes) {
                model.addAttribute(roomType + "RoomPrice", roomData.getPrice(roomType));
                model.addAttribute(roomType + "RoomText", roomData.getRoomText(roomType));
                model.addAttribute(roomType + "RoomCapacity", roomData.getRoomCapacity(roomType));
                model.addAttribute(roomType + "RoomNum", roomData.getRoomNum(roomType));
            }
            model.addAttribute("error", "選択された客室は満室のため予約できません。");
            return "selectRoom";
        }
		return "booking";
	}


    /* -------------------
        個人情報入力
    -------------------- */
    @PostMapping("/booking")
    public String booking(@ModelAttribute @Validated UserData userData,
						@RequestParam("nameFamily") String nameFamily, @RequestParam("name") String name,
                        @RequestParam("nameFamilyKana") String nameFamilyKana, @RequestParam("nameKana") String nameKana, 
                        @RequestParam("tel") String tel, @RequestParam("email") String email, 
                        Model model) {

        boolean isNameInvalid = inputFormCheck1(nameFamily) || inputFormCheck1(name);
        boolean isNameKanaInvalid = inputFormCheck2(nameFamilyKana) || inputFormCheck2(nameKana);
        boolean isTelInvalid = inputFormCheckTel(tel);
        boolean isEmailInvalid = inputFormCheckEmail(email);

        if(isNameInvalid) { // 予約者名(性・名)の入力チェック
            model.addAttribute("formErrorName1", "漢字またはひらがなで入力してください。");
            return "booking";
        }

        if(isNameKanaInvalid) { // セイ・メイ(カナ・英字)の入力チェック
            model.addAttribute("formErrorName2", "カタカナまたは全角英字で入力してください。");
            return "booking";
        }

        if(isTelInvalid) { // 電話番号の入力チェック
            model.addAttribute("formErrorTel", "電話番号は0x0 後ろ8桁、ハイフンなしで入力してください。");
            return "booking";
        }

        if(isEmailInvalid) { // 電話番号の入力チェック
            model.addAttribute("formErrorEmail", "正しい形式でメールアドレスを入力してください。");
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

    /* ------------------------------------------------
        名前入力チェック
            漢字入力:"^[\u4e00-\u9fa5]+$" ( 一 ~ 龥 )
            ひらがな:"^[\\u3040-\\u309F]+$"
            カタカナ:"^[\u30a0-\u30ff]+$"
            全角英字:"^[a-zA-Z]+$"
    ------------------------------------------------- */
    public boolean inputFormCheck1(String str){
        if (str.matches("^[\u4e00-\u9fa5]+$") || str.matches("^[\\u3040-\\u309F]+$")) {
            return false;
        }
        else { return true; }
    }

    public boolean inputFormCheck2(String str){
        if (str.matches("^[\u30a0-\u30ff]+$") || str.matches("^[a-zA-Z]+$")) {
            return false;
        }
        else { return true; }
    }

    /* ------------------------------------------------
        電話番号入力チェック
            電話番号:"^0[789]0\d{8}$"
            ・090..or 080..or 070..
            ・ハイフンなし
    ------------------------------------------------- */
    public boolean inputFormCheckTel(String str){
        if (str.matches("^0[789]0\\d{8}$")) {
            return false;
        }
        else { return true; }
    }

    /* ------------------------------------------------
        メールアドレスの入力チェック
    ------------------------------------------------- */
    public boolean inputFormCheckEmail(String str){
        if (str.matches("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            return false;
        }
        else { return true; }
    }
}
