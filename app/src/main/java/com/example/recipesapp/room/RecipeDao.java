package com.example.recipesapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipesapp.models.FavouriteRecipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    long insert(FavouriteRecipe recipe);

    @Query("DELETE FROM favourite_recipe WHERE recipeId = :id")
    void delete(String id);

    @Query("SELECT * FROM favourite_recipe")
    List<FavouriteRecipe> getAll();

    @Query("SELECT * FROM favourite_recipe WHERE recipeId = :favouriteName")
    FavouriteRecipe getFavourite(String favouriteName);

    @Query("SELECT * FROM favourite_recipe")
    List<FavouriteRecipe> getAllFavourites();

}
