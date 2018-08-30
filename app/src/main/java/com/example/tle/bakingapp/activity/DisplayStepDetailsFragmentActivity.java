package com.example.tle.bakingapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.model.Step;

public class DisplayStepDetailsFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_step_detail_fragment);

        Step step = getIntent().getParcelableExtra("step");

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("step", step);
        stepDetailsFragment.setArguments(arguments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
        fragmentTransaction.commit();
    }

}
