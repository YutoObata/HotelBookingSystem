package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



public class ReservationManager {

    @Autowired
	UserRepository userRepository;

    @Autowired
    RoomData roomData;

    private Map<String, List<UserData>> roomReservations;

    public ReservationManager() {
        roomReservations = new HashMap<>();
    }

    public boolean isOverlappingReservation(LocalDate checkInDate, LocalDate checkOutDate, String room) {
        List<UserData> reservationsForRoom = roomReservations.getOrDefault(room, new ArrayList<>());
        for (UserData reservation : reservationsForRoom) {
            // System.out.println(reservation);
            if (checkInDate.isBefore(reservation.getCheckOutDate()) && checkOutDate.isAfter(reservation.getCheckInDate().minusDays(1))) {
                return true; // 部屋ごとの重なる予約がある
            }
        }
        return false; // 重なる予約がない
    }

    public void addReservation(LocalDate checkInDate, LocalDate checkOutDate, String room) {
        // System.out.println("in:"+checkInDate+",out:"+checkOutDate+",room:"+room);
        if (isOverlappingReservation(checkInDate, checkOutDate, room)) {
            System.out.println("予約が重なっています。別の日程を選択してください。");
            return;
        }

        // UserData newReservation = new UserData(guestName, checkInDate, checkOutDate, room);
        // List<UserData> reservationsForRoom = roomReservations.getOrDefault(room, new ArrayList<>());
        // reservationsForRoom.add(newReservation);
        // roomReservations.put(room, reservationsForRoom);
        System.out.println("予約が追加されました。");
    }
}

