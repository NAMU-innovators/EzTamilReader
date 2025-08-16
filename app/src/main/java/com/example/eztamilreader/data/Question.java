package com.example.eztamilreader.data;

import java.util.List;

public class Question {
    public String id;
    public String tamilQuestion;   // e.g., "‘வழி’ என்பதன் அர்த்தம் என்ன?"
    public String englishQuestion; // e.g., "What is the meaning of 'வழி'?"
    public List<String> optionsTamil;   // ["பாதை", "மலர்", "வீடு"]
    public List<String> optionsEnglish; // ["Path", "Flower", "House"] (optional)
    public int correctIndex;       // 0-based

    // Empty constructor for Gson
    public Question() {}
}
