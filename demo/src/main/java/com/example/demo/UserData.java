package com.example.demo;

import org.springframework.security.access.method.P;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userData")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String tel;
    private String email;
    private String checkIn;
    private String checkOut;
    private int adult;
    private int child;
    private String room;
    private int price;

    public UserData() {
    }
    
    public UserData(String name, String tel, String email,
                    String checkIn, String checkOut,
                    int adult, int child, 
                    String room, int price) {
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.adult = adult;
        this.child = child;
        this.room = room;
        this.price = price;
    }
}
