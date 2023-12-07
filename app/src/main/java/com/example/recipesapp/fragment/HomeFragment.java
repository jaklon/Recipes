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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvFavouriteMeal.setAdapter(new RecipeAdapter());
        binding.rvPopulars.setAdapter(new RecipeAdapter());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}