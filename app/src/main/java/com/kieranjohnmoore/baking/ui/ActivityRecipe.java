package com.kieranjohnmoore.baking.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ActivityRecipe extends AppCompatActivity {
    private static final String TAG = ActivityRecipe.class.getSimpleName();
    public static final String VIEW_STEP = "VIEW_STEP";
    public static final String VIEW_INGREDIENTS = "VIEW_INGREDIENTS";
    public static final String DATA_RECIPE_STEP = "DATA_RECIPE_STEP";

    private final FragmentRecipe recipeSteps = new FragmentRecipe();
    private final FragmentIngredientsList ingredientsList = new FragmentIngredientsList();
    private final FragmentRecipeStep recipeStep = new FragmentRecipeStep();
    private SharedViewModel sharedViewModel;
    private Recipe recipe;
    private boolean isTabletMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        final Intent intent = getIntent();
        if (intent == null || intent.getParcelableExtra(ActivityMain.DATA_RECIPE) == null) {
            closeOnError();
            return;
        }

        recipe = intent.getParcelableExtra(ActivityMain.DATA_RECIPE);
        Log.d(TAG, "Loading recipe: " + recipe);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        sharedViewModel.setRecipe(recipe);

        isTabletMode = this.getResources().getBoolean(R.bool.tablet_mode);

        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            if (isTabletMode) {
                Log.d(TAG, "Using tablet view");
                fragmentManager.beginTransaction()
                        .add(R.id.side_bar, recipeSteps)
                        .commit();
            } else {
                Log.d(TAG, "Using phone view");
                fragmentManager.beginTransaction()
                        .add(R.id.main_container, recipeSteps)
                        .commit();
                setTitle(recipe.name);
            }
        }

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewStep, new IntentFilter(VIEW_STEP));
        broadcastManager.registerReceiver(viewIngredients, new IntentFilter(VIEW_INGREDIENTS));
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver viewStep = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to step");

            Bundle bundle = intent.getExtras();
            Log.d(TAG, "Navigation bundle: " + bundle);

            if (bundle != null) {
                final int stepId = bundle.getInt(DATA_RECIPE_STEP);
                sharedViewModel.setStep(recipe.steps.get(stepId));
            }

            setFragment(recipeStep);
        }
    };

    private BroadcastReceiver viewIngredients = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to ingredients");
            setFragment(ingredientsList);
        }
    };

    private void setFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.main_container) == fragment) {
            return;
        }

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_container, fragment);

        if (!isTabletMode) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(viewStep);
        broadcastManager.unregisterReceiver(viewIngredients);
    }
}


