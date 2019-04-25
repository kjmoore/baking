package com.kieranjohnmoore.baking.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.model.RecipeStep;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends AndroidViewModel {
    private final static String TAG = SharedViewModel.class.getSimpleName();
    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    private MutableLiveData<RecipeStep> step = new MutableLiveData<>();

    public SharedViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Creating new shared view model");
    }

    public MutableLiveData<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe.setValue(recipe);
    }

    public MutableLiveData<RecipeStep> getStep() {
        return step;
    }

    public void setStep(RecipeStep step) {
        this.step.setValue(step);
    }
}
