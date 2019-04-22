package com.kieranjohnmoore.baking.model;

import androidx.annotation.NonNull;

public class RecipeStep {
    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    @Override
    @NonNull
    public String toString() {
        return "RecipeStep{" +
                "id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
