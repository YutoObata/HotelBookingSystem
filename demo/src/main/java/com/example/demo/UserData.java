package com.example.demo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userData")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 宿泊者名
    private int tel;     // 電話番号
    private int adult;   // 大人の人数
    private int child;   // 子供の人数
    private String room; //客室タイプ

    public UserData() {
    }

    public UserData(int adult, int child) {
        this.adult = adult;
        this.child = child;
    }

    public UserData(String name, int tel, int adult) {
        this.name = name;
        this.tel = tel;
        this.adult = adult;
    }

    /* -------------------
        getter, setter
    -------------------- */
    // Name<String>
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Tel<int>
    public Integer getTel() {
        return tel;
    }
    public void setTel(int tel) {
        this.tel = tel;
    }

    // Adult<int>
    public Integer getAdult() {
        return adult;
    }
    public void setAdult(int adult) {
        this.adult = adult;
    }

    // Child<int>
    public Integer getChild() {
        return child;
    }
    public void setChild(int child) {
        this.child = child;
    }
}
