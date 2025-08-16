package com.example.eztamilreader.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String NAME = "ez_tamil_prefs";
    private static final String KEY_LANG = "lang";
    private static final String KEY_SUB = "is_subscribed";

    public static void setLang(Context ctx, String lang) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_LANG, lang).apply();
    }

    public static String getLang(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_LANG, "TA_EN"); // both visible by default
    }

    public static void setSubscribed(Context ctx, boolean val) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_SUB, val).apply();
    }

    public static boolean isSubscribed(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_SUB, false);
    }
}
