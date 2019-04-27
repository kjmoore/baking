package com.kieranjohnmoore.baking.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.ActivityMainBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.MainViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = ActivityMain.class.getSimpleName();

    public static final String DATA_RECIPE = "DATA_RECIPE";

    private static final int COLUMN_WIDTH_DP = 400;

    private RecipeListRecyclerView recipeListRecyclerView = new RecipeListRecyclerView();
    private ActivityMainBinding viewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.d(TAG, "Starting app");

        final GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        viewBinding.recipeList.setLayoutManager(layoutManager);
        viewBinding.recipeList.setAdapter(recipeListRecyclerView);

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, this::onRecipesDownloaded);
    }

    private void onRecipesDownloaded(List<Recipe> recipes) {
        Log.v(TAG, "Downloaded data: " + recipes);
        recipeListRecyclerView.updateRecipes(recipes);
        viewBinding.noData.setVisibility(View.GONE);
    }
    //TODO: Loading spinner

    private int getSpanCount() {
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        final float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        float total = screenWidthDp / COLUMN_WIDTH_DP;
        return Math.round(total);
    }
}


