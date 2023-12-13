package com.example.recipesapp.models;

public class Recipe {
    private String id;
    private String name;
    private String image;
    private String description;
    private String category;
    private String calories;
    private String time;
    private String authorId;

    public Recipe() {
    }

    public Recipe(String name, String description, String time, String category, String calories, String image, String authorId) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.category = category;
        this.calories = calories;
        this.image = image;
        this.authorId = authorId;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    public String getCalories() {
        return calories;
    }

    public String getImage() {
        return image;
    }

    public String getAuthorId() {
        return authorId;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}