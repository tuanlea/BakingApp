package com.example.tle.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.model.Ingredient;
import com.example.tle.bakingapp.task.IngredientListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IngredientFragment extends Fragment {

    private static final String ARG_INGREDIENT_LIST = "ingredient-list";
    private OnListFragmentInteractionListener mListener;
    private List<Ingredient> ingredientList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    public static IngredientFragment newInstance(List<Ingredient> ingredientList) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENT_LIST,
                (ArrayList<? extends Parcelable>) ingredientList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ingredientList = getArguments().getParcelableArrayList(ARG_INGREDIENT_LIST);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_list_fragment, container,
                false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new IngredientListAdapter(ingredientList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Ingredient ingredient);
    }
}
