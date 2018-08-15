package com.example.tle.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class RecipeHolder extends RecyclerView.ViewHolder {
    public TextView recipeNameTv;

    RecipeHolder(View itemView) {
        super(itemView);
        recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
    }
}
