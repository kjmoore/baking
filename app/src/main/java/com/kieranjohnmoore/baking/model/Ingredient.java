package com.kieranjohnmoore.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Ingredient implements Parcelable {
    public double quantity = 0;
    public String measure = "";
    public String ingredient = "";

    @Override
    @NonNull
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Warning, changing this requires changes to the CREATOR
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            //Warning, any changes here need to be reflected in writeToParcel
            final Ingredient unpacked = new Ingredient();
            unpacked.quantity = in.readDouble();
            unpacked.measure = in.readString();
            unpacked.ingredient = in.readString();

            return unpacked;
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
