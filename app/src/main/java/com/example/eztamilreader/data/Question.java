package com.example.eztamilreader.data;

public class Question {
    public enum QuestionType {
        MCQ,           // Multiple Choice Question
        PRONUNCIATION, // Pronunciation word
        READING,       // Reading text aloud
        MATCHING       // Match Tamil with English
    }

    private String tamilQ;
    private String englishQ;
    private String[] options;
    private int correctIndex;
    private QuestionType type;
    private String audioFileName; // For pronunciation questions
    private String readingText;   // For reading questions
    private String matchPair;     // For matching questions

    // ✅ MCQ Constructor
    public Question(String tamilQ, String englishQ, String[] options, int correctIndex) {
        this.tamilQ = tamilQ;
        this.englishQ = englishQ;
        this.options = options;
        this.correctIndex = correctIndex;
        this.type = QuestionType.MCQ;
    }

    // ✅ Pronunciation Constructor
    public Question(String tamilQ, String englishQ, String audioFileName, QuestionType type) {
        this.tamilQ = tamilQ;
        this.englishQ = englishQ;
        this.audioFileName = audioFileName;
        this.type = type;
        this.options = new String[]{};
        this.correctIndex = -1;
    }

    // ✅ Reading Constructor
    public Question(String readingText, QuestionType type) {
        this.readingText = readingText;
        this.tamilQ = "பின்வரும் வாக்கியத்தை படியுங்கள்";
        this.englishQ = "Read the following sentence";
        this.type = type;
        this.options = new String[]{};
        this.correctIndex = -1;
    }

    // ✅ Matching Constructor
    public Question(String tamilQ, String matchPair, QuestionType type) {
        this.tamilQ = tamilQ;
        this.englishQ = "Match the Tamil word with English";
        this.matchPair = matchPair;
        this.type = type;
        this.options = new String[]{};
        this.correctIndex = -1;
    }

    // ✅ Getters
    public String getTamilQ() { return tamilQ; }
    public String getEnglishQ() { return englishQ; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public QuestionType getType() { return type; }
    public String getAudioFileName() { return audioFileName; }
    public String getReadingText() { return readingText; }
    public String getMatchPair() { return matchPair; }

    // ✅ Utility methods
    public boolean hasOptions() { return options != null && options.length > 0; }
    public String getCorrectAnswer() {
        return hasOptions() ? options[correctIndex] : "";
    }
    public boolean isMCQ() { return type == QuestionType.MCQ; }
    public boolean isPronunciation() { return type == QuestionType.PRONUNCIATION; }
    public boolean isReading() { return type == QuestionType.READING; }
    public boolean isMatching() { return type == QuestionType.MATCHING; }
}
