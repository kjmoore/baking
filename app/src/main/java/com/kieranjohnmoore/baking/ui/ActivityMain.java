package com.kieranjohnmoore.baking.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = ActivityMain.class.getSimpleName();
    public static final String VIEW_RECIPE = "VIEW_RECIPE";
    public static final String VIEW_STEP = "VIEW_STEP";
    public static final String DATA_RECIPE_ID = "DATA_RECIPE_ID";
    public static final String DATA_RECIPE_STEP = "DATA_RECIPE_STEP";

    private final FragmentRecipeList recipeList = new FragmentRecipeList();
    private final FragmentRecipeStepList recipeSteps = new FragmentRecipeStepList();
    private final FragmentRecipeStep recipeStep = new FragmentRecipeStep();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Starting app");
        ViewModelProviders.of(this).get(SharedViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Add the fragment to its container using a transaction
        fragmentManager.beginTransaction()
                .add(R.id.main_container, recipeList)
                .commit();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewRecipe, new IntentFilter(VIEW_RECIPE));
        broadcastManager.registerReceiver(viewStep, new IntentFilter(VIEW_STEP));
    }

    private BroadcastReceiver viewStep = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Navigating to step");

            Bundle bundle = intent.getExtras();
            Log.d(TAG, "Navigation bundle: " + bundle);

            recipeStep.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, recipeStep)
                    .addToBackStack(null)
                    .commit();
        }
    };

    private BroadcastReceiver viewRecipe = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Navigating to Recipe");

            Bundle bundle = intent.getExtras();
            Log.d(TAG, "Navigation bundle: " + bundle);

            recipeSteps.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, recipeSteps)
                    .addToBackStack(null)
                    .commit();
        }
    };
}


