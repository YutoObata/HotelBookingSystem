package com.example.demo;

public class RoomData {
    
    // 客室の料金：Suite, Deluxe, Superior, Standard, Economy
    int[] roomPrice = {48000, 42000, 36000, 24000, 12000};

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
        }
        return 0;
    }

    // 客室のタイトル
}
