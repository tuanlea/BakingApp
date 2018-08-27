package com.example.tle.bakingapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.fragment.StepFragment;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

public class DetailsActivity extends AppCompatActivity implements StepFragment.OnListFragmentInteractionListener {

    Recipe recipe;
    String recipeParcelName = "recipe";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState != null) {
            this.recipe = savedInstanceState.getParcelable(recipeParcelName);
        } else {
            this.recipe = getIntent().getParcelableExtra(recipeParcelName);
        }

        if (this.recipe != null) {
            String name = this.recipe.getName();
            if (name != null) {
                setTitle(name);
            }
        }

        StepFragment stepFragment = new StepFragment();
        stepFragment.setSteps(this.recipe.getStepList());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.step_fragment_container, stepFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Step step) {
        Toast.makeText(getApplicationContext(), "Fragment interaction: " + step.getId(),
                Toast.LENGTH_SHORT).show();
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.step_details_fragment_container);

        if (stepDetailsFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // update the fragment view
            stepDetailsFragment.updateStepDetailsView(step);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            stepDetailsFragment = new StepDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable("step", step);
            stepDetailsFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(recipeParcelName, recipe);
    }
}
