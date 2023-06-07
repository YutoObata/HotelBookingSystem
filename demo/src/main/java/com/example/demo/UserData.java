package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userData")
public class UserData {
    @Id
    private String name;
    private int tel;
    private int adult;

    public UserData() {
    }

    public UserData(String name, int tel, int adult) {
        this.name = name;
        this.tel = tel;
        this.adult = adult;
    }

    // getter, setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }
}
