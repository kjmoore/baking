package com.kieranjohnmoore.baking.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.ActivityRecipeBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ActivityRecipe extends AppCompatActivity {
    private static final String TAG = ActivityRecipe.class.getSimpleName();
    public static final String VIEW_STEP = "VIEW_STEP";
    public static final String DATA_RECIPE_STEP = "DATA_RECIPE_STEP";

    private final FragmentRecipeStepList recipeSteps = new FragmentRecipeStepList();
    private final FragmentRecipeStep recipeStep = new FragmentRecipeStep();
    private SharedViewModel sharedViewModel;
    private Recipe recipe;
    private boolean isTabletMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        if (intent == null || intent.getParcelableExtra(ActivityMain.DATA_RECIPE) == null) {
            closeOnError();
            return;
        }

        recipe = intent.getParcelableExtra(ActivityMain.DATA_RECIPE);
        Log.d(TAG, "Loading recipe: " + recipe);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        sharedViewModel.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();

        final ActivityRecipeBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        isTabletMode = (viewDataBinding.sideBar != null);

        if (isTabletMode) {
            Log.e(TAG, "Using tablet view");
            fragmentManager.beginTransaction()
                    .add(R.id.side_bar, recipeSteps)
                    .commit();
        } else {
            Log.e(TAG, "Using phone view");
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, recipeSteps)
                    .commit();
            setTitle(recipe.name);
        }

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewStep, new IntentFilter(VIEW_STEP));
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

            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.main_container, recipeStep);

            if (!isTabletMode) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(viewStep);
    }
}


