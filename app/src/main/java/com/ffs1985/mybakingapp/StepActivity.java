package com.ffs1985.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ffs1985.mybakingapp.fragments.StepDetailFragment;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipeObserver;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements RecipeObserver, View.OnClickListener {
    @BindView(R.id.next_step) Button btnNextStep;

    private Step step;
    private StepDetailFragment stepDetailFragment;
    private RecipesViewModel recipesViewModel;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        btnNextStep.setOnClickListener(this);

        recipesViewModel = RecipesViewModel.getInstance();
        recipesViewModel.addObserver(this);
        step = recipesViewModel.getStep();
        setStepInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipesViewModel.removeObserver(this);
    }

    @Override
    public void recipesUpdated(List<Recipe> recipeList) {
        // NOTHING.
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        // NOTHING.
    }

    @Override
    public void onStepSelected(Step step, int stepNumber) {
        this.step = step;
        setStepInfo();
    }

    @Override
    public void onWidgetRecipeSelected(Recipe recipe) {
        // NOTHING.
    }

    private void setStepInfo() {
        if (stepDetailFragment == null) {
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_fragment_container, stepDetailFragment)
                    .commit();
        } else {
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_fragment_container, stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {
        recipesViewModel.removeObserver(this);
        recipesViewModel.nextStep();
        setStepInfo();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Context context = this;
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);
        recipesViewModel.removeObserver(this);
        startActivity(intentToStartRecipeActivity);
    }
}
