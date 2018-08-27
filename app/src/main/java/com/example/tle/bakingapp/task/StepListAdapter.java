package com.example.tle.bakingapp.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.StepListFragment.OnListFragmentInteractionListener;
import com.example.tle.bakingapp.model.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepHolder> {

    private final List<Step> steps;
    private final OnListFragmentInteractionListener mListener;

    public StepListAdapter(List<Step> steps, OnListFragmentInteractionListener listener) {
        this.steps = steps;
        mListener = listener;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_fragment_item, parent, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepHolder holder, int position) {
        final Step step = steps.get(position);
        holder.recipeNameTv.setText(step.getShortDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(step);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

}