package com.kieranjohnmoore.baking.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.baking.data.RecipeReceiver;
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

public class SharedViewModel extends AndroidViewModel {
    private final static String TAG = SharedViewModel.class.getSimpleName();
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public SharedViewModel(@NonNull Application application) {
        super(application);
        refreshServerData();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void refreshServerData() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RecipeReceiver service = retrofit.create(RecipeReceiver.class);
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
