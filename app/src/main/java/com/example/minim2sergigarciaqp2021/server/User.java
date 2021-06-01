package com.example.minim2sergigarciaqp2021.server;

import java.util.List;

public class User {
    String username;
    int id;
    String avatar_URL;
    String name;
    String surname;
    List<String> badges;

    public User(){ }

    public User(String username, int id, String avatar_URL, String name, String surname, List<String> badges) {
        this.username = username;
        this.id = id;
        this.avatar_URL = avatar_URL;
        this.name = name;
        this.surname = surname;
        this.badges = badges;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_URL() {
        return avatar_URL;
    }

    public void setAvatar_URL(String avatar_URL) {
        this.avatar_URL = avatar_URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }
}
