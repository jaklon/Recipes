package com.example.recipesapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentProfileBinding;
import com.example.recipesapp.adapters.RecipeAdapter;
import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.models.User;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProfile();
        loadUserRecipes();
    }

    private void loadUserRecipes() {
        binding.rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvProfile.setAdapter(new RecipeAdapter());
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("1", "One", "recipe1", "null", "Favourite", "null", "", "", "", ""));
        recipes.add(new Recipe("2", "Two", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        recipes.add(new Recipe("2", "Three", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        recipes.add(new Recipe("2", "Three", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        recipes.add(new Recipe("2", "Three", "recipe2", "null", "Favourite", "null", "", "", "", ""));
        RecipeAdapter adapter = (RecipeAdapter) binding.rvProfile.getAdapter();
        if (adapter != null) {
            adapter.setRecipeList(recipes);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    binding.tvUserName.setText(user.getName());
                    binding.tvEmail.setText(user.getEmail());
                    Glide
                            .with(requireContext())
                            .load(user.getImage())
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher)
                            .into(binding.imgProfile);

                    Glide
                            .with(requireContext())
                            .load(user.getCover())
                            .centerCrop()
                            .placeholder(R.drawable.bg_default_recipe)
                            .into(binding.imgCover);
                } else {
                    Log.e("ProfileFragment", "onDataChange: User is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "onCancelled: " + error.getMessage());
            }
        });
        User user = new User();
        user.setName("Jaklon");
        user.setEmail("jaklon@gmail.com");
        binding.tvUserName.setText(user.getName());
        binding.tvEmail.setText(user.getEmail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}