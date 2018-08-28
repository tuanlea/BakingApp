package com.example.tle.bakingapp.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.IngredientFragment.OnListFragmentInteractionListener;
import com.example.tle.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {

    private final List<Ingredient> ingredientList;
    private final OnListFragmentInteractionListener mListener;

    public IngredientListAdapter(List<Ingredient> ingredientList, OnListFragmentInteractionListener listener) {
        this.ingredientList = ingredientList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredient = ingredient;
        holder.ingredientNameTv.setText(ingredient.getIngredientName());
        holder.quantityTv.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureTv.setText(ingredient.getMeasure());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.ingredient);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView quantityTv;
        public TextView ingredientNameTv;
        public TextView measureTv;
        public Ingredient ingredient;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ingredientNameTv = view.findViewById(R.id.ingredient_name_tv);
            quantityTv = view.findViewById(R.id.quantity_tv);
            measureTv = view.findViewById(R.id.measure_tv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + ingredientNameTv.getText() + "'";
        }
    }
}
