package com.example.tle.bakingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tle.bakingapp.model.Step;
import com.example.tle.bakingapp.R;

public class StepDetailsFragment extends Fragment {
    Step step;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable("step");
        } else {
            Bundle arguments = getArguments();
            step = arguments.getParcelable("step");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        TextView descriptionTv = view.findViewById(R.id.step_details_description_tv);
        String description = step.getDescription();
        descriptionTv.setText(description);
        return view;
    }

    public void updateStepDetailsView(Step step) {
        View view = getView();
        TextView descriptionTv = view.findViewById(R.id.step_details_description_tv);
        String description = step.getDescription();
        descriptionTv.setText(description);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", step);
    }
}
