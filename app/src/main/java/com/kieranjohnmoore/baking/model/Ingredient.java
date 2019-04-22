package com.kieranjohnmoore.baking.model;

import androidx.annotation.NonNull;

class Ingredient {
    public double quantity;
    public String measure;
    public String ingredient;

    @Override
    @NonNull
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
