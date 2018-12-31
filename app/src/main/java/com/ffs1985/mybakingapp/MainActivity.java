package com.ffs1985.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.ffs1985.mybakingapp.adapters.RecipesAdapter;
import com.ffs1985.mybakingapp.data.RecipesUtil;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.util.BasicIdlingResource;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler{
    private List<Recipe> recipeList;
    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private static final String recipeId = "recipe_id";
    private RecipesViewModel recipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_recipes);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            GridLayoutManager listLayoutManager = new GridLayoutManager(this,3);
            mRecyclerView.setLayoutManager(listLayoutManager);
        } else {
            LinearLayoutManager listLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(listLayoutManager);
        }

        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        recipesViewModel = RecipesViewModel.getInstance();
        recipesViewModel.addObserver(mRecipesAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(Recipe recipe) {
        Context context = this;
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);
        intentToStartRecipeActivity.putExtra(recipeId, recipe.getId());
        recipesViewModel.loadRecipe(recipe.getId());
        recipesViewModel.removeObserver(mRecipesAdapter);
        startActivity(intentToStartRecipeActivity);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return recipesViewModel.getIdlingResource();
    }
}
