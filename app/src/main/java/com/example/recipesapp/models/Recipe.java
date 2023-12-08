package com.example.recipesapp.models;

public class Recipe {
    private String id;
    private String name;
    private String image;
    private String description;
    private String category;
    private String instructions;
    private String ingredients;
    private String calories;
    private String time;
    private String servings;

    public Recipe(String id, String name, String image, String description, String category, String instructions, String ingredients, String calories, String time, String servings) {}
    public Recipe(String id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.calories = calories;
        this.time = time;
        this.servings = servings;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getCalories() {
        return calories;
    }

    public String getTime() {
        return time;
    }

    public String getServings() {
        return servings;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }
}
