package com.kieranjohnmoore.baking.model;

import java.util.List;

import androidx.annotation.NonNull;

public class Recipe {
    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<RecipeStep> steps;
    public int servings;
    public String image;

    @Override
    @NonNull
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }
}
