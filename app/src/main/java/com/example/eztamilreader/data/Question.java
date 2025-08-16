package com.example.eztamilreader.data;

public class Question {
    private String tamilQ;
    private String englishQ;
    private String[] options;
    private int correctIndex;

    // Full constructor
    public Question(String tamilQ, String englishQ, String[] options, int correctIndex) {
        this.tamilQ = tamilQ;
        this.englishQ = englishQ;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    // ✅ Simple constructor (for direct questions without options)
    public Question(String tamilQ, String englishQ) {
        this.tamilQ = tamilQ;
        this.englishQ = englishQ;
        this.options = new String[]{}; // no options
        this.correctIndex = -1;        // no correct answer
    }

    public String getTamilQ() { return tamilQ; }
    public String getEnglishQ() { return englishQ; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
}
