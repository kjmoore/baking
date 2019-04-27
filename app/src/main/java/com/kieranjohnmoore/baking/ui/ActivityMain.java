package com.kieranjohnmoore.baking.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.ActivityMainBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.MainViewModel;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

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

        //Install TLSv1.2 for old android devices
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            Log.e(TAG, "Could not install TLSv1.2, data will not download");
        }
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


