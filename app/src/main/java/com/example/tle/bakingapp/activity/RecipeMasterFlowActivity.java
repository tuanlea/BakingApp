package com.example.tle.bakingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.IngredientFragment;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.fragment.StepListFragment;
import com.example.tle.bakingapp.model.Ingredient;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeMasterFlowActivity extends AppCompatActivity
        implements StepListFragment.OnListFragmentInteractionListener,
            IngredientFragment.OnListFragmentInteractionListener {

    Recipe recipe;
    Step step;
    String recipeParcelName = "recipe";

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

        if (this.recipe != null) {
            String name = this.recipe.getName();
            if (name != null) {
                setTitle(name);
            }
        }

        List<Step> stepList = this.recipe.getStepList();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("stepList", (ArrayList<? extends Parcelable>) stepList);
        StepListFragment stepListFragment = new StepListFragment();
        stepListFragment.setArguments(arguments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.step_list_fragment_container, stepListFragment);

        // Ingredients fragment
        IngredientFragment ingredientFragment =
                IngredientFragment.newInstance(this.recipe.getIngredientList());
        fragmentTransaction.replace(R.id.ingredient_list_fragment_container, ingredientFragment);

        // Step details fragment
        View view = findViewById(R.id.step_details_fragment_container);
        if (view != null) {
            arguments = new Bundle();
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

        View detailView = findViewById(R.id.step_details_fragment_container);
        if (detailView == null) {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected step
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable("step", step);
            stepDetailsFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.step_list_fragment_container, stepDetailsFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        } else {
            // If article frag is available, we're in two-pane layout...

            // update the fragment view
            StepDetailsFragment stepDetailsFragment = (StepDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.step_details_fragment_container);
            stepDetailsFragment.updateStepDetailsView(step);
        }
    }

    @Override
    public void onListFragmentInteraction(Ingredient ingredient) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(recipeParcelName, recipe);
        outState.putParcelable("step", this.step);
    }

}
