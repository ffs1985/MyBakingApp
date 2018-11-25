package com.ffs1985.mybakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipeObserver;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.model.Step;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
        RecipesViewModel viewModel = RecipesViewModel.getInstance();
        RemoteViews rv = getRecipeListRemoteView(context, viewModel.getWidgetRecipe());
        for( int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getRecipeListRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_recipe_title, recipe.getName());
        Intent intent = new Intent(context, RecipeWidgetService.class);
        views.setRemoteAdapter(R.id.widget_recipe_list_view, intent);
        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipe_list_view, appPendingIntent);
        return views;
    }
}

