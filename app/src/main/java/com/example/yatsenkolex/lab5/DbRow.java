package com.example.yatsenkolex.lab5;

import android.annotation.SuppressLint;

import java.io.Serializable;

public class DbRow implements Serializable {
    private int id;
    private String name, email;

    DbRow(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("id:%d   name:%s   email:%s", id, name, email);
    }
}
