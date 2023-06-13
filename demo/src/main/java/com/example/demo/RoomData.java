package com.example.demo;

public class RoomData {
    
    // 客室の料金：Suite, Deluxe, Superior, Standard, Economy
    int[] roomPrice = {48000, 42000, 36000, 24000, 12000};
    // 客室の説明
    String[] roomText = {"1キング 高層階スイート",
                        "1キング デラックス",
                        "1クイーン スーペリア",
                        "2シングル スタンダード",
                        "1シングル エコノミー"};
    // 客室の定員
    int[] roomCapacity = {8, 6, 4, 2, 2};

    public int getPrice(String room){
        switch (room) {
            case "Suite":
                return roomPrice[0];
            case "Deluxe":
                return roomPrice[1];
            case "Superior":
                return roomPrice[2];
            case "Standard":
                return roomPrice[3];
            case "Economy":
                return roomPrice[4];
        } return 0;
    }

    public String getRoomText(String room){
        switch (room) {
            case "Suite":
                return roomText[0];
            case "Deluxe":
                return roomText[1];
            case "Superior":
                return roomText[2];
            case "Standard":
                return roomText[3];
            case "Economy":
                return roomText[4];
        } return "";
    }

    public int getRoomCapacity(String room){
        switch (room) {
            case "Suite":
                return roomCapacity[0];
            case "Deluxe":
                return roomCapacity[1];
            case "Superior":
                return roomCapacity[2];
            case "Standard":
                return roomCapacity[3];
            case "Economy":
                return roomCapacity[4];
        } return 0;
    }
}
