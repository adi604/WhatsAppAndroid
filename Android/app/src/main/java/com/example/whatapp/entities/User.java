package com.example.whatapp.entities;

public class User {
    private String id;
    private String name;
    private String password;
    private String image;

    public User(String id, String name, String password, String image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
