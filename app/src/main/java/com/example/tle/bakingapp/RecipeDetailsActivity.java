package com.example.tle.bakingapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra("recipe");

        TextView textView = findViewById(R.id.recipe_detail_name_tv);
        textView.setText(recipe.getName());

    }
}
