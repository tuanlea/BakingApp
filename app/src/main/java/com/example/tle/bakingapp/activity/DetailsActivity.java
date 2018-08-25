package com.example.tle.bakingapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.StepFragment;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

public class DetailsActivity extends AppCompatActivity implements StepFragment.OnListFragmentInteractionListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        StepFragment stepFragment = new StepFragment();
        stepFragment.setSteps(recipe.getStepList());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.step_fragment_container, stepFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Step step) {
        Toast.makeText(getApplicationContext(), "Fragment interaction: " + step.getId(),
                Toast.LENGTH_SHORT);
    }
}
