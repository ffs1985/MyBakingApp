package com.ffs1985.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ffs1985.mybakingapp.fragments.MasterListFragment.OnStepClickListener;
import com.ffs1985.mybakingapp.fragments.StepDetailFragment;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipeObserver;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.model.Step;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipeObserver, OnStepClickListener {
    private Recipe recipe;
    private Step step;

    private RecipesViewModel recipesViewModel;
    private boolean mTwoPane;
    private StepDetailFragment stepDetailFragment;

    private Menu menu;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_recipe);
        if (findViewById(R.id.two_pane_layout) != null) {
           mTwoPane = true;
        }

        recipesViewModel = RecipesViewModel.getInstance();
        recipesViewModel.addObserver(this);
        recipe = recipesViewModel.getRecipe();
        setRecipeInfo();
        setTitle(recipe.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipesViewModel.removeObserver(this);
    }

    private void setRecipeInfo() {
        if(mTwoPane) {
            this.step = recipesViewModel.getStep();
            if(this.step != null) {
                setStepInfo();
            }
        }
    }

    @Override
    public void recipesUpdated(List<Recipe> recipeList) {
        // NOTHING.
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        this.recipe = recipe;
        setRecipeInfo();
    }

    @Override
    public void onStepSelected(Step step, int index) {
        this.step = step;
        if (mTwoPane) {
            setStepInfo();
        } else {
            Class destinationClass = StepActivity.class;
            Intent intentToStartRecipeActivity = new Intent(this, destinationClass);
            startActivity(intentToStartRecipeActivity);
        }
    }

    @Override
    public void onWidgetRecipeSelected(Recipe recipe) {
        SelectRecipeService.startActionSelectRecipeWidgets(this);
    }

    private void setStepInfo() {
        if (stepDetailFragment == null) {
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onMasterStepSelected(Step step) {
        this.step = step;
        recipesViewModel.loadStep(step.getId());
        if (mTwoPane) {
            setStepInfo();
        } else {
            Class destinationClass = StepActivity.class;
            Intent intentToStartRecipeActivity = new Intent(this, destinationClass);
            startActivity(intentToStartRecipeActivity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setRecipeAsSelected();
        return super.onOptionsItemSelected(item);
    }

    private void setRecipeAsSelected() {
        recipesViewModel.setWidgetRecipe();
    }
}
