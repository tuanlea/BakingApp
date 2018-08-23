package com.example.tle.bakingapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private List<Recipe> recipies;
    private RecyclerView recyclerView;
    private RecipeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);

        recyclerView = view.findViewById(R.id.recipes_rv);
        int columnCount = view.getResources().getInteger(R.integer.column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this.getActivity().getApplicationContext(), columnCount));
        adapter = new RecipeListAdapter(this.getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        new RecipeFragment.GetJsonAsyncTask().execute();

        return view;
    }


    private class GetJsonAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Main Activity", "Failed to get JSON", e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            recipies = parseJsonToRecipe(json);
            adapter.setRecipes(recipies);
        }
    }

    private List<Recipe> parseJsonToRecipe(String json) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray recipiesArr = new JSONArray(json);
            for (int i = 0; i < recipiesArr.length(); i++) {
                JSONObject recipeObj = recipiesArr.getJSONObject(i);
                int id = recipeObj.getInt("id");
                String name = recipeObj.getString("name");
                int servings = recipeObj.getInt("servings");
                String image = recipeObj.getString("image");

                JSONArray ingredientsArr = recipeObj.getJSONArray("ingredients");
                List<Ingredient> ingredients = parseJsonToIngredient(ingredientsArr);
                JSONArray stepsArr = recipeObj.getJSONArray("steps");
                List<Step> steps = parseJsonToStep(stepsArr);

                Recipe recipe = new Recipe();
                recipe.setId(id);
                recipe.setName(name);
                recipe.setServings(servings);
                recipe.setImage(image);
                recipe.setIngredientList(ingredients);
                recipe.setStepList(steps);

                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Main Activity", "Failed to parse JSON", e);
        }

        return recipes;
    }

    private List<Ingredient> parseJsonToIngredient(JSONArray ingredientsArr) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArr.length(); i++) {
            JSONObject ingredientObj = ingredientsArr.getJSONObject(i);
            int quantity = ingredientObj.getInt("quantity");
            String measure = ingredientObj.getString("measure");
            String ingredientName = ingredientObj.getString("ingredient");

            Ingredient ingredient = new Ingredient();
            ingredient.setQuantity(quantity);
            ingredient.setMeasure(measure);
            ingredient.setIngredientName(ingredientName);

            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private List<Step> parseJsonToStep(JSONArray stepsArr) throws JSONException {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < stepsArr.length(); i++) {
            JSONObject stepObj = stepsArr.getJSONObject(i);
            int stepId = stepObj.getInt("id");
            String shortDescription = stepObj.getString("shortDescription");
            String description = stepObj.getString("description");
            String videoURL = stepObj.getString("videoURL");
            String thumbnailURL = stepObj.getString("thumbnailURL");

            Step step = new Step();
            step.setId(stepId);
            step.setShortDesc(shortDescription);
            step.setDescription(description);
            step.setVideoURL(videoURL);
            step.setThumbnailURL(thumbnailURL);

            steps.add(step);
        }
        return steps;
    }

}
