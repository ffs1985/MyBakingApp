package com.ffs1985.mybakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListRemoteViewFactory(this.getApplicationContext());
    }
}
