package com.example.silvio.moviesaoo.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/**
 * Created by silvio on 21/12/2017.
 */

public class FontManager {

    @StringDef({M_BOLD, M_EXTRA_BOLD, M_REGULAR, M_LIGHT, M_MEDIUM, U_LIGHT, U_MEDIUM, U_BOLD, U_REGULAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontType {
    }

    public static final String M_BOLD = "m_bold";
    public static final String M_EXTRA_BOLD = "m_extra_bold";
    public static final String M_REGULAR = "m_regular";
    public static final String M_LIGHT = "m_light";
    public static final String M_MEDIUM = "m_medium";
    public static final String U_LIGHT = "u_light";
    public static final String U_MEDIUM = "u_medium";
    public static final String U_BOLD = "u_bold";
    public static final String U_REGULAR = "u_regular";

    private final Context context;
    private final HashMap<String, Typeface> typefaces;

    public FontManager(Context context) {
        this.context = context;
        typefaces = new HashMap<>();
    }

    public Typeface get(@FontType String type) {

        Typeface typeface = typefaces.get(type);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), type + ".otf");
            typefaces.put(type, typeface);
        }

        return typeface;
    }
}
