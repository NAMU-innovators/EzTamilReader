package com.example.eztamilreader.data;

import android.content.Context;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataStore {
    private static Worksheet readWorksheet(Context ctx, String fileName) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open("questions/" + fileName)));
            Worksheet ws = new Gson().fromJson(br, Worksheet.class);
            br.close();
            return ws;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Worksheet getWorksheet(Context ctx, int number) {
        String fname = "worksheet" + number + ".json";
        return readWorksheet(ctx, fname);
    }
}
