package com.ffs1985.mybakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class SelectRecipeService extends IntentService {
    public static final String ACTION_SELECT_RECIPE = "com.ffs1985.mybakingapp.action.SELECT_RECIPE";

    public SelectRecipeService() {
        super("SelectRecipeService");
    }

    public static void startActionSelectRecipeWidgets(Context context) {
        Intent intent = new Intent(context, SelectRecipeService.class);
        intent.setAction(ACTION_SELECT_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SELECT_RECIPE.equals(action)) {
                handleActionSelectRecipe();
            }
        }
    }

    private void handleActionSelectRecipe() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_list_view);
        RecipeWidget.updateAppWidget(this, appWidgetManager, appWidgetIds);
    }
}
