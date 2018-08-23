package com.example.tle.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class RecipeListAdapter extends RecyclerView.Adapter<RecipeHolder> {
    private LayoutInflater layoutInflater;
    private List<Recipe> recipes;

    public RecipeListAdapter(Context applicationContext) {
        layoutInflater = LayoutInflater.from(applicationContext);
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                layoutInflater.inflate(R.layout.activity_main_recylerview_item,
                        parent,
                        false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        if (recipes != null) {
            Recipe recipe = recipes.get(position);
            holder.recipeNameTv.setText(recipe.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
