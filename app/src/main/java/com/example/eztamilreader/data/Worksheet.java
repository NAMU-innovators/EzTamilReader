package com.example.eztamilreader.data;

import java.util.List;

public class Worksheet {
    public int number;
    public String title; // "Basic Vowels", etc.
    public List<Question> direct;  // 15 direct-from-worksheet Qs
    public List<Question> model;   // 15 model-based Qs

    public Worksheet() {}
}
