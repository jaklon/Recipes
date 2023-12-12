package com.example.recipesapp.models;

public class  User {
    private String id;
    private String name;
    private String email;
    private String image;
    private String cover;

    public User() {
    }

    public User(String id, String name, String email, String image, String cover) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.cover = cover;
    }

    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for 'image'
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter and Setter for 'cover'
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
