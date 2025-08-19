package com.example.eztamilreader.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String NAME = "ez_tamil_prefs";
    private static final String KEY_LANG = "lang";
    private static final String KEY_SUB = "is_subscribed";
    private static final String KEY_COMPLETED = "completed_worksheet_";

    public static void setLang(Context ctx, String lang) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_LANG, lang).apply();
    }

    public static String getLang(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_LANG, "TA_EN");
    }

    public static void setSubscribed(Context ctx, boolean val) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_SUB, val).apply();
    }

    public static boolean isSubscribed(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_SUB, false);
    }

    // ✅ Worksheet completion tracking
    public static void setWorksheetCompleted(Context ctx, int worksheetNumber) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_COMPLETED + worksheetNumber, true).apply();
    }

    public static boolean isWorksheetCompleted(Context ctx, int worksheetNumber) {
        SharedPreferences sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_COMPLETED + worksheetNumber, false);
    }

    // ✅ Check if worksheet is unlocked (first 2 free, or previous completed with 80%+, or subscribed)
    public static boolean isWorksheetUnlocked(Context ctx, int worksheetNumber) {
        if (worksheetNumber <= 2) return true; // First 2 free
        if (isSubscribed(ctx)) return true; // Subscribed users get all
        if (worksheetNumber == 3) return isWorksheetCompleted(ctx, 2); // Worksheet 3 unlocked if 2 completed
        return isWorksheetCompleted(ctx, worksheetNumber - 1); // Sequential unlocking
    }
}
