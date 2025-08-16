package com.example.eztamilreader.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

// 👇 Add these imports so DataStore can see Worksheet & Question
import com.example.eztamilreader.data.Worksheet;
import com.example.eztamilreader.data.Question;

public class DataStore {

    // Example method to fetch a worksheet by ID
    public static Worksheet getWorksheet(Context context, int id) {
        Worksheet w = new Worksheet();
        w.number = id;
        w.title = "Sample Worksheet " + id;

        // Fill some demo questions
        w.direct = new ArrayList<>();
        w.model = new ArrayList<>();

        w.direct.add(new Question("What is அ ?", "Answer 1"));
        w.direct.add(new Question("What is ஆ ?", "Answer 2"));

        w.model.add(new Question("Match this அ", "Answer 3"));
        w.model.add(new Question("Match this ஆ", "Answer 4"));

        return w;
    }
}
