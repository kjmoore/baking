package com.kieranjohnmoore.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.kieranjohnmoore.baking.R;

public class IngredientsListWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, String shoppingList) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_list_widget);
        views.setTextViewText(R.id.widget_shopping_list, shoppingList);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {}

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}
}

