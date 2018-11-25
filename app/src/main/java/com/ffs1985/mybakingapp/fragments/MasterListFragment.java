package com.ffs1985.mybakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffs1985.mybakingapp.R;
import com.ffs1985.mybakingapp.StepActivity;
import com.ffs1985.mybakingapp.adapters.IngredientsAdapter;
import com.ffs1985.mybakingapp.adapters.StepsAdapter;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipeObserver;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private Recipe recipe;
    private RecipesViewModel recipesViewModel;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;

    @BindView(R.id.recyclerview_ingredients) RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.recyclerview_steps) RecyclerView mStepsRecyclerView;
    @BindView(R.id.recipe_detail_servings) TextView tvServings;

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onMasterStepSelected(Step step);
    }

    public MasterListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_recipe, container, false);
        ButterKnife.bind(this, view);

        recipesViewModel = RecipesViewModel.getInstance();
        this.recipe = recipesViewModel.getRecipe();

        LinearLayoutManager listLayoutManager = new LinearLayoutManager(getActivity());
        mIngredientsRecyclerView.setLayoutManager(listLayoutManager);
        ingredientsAdapter = new IngredientsAdapter();
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);

        LinearLayoutManager listLayoutManager2 = new LinearLayoutManager(getActivity());
        mStepsRecyclerView.setLayoutManager(listLayoutManager2);
        stepsAdapter = new StepsAdapter(this);
        mStepsRecyclerView.setAdapter(stepsAdapter);
        setRecipeInfo();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(Step step) {
        mCallback.onMasterStepSelected(step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    private void setRecipeInfo() {
        ingredientsAdapter.setIngredientData(recipe.getIngredients());
        stepsAdapter.setStepData(recipe.getSteps());
        tvServings.setText("Recipe Servings: "+recipe.getServings());
    }
}
