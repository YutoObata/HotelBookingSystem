package com.example.demo;

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

    private String nameFamily;
    private String name;
    private String nameFamilyKana;
    private String nameKana;
    private int tel;
    private String email;
    private String checkIn;
    private String checkOut;
    private Integer adult;
    private Integer child;
    private String room;
    private int price;

    public UserData() {
        this.adult = 1;
        this.child = 0;
    }
    
    public UserData(String nameFamily, String name, String nameFamilyKana, String nameKana, 
                    int tel, String email,
                    String checkIn, String checkOut,
                    int adult, int child, 
                    String room, int price) {
        this.nameFamily = nameFamily;
        this.name = name;
        this.nameFamilyKana = nameFamilyKana;
        this.nameKana = nameKana;
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
