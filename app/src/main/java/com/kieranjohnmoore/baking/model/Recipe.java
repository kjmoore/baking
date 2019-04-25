package com.kieranjohnmoore.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable {
    public int id = 0;
    public String name = "";
    public List<Ingredient> ingredients = Collections.emptyList();
    public List<RecipeStep> steps = Collections.emptyList();
    public double servings = 0;
    public String image = "";

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Warning, changing this requires changes to the CREATOR
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeDouble(servings);
        dest.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            //Warning, any changes here need to be reflected in writeToParcel
            final Recipe unpacked = new Recipe();
            unpacked.id = in.readInt();
            unpacked.name = in.readString();
            unpacked.ingredients = new ArrayList<>();
            in.readList(unpacked.ingredients, Ingredient.class.getClassLoader());
            unpacked.steps = new ArrayList<>();
            in.readList(unpacked.steps, RecipeStep.class.getClassLoader());
            unpacked.servings = in.readInt();
            unpacked.image = in.readString();

            return unpacked;
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
