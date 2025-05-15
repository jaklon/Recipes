package com.example.recipesapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipes.databinding.FragmentCategoryBinding;
import com.example.recipesapp.adapters.CategoryAdapter;
import com.example.recipesapp.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryAdapter categoryAdapter;

    private DatabaseReference reference;
    private ValueEventListener categoriesListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryAdapter = new CategoryAdapter();
        binding.rvCategories.setAdapter(categoryAdapter);

        loadCategories();
    }

    private void loadCategories() {
        reference = FirebaseDatabase.getInstance().getReference("Categories");

        categoriesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Cek apakah binding masih valid (view belum dihancurkan)
                if (binding == null) return;

                List<Category> categories = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    if (category != null) {
                        categories.add(category);
                    }
                }
                categoryAdapter.setCategoryList(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Error", error.getMessage());
            }
        };

        reference.addValueEventListener(categoriesListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Hapus listener Firebase supaya tidak callback setelah view dihancurkan
        if (reference != null && categoriesListener != null) {
            reference.removeEventListener(categoriesListener);
        }

        binding = null;
    }
}
