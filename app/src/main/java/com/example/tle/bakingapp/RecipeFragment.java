package com.example.tle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecipeFragment extends Fragment implements RecipeItemClickHandler {

    private RecyclerView recyclerView;
    private RecipeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        recyclerView = view.findViewById(R.id.recipes_rv);

        int columnCount = view.getResources().getInteger(R.integer.column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this.getActivity().getApplicationContext(), columnCount));
        adapter = new RecipeListAdapter(this.getActivity().getApplicationContext(),
                this);

        Bundle arguments = getArguments();
        List<Recipe> recipes = arguments.getParcelableArrayList("recipes");
        adapter.setRecipes(recipes);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void handleRecipeClick(Recipe recipe) {
        Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}