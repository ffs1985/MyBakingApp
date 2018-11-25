package com.ffs1985.mybakingapp.model;

import com.ffs1985.mybakingapp.RecipeWidget;
import com.ffs1985.mybakingapp.data.RecipesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesViewModel implements Callback<List<Recipe>> {
    private HashMap<Integer, Recipe> recipes;
    private Recipe recipe;
    private Recipe widgetRecipe;
    private Step step;
    private int stepNumber;
    private static RecipesViewModel instance;
    private List<RecipeObserver> observers;

    public static RecipesViewModel getInstance() {
        if (instance == null) {
            instance = new RecipesViewModel();
        }
        return instance;
    }

    private RecipesViewModel() {
        RecipesUtil.loadData(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if(recipes == null) {
            recipes = new HashMap<>();
        }
        for (Recipe recipe : response.body()) {
            recipes.put(recipe.getId(), recipe);
        }
        for (RecipeObserver observer: observers) {
            observer.recipesUpdated(new ArrayList<Recipe>(recipes.values()));
        }
        if (recipes.size() > 0) {
            widgetRecipe = response.body().get(0);
        }

    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        // TODO(ffs1985): add failure message.
        throw new RuntimeException(t);
    }

    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes.values());
    }

    public void loadRecipe(int id) {
        recipe = recipes.get(id);
        for (RecipeObserver observer: observers) {
            observer.onRecipeSelected(recipe);
        }
    }

    public void loadStep(int id) {
        boolean found = false;
        int index = 0;
        List<Step> steps = recipe.getSteps();
        for (int i = 0; i < steps.size();i++) {
            Step currenStep = steps.get(i);
            if(currenStep.getId() == id) {
                this.step = currenStep;
                found = true;
                index = i;
                break;
            }
        }
        if(found) {
            this.stepNumber = index+1;
            for (RecipeObserver observer: observers) {
                observer.onStepSelected(this.step, index + 1);
            }
        }
    }

    public List<Integer> getRecipesIds() {
        return new ArrayList<>(recipes.keySet());
    }

    public Step getStep(int stepId) {
        for (Step step: recipe.getSteps()) {
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }

    public Step getStep(int recipeId, int stepId) {
        recipe = recipes.get(recipeId);
        for (Step step: recipe.getSteps()) {
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Step getStep() {
        return this.step;
    }

    public int getStepNumber() {
        return this.stepNumber;
    }

    public void nextStep() {
        List<Step> steps = recipe.getSteps();
        int index = steps.indexOf(step);
        if (index < steps.size() -1) {
            index++;
        } else {
            index = 0;
        }
        step = steps.get(index);
        for (RecipeObserver observer: observers) {
            observer.onStepSelected(step, index+1);
        }
    }

    public void addObserver(RecipeObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public void removeObserver(RecipeObserver observer) {
        observers.remove(observer);
    }

    public Recipe getWidgetRecipe() {
        return this.widgetRecipe;
    }

    public void setWidgetRecipe() {
        setWidgetRecipe(this.recipe);
    }

    private void setWidgetRecipe(Recipe recipe) {
        if (recipe != null) {
            this.widgetRecipe = recipe;
            for (RecipeObserver observer: observers) {
                observer.onWidgetRecipeSelected(recipe);
            }
        }
    }
}
