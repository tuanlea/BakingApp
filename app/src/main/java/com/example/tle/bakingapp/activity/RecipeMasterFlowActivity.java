package com.example.tle.bakingapp.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.example.tle.bakingapp.BakingAppAppWidget;
import com.example.tle.bakingapp.R;
import com.example.tle.bakingapp.fragment.IngredientFragment;
import com.example.tle.bakingapp.fragment.StepDetailsFragment;
import com.example.tle.bakingapp.fragment.StepListFragment;
import com.example.tle.bakingapp.model.Ingredient;
import com.example.tle.bakingapp.model.Recipe;
import com.example.tle.bakingapp.model.Step;

import java.util.List;

public class RecipeMasterFlowActivity extends AppCompatActivity
        implements StepListFragment.OnListFragmentInteractionListener,
            IngredientFragment.OnListFragmentInteractionListener {

    Recipe recipe;
    Step step;
    String recipeParcelName = "recipe";
    boolean isOnePane;

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

        if (this.recipe == null) {
            return;
        }

        String name = this.recipe.getName();
        if (name != null) {
            setTitle(name);
        }

        initializeFragments();

        // update widget with the ingredient
        updateWidget();
    }

    private void updateWidget() {
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_app_widget);
        ComponentName thisWidget = new ComponentName(context, BakingAppAppWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, "updated text: " + this.recipe.getName());
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initializeFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<Step> stepList = this.recipe.getStepList();
        StepListFragment stepListFragment = StepListFragment.newInstance(stepList);
        fragmentTransaction.replace(R.id.step_list_fragment_container, stepListFragment);

        // Ingredients fragment
        IngredientFragment ingredientFragment =
                IngredientFragment.newInstance(this.recipe.getIngredientList());
        fragmentTransaction.replace(R.id.ingredient_list_fragment_container, ingredientFragment);

        // Step details fragment
        View view = findViewById(R.id.step_details_fragment_container);
        isOnePane = (view == null);
        if (!isOnePane) {
            Bundle arguments = new Bundle();
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

        if (isOnePane) {
            // Start another activity to display step details fragment
            Intent intent = new Intent(this, DisplayStepDetailsFragmentActivity.class);
            intent.putExtra("step", step);
            intent.putExtra(recipeParcelName, recipe);
            setResult(Activity.RESULT_OK, intent);
            startActivityForResult(intent, Activity.RESULT_OK);
        } else {
            // If article frag is available, we're in two-pane layout...

            // update the fragment view
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.step_details_fragment_container, stepDetailsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListFragmentInteraction(Ingredient ingredient) {
        // No action when an ingredient is clicked
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(recipeParcelName, recipe);
        outState.putParcelable("step", this.step);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.recipe = savedInstanceState.getParcelable(recipeParcelName);
        this.step = savedInstanceState.getParcelable("step");
        initializeFragments();
    }
}