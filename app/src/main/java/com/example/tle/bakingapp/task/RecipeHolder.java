package com.example.tle.bakingapp.task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tle.bakingapp.R;

class RecipeHolder extends RecyclerView.ViewHolder {
    public TextView recipeNameTv;
    public ImageView recipeImageIv;

    RecipeHolder(View itemView) {
        super(itemView);
        recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
        recipeImageIv = itemView.findViewById(R.id.recipe_image_iv);
    }

}
