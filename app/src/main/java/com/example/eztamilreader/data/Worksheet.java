package com.example.eztamilreader.data;

import com.example.eztamilreader.data.Question;

import java.util.ArrayList;
import java.util.List;

public class Worksheet {
    public int number;
    public String title; // "Basic Vowels", etc.
    public List<Question> direct;  // 15 direct-from-worksheet Qs
    public List<Question> model;   // 15 model-based Qs

    public Worksheet() {}

    // Helper to combine all questions into one list
    public List<Question> getAllQuestions() {
        List<Question> all = new ArrayList<>();
        if (direct != null) all.addAll(direct);
        if (model != null) all.addAll(model);
        return all;
    }
}
