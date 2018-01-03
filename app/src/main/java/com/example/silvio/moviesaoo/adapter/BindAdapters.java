package com.example.silvio.moviesaoo.adapter;

import android.databinding.BindingAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.util.FontManager;

/**
 * Created by silvio on 21/12/2017.
 */

public class BindAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        if (url != null && view != null) {
            Glide.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_empty)
                    .into(view);
        }
    }

    @BindingAdapter("font")
    public static void setTypeFace(TextView view, String font) {
        FontManager fontManager = MainApplication.getAppComponent().getFontManager();
        view.setTypeface(fontManager.get(font));
    }

    @BindingAdapter("font")
    public static void setEdittextFont(EditText view, String font) {
        FontManager fontManager = MainApplication.getAppComponent().getFontManager();
        view.setTypeface(fontManager.get(font));
    }

    @BindingAdapter("font")
    public static void setButtonFont(Button view, String font) {
        FontManager fontManager = MainApplication.getAppComponent().getFontManager();
        view.setTypeface(fontManager.get(font));
    }
}
