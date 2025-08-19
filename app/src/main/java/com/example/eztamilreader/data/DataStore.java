package com.example.eztamilreader.data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStore {

    // ✅ Load worksheet with 30 questions (10+10+5+5)
    public static Worksheet getWorksheet(Context context, int id) {
        try {
            String jsonContent = loadWorksheetFromAssets(context, id);
            if (jsonContent != null) {
                return parseWorksheetFromJson(jsonContent, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generateWorksheet(id);
    }

    private static String loadWorksheetFromAssets(Context context, int id) {
        try {
            InputStream inputStream = context.getAssets().open("questions/worksheet_" + id + ".json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    private static Worksheet parseWorksheetFromJson(String jsonContent, int id) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>(){}.getType();
        List<Question> questions = gson.fromJson(jsonContent, listType);

        Worksheet worksheet = new Worksheet();
        worksheet.number = id;
        worksheet.title = getWorksheetTitle(id);
        worksheet.direct = questions; // Use all 30 questions
        worksheet.model = new ArrayList<>();

        return worksheet;
    }

    // ✅ Generate 30 questions per worksheet
    private static Worksheet generateWorksheet(int id) {
        Worksheet w = new Worksheet();
        w.number = id;
        w.title = getWorksheetTitle(id);
        w.direct = new ArrayList<>();
        w.model = new ArrayList<>();

        List<Question> allQuestions = new ArrayList<>();

        switch (id) {
            case 1:
                allQuestions = generateVowelQuestions();
                break;
            case 2:
                allQuestions = generateConsonantQuestions();
                break;
            default:
                allQuestions = generateAdvancedQuestions(id);
                break;
        }

        // Put all 30 questions in direct for TestActivity
        w.direct = allQuestions;
        return w;
    }

    // ✅ Generate 30 Vowel Questions (Worksheet 1)
    private static List<Question> generateVowelQuestions() {
        List<Question> questions = new ArrayList<>();
        String[] vowels = {"அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ", "ஓ", "ஔ"};
        String[] vowelNames = {"a", "aa", "i", "ii", "u", "uu", "e", "ee", "ai", "o", "oo", "au"};

        // 1. 10 MCQ Questions
        for (int i = 0; i < 10; i++) {
            int idx = i % vowels.length;
            String[] options = {vowelNames[idx], "ka", "ma", "na"};
            questions.add(new Question(
                    "இந்த எழுத்தின் ஒலி என்ன? " + vowels[idx],
                    "What is the sound of this letter? " + vowels[idx],
                    options, 0
            ));
        }

        // 2. 10 Pronunciation Questions
        for (int i = 0; i < 10; i++) {
            int idx = i % vowels.length;
            questions.add(new Question(
                    "இந்த எழுத்தை உச்சரியுங்கள்: " + vowels[idx],
                    "Pronounce this letter: " + vowels[idx],
                    "vowel_" + vowelNames[idx] + ".mp3",
                    Question.QuestionType.PRONUNCIATION
            ));
        }

        // 3. 5 Reading Questions
        String[] readingTexts = {
                "அம்மா", "அப்பா", "அண்ணா", "அக்கா", "அவன்"
        };
        for (String text : readingTexts) {
            questions.add(new Question(text, Question.QuestionType.READING));
        }

        // 4. 5 Matching Questions
        String[] matchPairs = {"அ:a", "ஆ:aa", "இ:i", "ஈ:ii", "உ:u"};
        for (String pair : matchPairs) {
            String[] parts = pair.split(":");
            questions.add(new Question(parts[0], parts[1], Question.QuestionType.MATCHING));
        }

        return questions;
    }

    // ✅ Generate 30 Consonant Questions (Worksheet 2)
    private static List<Question> generateConsonantQuestions() {
        List<Question> questions = new ArrayList<>();
        String[] consonants = {"க", "ங", "ச", "ஞ", "ட", "ண", "த", "ந", "ப", "ம"};
        String[] consonantNames = {"ka", "nga", "cha", "nya", "ta", "na", "tha", "nha", "pa", "ma"};

        // 10 MCQ
        for (int i = 0; i < 10; i++) {
            int idx = i % consonants.length;
            String[] options = {consonantNames[idx], "a", "i", "u"};
            questions.add(new Question(
                    "இந்த எழுத்தின் ஒலி என்ன? " + consonants[idx],
                    "What is the sound of this letter? " + consonants[idx],
                    options, 0
            ));
        }

        // 10 Pronunciation
        for (int i = 0; i < 10; i++) {
            int idx = i % consonants.length;
            questions.add(new Question(
                    "இந்த எழுத்தை உச்சரியுங்கள்: " + consonants[idx],
                    "Pronounce this letter: " + consonants[idx],
                    "consonant_" + consonantNames[idx] + ".mp3",
                    Question.QuestionType.PRONUNCIATION
            ));
        }

        // 5 Reading
        String[] readingTexts = {"கமல்", "சுமா", "ரமேஷ்", "நிஷா", "பாலு"};
        for (String text : readingTexts) {
            questions.add(new Question(text, Question.QuestionType.READING));
        }

        // 5 Matching
        String[] matchPairs = {"க:ka", "ச:cha", "த:tha", "ப:pa", "ம:ma"};
        for (String pair : matchPairs) {
            String[] parts = pair.split(":");
            questions.add(new Question(parts[0], parts[1], Question.QuestionType.MATCHING));
        }

        return questions;
    }

    // ✅ Generate advanced questions for worksheets 3-50
    private static List<Question> generateAdvancedQuestions(int id) {
        List<Question> questions = new ArrayList<>();

        // 10 MCQ
        for (int i = 0; i < 10; i++) {
            String[] options = {"correct", "wrong1", "wrong2", "wrong3"};
            questions.add(new Question(
                    "தமிழ் MCQ " + (i + 1) + " - பாடம் " + id,
                    "Tamil MCQ " + (i + 1) + " - Lesson " + id,
                    options, 0
            ));
        }

        // 10 Pronunciation
        for (int i = 0; i < 10; i++) {
            questions.add(new Question(
                    "உச்சரிப்பு " + (i + 1) + " - பாடம் " + id,
                    "Pronunciation " + (i + 1) + " - Lesson " + id,
                    "advanced_" + id + "_" + i + ".mp3",
                    Question.QuestionType.PRONUNCIATION
            ));
        }

        // 5 Reading
        for (int i = 0; i < 5; i++) {
            questions.add(new Question("வாசிப்பு " + (i + 1), Question.QuestionType.READING));
        }

        // 5 Matching
        for (int i = 0; i < 5; i++) {
            questions.add(new Question("தமிழ்" + i, "tamil" + i, Question.QuestionType.MATCHING));
        }

        return questions;
    }

    private static String getWorksheetTitle(int id) {
        String[] titles = {
                "Basic Vowels (உயிர் எழுத்துக்கள்)",
                "Basic Consonants (மெய் எழுத்துக்கள்)",
                "Combined Letters (உயிர்மெய் எழுத்துக்கள்)",
                "Simple Words (எளிய சொற்கள்)",
                "Animals (விலங்குகள்)",
                "Colors (நிறங்கள்)",
                "Numbers (எண்கள்)",
                "Family (குடும்பம்)",
                "Body Parts (உடல் உறுப்புகள்)",
                "Food (உணவு)"
        };

        if (id <= titles.length) {
            return titles[id - 1];
        }
        return "Advanced Tamil " + id;
    }
}
