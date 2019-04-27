package com.kieranjohnmoore.baking.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.baking.data.RecipeReceiver;
import com.kieranjohnmoore.baking.data.RetrofitBuilder;
import com.kieranjohnmoore.baking.model.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {
    private final static String TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        refreshServerData();
        Log.d(TAG, "Creating main view model");
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    private void refreshServerData() {
        final RecipeReceiver service = RetrofitBuilder.getRetrofit().create(RecipeReceiver.class);
        final Call<List<Recipe>> request = service.getRecipes();
        request.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Could not load recipes", t);
            }
        });
    }
}
