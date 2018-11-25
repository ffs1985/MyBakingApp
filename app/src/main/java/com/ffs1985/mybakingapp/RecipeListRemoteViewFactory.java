package com.ffs1985.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ffs1985.mybakingapp.model.Ingredient;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipesViewModel;

public class RecipeListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    RecipesViewModel viewModel;
    Recipe recipe;

    public RecipeListRemoteViewFactory(Context applicationContext) {
        this.mContext = applicationContext;
        this.viewModel = RecipesViewModel.getInstance();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.recipe = viewModel.getWidgetRecipe();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Ingredient ingredient = recipe.getIngredients().get(i);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_ingredient_widget);
        // Update the plant image
        views.setTextViewText(R.id.widget_recipe_ingredient_name, ingredient.getIngredient());
        views.setTextViewText(R.id.widget_recipe_ingredient_quantity, ingredient.getQuantity() + " " + ingredient.getMeasure());
        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        Bundle extras = new Bundle();
        extras.putLong("RECIPE_ID", recipe.getId());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.layout.recipe_ingredient_widget, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
