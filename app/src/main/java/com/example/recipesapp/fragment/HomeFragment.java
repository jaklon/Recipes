package com.example.recipesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipes.databinding.FragmentHomeBinding;
import com.example.recipesapp.adapters.RecipeAdapter;
import com.example.recipesapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<Recipe> favouriteRecipes;
    List<Recipe> popularRecipes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFavouriteRecipes();
        loadPopularRecipes();
    }

    private void loadPopularRecipes() {
        binding.rvPopulars.setAdapter(new RecipeAdapter());
        popularRecipes = new ArrayList<>();
        popularRecipes.add(new Recipe("1", "Popular One", "recipe1", "null", "Favourite", "null", "", "", "", ""));
        popularRecipes.add(new Recipe("2", "Popular Two", "recipe1", "null", "Favourite", "null", "", "", "", ""));
        popularRecipes.add(new Recipe("3", "Popular 3", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        RecipeAdapter adapter = (RecipeAdapter) binding.rvPopulars.getAdapter();
        if (adapter != null) {
            adapter.setRecipeList(popularRecipes);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadFavouriteRecipes() {
        favouriteRecipes = new ArrayList<>();
        favouriteRecipes.add(new Recipe("1", "Favourite One", "recipe1", "null", "Favourite", "null", "", "", "", ""));
        favouriteRecipes.add(new Recipe("2", "Favourite Two", "recipe1", "null", "Favourite", "null", "", "", "", ""));
        favouriteRecipes.add(new Recipe("3", "Favourite 3", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        favouriteRecipes.add(new Recipe("4", "Favourite 4", "recipe1", "null", "Favourite", "null", "", "", "", ""));

        binding.rvFavouriteMeal.setAdapter(new RecipeAdapter());
        RecipeAdapter adapter = (RecipeAdapter) binding.rvFavouriteMeal.getAdapter();
        if (adapter != null) {
            adapter.setRecipeList(favouriteRecipes);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}