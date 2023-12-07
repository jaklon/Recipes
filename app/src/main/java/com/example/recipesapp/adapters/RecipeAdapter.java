package com.example.recipesapp.adapters;

import static com.example.recipes.databinding.ItemRecipeBinding.inflate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.databinding.ItemRecipeBinding;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    @NonNull
    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeHolder(inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class RecipeHolder extends RecyclerView.ViewHolder {
        ItemRecipeBinding binding;

        public RecipeHolder(@NonNull ItemRecipeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind() {

        }

    }
}
