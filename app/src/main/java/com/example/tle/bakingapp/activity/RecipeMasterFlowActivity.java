package com.example.tle.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.IngredientFragment;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.fragment.StepListFragment;
import com.example.tle.bakingapp.model.Ingredient;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

import java.util.List;

public class RecipeMasterFlowActivity extends AppCompatActivity
        implements StepListFragment.OnListFragmentInteractionListener,
            IngredientFragment.OnListFragmentInteractionListener {

    Recipe recipe;
    Step step;
    String recipeParcelName = "recipe";
    boolean isOnePane;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master_flow);

        if (savedInstanceState != null) {
            this.recipe = savedInstanceState.getParcelable(recipeParcelName);
            this.step = savedInstanceState.getParcelable("step");
        } else {
            this.recipe = getIntent().getParcelableExtra(recipeParcelName);
        }

        if (this.recipe == null) {
            return;
        }

        String name = this.recipe.getName();
        if (name != null) {
            setTitle(name);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<Step> stepList = this.recipe.getStepList();
        StepListFragment stepListFragment = StepListFragment.newInstance(stepList);
        fragmentTransaction.replace(R.id.step_list_fragment_container, stepListFragment);

        // Ingredients fragment
        IngredientFragment ingredientFragment =
                IngredientFragment.newInstance(this.recipe.getIngredientList());
        fragmentTransaction.replace(R.id.ingredient_list_fragment_container, ingredientFragment);

        // Step details fragment
        View view = findViewById(R.id.step_details_fragment_container);
        isOnePane = (view == null);
        if (!isOnePane) {
            Bundle arguments = new Bundle();
            // default to first step
            if (step == null) {
                step = stepList.get(0);
            }
            // otherwise, use saved step
            arguments.putParcelable("step", step);

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setArguments(arguments);

            fragmentTransaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Step step) {
        // Remember the selected step to restore
        this.step = step;

        if (isOnePane) {
            // Start another activity to display step details fragment
            Intent intent = new Intent(this, DisplayStepDetailsFragmentActivity.class);
            intent.putExtra("step", step);
            startActivity(intent);
        } else {
            // If article frag is available, we're in two-pane layout...

            // update the fragment view
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListFragmentInteraction(Ingredient ingredient) {
        // No action when an ingredient is clicked
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(recipeParcelName, recipe);
        outState.putParcelable("step", this.step);
    }

}