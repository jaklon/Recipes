package com.example.recipesapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.databinding.ItemCategoryBinding;
import com.example.recipesapp.AllRecipesActivity;
import com.example.recipesapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categoryList = new ArrayList<>();

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        notifyDataSetChanged(); // Perhatikan: Menggunakan notifyDataSetChanged, bisa diganti dengan metode lain jika perlu.
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        holder  .onBind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        public CategoryHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Category category) {
            binding.tvName.setText(category.getName());
            Glide
                    .with(binding.getRoot().getContext())
                    .load(category.getImage())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(binding.imgBgCategory);

            binding.getRoot().setOnClickListener(view -> openRecipesByCategory(category.getName(), view));
        }

        private void openRecipesByCategory(String categoryName, View view) {
            Intent intent = new Intent(view.getContext(), AllRecipesActivity.class);
            intent.putExtra("type", "category");
            intent.putExtra("category", categoryName);
            view.getContext().startActivity(intent);
        }
    }
}

