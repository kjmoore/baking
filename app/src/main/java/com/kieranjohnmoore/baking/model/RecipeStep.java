package com.kieranjohnmoore.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RecipeStep implements Parcelable {
    public int id = 0;
    public String shortDescription = "";
    public String description = "";
    public String videoURL = "";
    public String thumbnailURL = "";

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Warning, changing this requires changes to the CREATOR
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        public RecipeStep createFromParcel(Parcel in) {
            //Warning, any changes here need to be reflected in writeToParcel
            final RecipeStep unpacked = new RecipeStep();
            unpacked.id = in.readInt();
            unpacked.shortDescription = in.readString();
            unpacked.description = in.readString();
            unpacked.videoURL = in.readString();
            unpacked.thumbnailURL = in.readString();

            return unpacked;
        }

        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };
}
