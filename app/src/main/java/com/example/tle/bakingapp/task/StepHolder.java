package com.example.tle.bakingapp.task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tle.bakingapp.R;

class StepHolder extends RecyclerView.ViewHolder {
    public TextView recipeNameTv;

    StepHolder(View itemView) {
        super(itemView);
        recipeNameTv = itemView.findViewById(R.id.step_fragment_name_tv);
    }
}
