package com.example.tle.bakingapp.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeHolder> {
    private LayoutInflater layoutInflater;
    private List<Recipe> recipes;
    RecipeItemClickHandler recipeItemClickHandler;

    public RecipeListAdapter(Context applicationContext,
                             RecipeItemClickHandler recipeItemClickHandler) {
        layoutInflater = LayoutInflater.from(applicationContext);
        this.recipeItemClickHandler = recipeItemClickHandler;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recipe_list_fragment_item,
                        parent,
                        false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        if (recipes != null) {
            final Recipe recipe = recipes.get(position);
            holder.recipeNameTv.setText(recipe.getName());

            String image = recipe.getImage();
            if (!TextUtils.isEmpty(image)) {
                Picasso.get().load(image).into(holder.recipeImageIv);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipeItemClickHandler.handleRecipeClick(recipe);
                }
            });
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
