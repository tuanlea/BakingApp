package com.example.tle.bakingapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

public class DisplayStepDetailsFragmentActivity extends AppCompatActivity {
    Recipe recipe;
    Step step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_step_detail_fragment);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        step = intent.getParcelableExtra("step");


        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailsFragment stepDetailsFragment =
                (StepDetailsFragment) fragmentManager.findFragmentById(R.id.step_details_fragment_container);
        // Existing fragment will automatically reattach to this activity, create new one otherwise.
        if (stepDetailsFragment == null) {
            stepDetailsFragment = new StepDetailsFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelable("step", step);
            stepDetailsFragment.setArguments(arguments);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Send the intent back to caller with the recipe
                Intent resultIntent = new Intent();
                resultIntent.putExtra("recipe", recipe);
                setResult(Activity.RESULT_OK, resultIntent);
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
