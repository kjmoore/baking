package com.kieranjohnmoore.baking.data;

import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kieranjohnmoore.baking.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    private static final String TAG = BindingAdapters.class.getSimpleName();

    @BindingAdapter("src")
    public static void setImage(final ImageView imageView, final String posterPath) {
        final Picasso.Builder picassoBuilder = new Picasso.Builder(imageView.getContext());

        picassoBuilder.listener((picasso, uri, exception) -> Log.e(TAG, uri.toString(), exception));

        try {
            if (posterPath == null || posterPath.isEmpty()) {
                Log.w(TAG, "The image URL was empty, loading placeholder");
                picassoBuilder.build()
                        .load(R.drawable.bake)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else {
                picassoBuilder.build()
                        .load(posterPath)
                        .fit()
                        .centerCrop()
                        .error(R.drawable.bake)
                        .into(imageView);
            }
        } catch (AndroidRuntimeException e) {
            Log.e(TAG, "Could not load image", e);
        }
    }

    @BindingAdapter("android:text")
    public static void setText(final TextView text, final double toSet) {
        text.setText(String.format(Locale.getDefault(),"%.2f", toSet));
    }
}
