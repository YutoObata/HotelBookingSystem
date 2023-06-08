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

    private String name;
    private int tel;
    private int adult;
    private int child;
    private String room;

    public UserData() {
    }
    
    public UserData(String name, int tel, int adult, int child, String room) {
        this.name = name;
        this.tel = tel;
        this.adult = adult;
        this.child = child;
        this.room = room;
    }
}
