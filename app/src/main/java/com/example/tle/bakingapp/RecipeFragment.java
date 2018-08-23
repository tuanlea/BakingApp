package com.example.tle.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecipeFragment extends Fragment {

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

        Bundle arguments = getArguments();
        List<Recipe> recipes = arguments.getParcelableArrayList("recipes");
        adapter.setRecipes(recipes);

        recyclerView.setAdapter(adapter);

        return view;
    }

}
