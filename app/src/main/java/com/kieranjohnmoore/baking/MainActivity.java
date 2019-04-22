package com.kieranjohnmoore.baking;

import android.os.Bundle;
import android.util.Log;

import com.kieranjohnmoore.baking.model.Recipe;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedViewModel viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewModel.getRecipes().observe(this, this::onRecipesDownloaded);
    }

    private void onRecipesDownloaded(List<Recipe> recipes) {
        Log.e(TAG, "We got: " + recipes);
    }
}
