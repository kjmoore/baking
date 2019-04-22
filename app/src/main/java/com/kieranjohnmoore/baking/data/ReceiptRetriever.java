package com.kieranjohnmoore.baking.data;

import com.kieranjohnmoore.baking.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReceiptRetriever {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
