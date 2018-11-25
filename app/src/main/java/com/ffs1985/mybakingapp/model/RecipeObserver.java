package com.ffs1985.mybakingapp.model;

import java.util.List;

public interface RecipeObserver {
    void recipesUpdated(List<Recipe> recipeList);
    void onRecipeSelected(Recipe recipe);
    void onStepSelected(Step step, int stepNumber);
    void onWidgetRecipeSelected(Recipe recipe);
}
