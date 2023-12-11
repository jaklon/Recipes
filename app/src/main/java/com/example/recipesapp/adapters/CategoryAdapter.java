package com.example.recipesapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.databinding.ItemCategoryBinding;
import com.example.recipesapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    List<Category> categoryList;

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
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
        private ItemCategoryBinding binding;
        public CategoryHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public  void onBind(Category category) {
            binding.tvName.setText(category.getName());
            if (category.getName().equalsIgnoreCase("Lunch")) {
                binding.imgBgCategory.setImageResource(R.drawable.lunch);
            } else if (category.getName().equalsIgnoreCase("Dinner")) {
                binding.imgBgCategory.setImageResource(R.drawable.recipe1);
            } else {
                binding.imgBgCategory.setImageResource(R.drawable.breakfast);
            }
        }
    }
}
