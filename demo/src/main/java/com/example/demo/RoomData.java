package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class RoomData {

    Map<String, Integer> roomPriceMap = new HashMap<>();
    Map<String, String> roomTextMap = new HashMap<>();
    Map<String, Integer> roomCapacityMap = new HashMap<>();
    Map<String, Integer> roomCountMap = new HashMap<>();
    Map<String, Integer> roomNowCountMap = new HashMap<>();

    public RoomData() {
        roomPriceMap.put("Suite", 48000);
        roomPriceMap.put("Deluxe", 42000);
        roomPriceMap.put("Superior", 36000);
        roomPriceMap.put("Standard", 24000);
        roomPriceMap.put("Economy", 12000);

        roomTextMap.put("Suite", "1キング 高層階スイート");
        roomTextMap.put("Deluxe", "1キング デラックス");
        roomTextMap.put("Superior", "1クイーン スーペリア");
        roomTextMap.put("Standard", "2シングル スタンダード");
        roomTextMap.put("Economy", "1シングル エコノミー");

        roomCapacityMap.put("Suite", 8);
        roomCapacityMap.put("Deluxe", 6);
        roomCapacityMap.put("Superior", 4);
        roomCapacityMap.put("Standard", 2);
        roomCapacityMap.put("Economy", 2);

        roomCountMap.put("Suite", 1);
        roomCountMap.put("Deluxe", 2);
        roomCountMap.put("Superior", 2);
        roomCountMap.put("Standard", 2);
        roomCountMap.put("Economy", 2);

        roomNowCountMap.put("Suite", roomCountMap.get("Suite"));
        roomNowCountMap.put("Deluxe", roomCountMap.get("Deluxe"));
        roomNowCountMap.put("Superior", roomCountMap.get("Superior"));
        roomNowCountMap.put("Standard", roomCountMap.get("Standard"));
        roomNowCountMap.put("Economy", roomCountMap.get("Economy"));
    }

    public int getPrice(String room) {
        return roomPriceMap.get(room);
    }

    public void setPrice(String room, int num) {
        roomPriceMap.put(room, num);
    }

    public String getRoomText(String room) {
        return roomTextMap.get(room);
    }

    public int getRoomCapacity(String room) {
        return roomCapacityMap.get(room);
    }

    public void setCapacity(String room, int num) {
        roomCapacityMap.put(room, num);
    }

    public int getRoomNum(String room) {
        return roomCountMap.get(room);
    }

    public void setRoomNum(String room, int num) {
        roomCountMap.put(room, num);
    }

    public int getNowRoomNum(String room) {
        return roomNowCountMap.get(room);
    }

    public void setNowRoomNum(String room, int num) {
        roomNowCountMap.put(room, num);
    }
}
