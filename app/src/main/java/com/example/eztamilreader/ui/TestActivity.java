package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.databinding.ActivityTestBinding;
import com.example.eztamilreader.data.DataStore;
import com.example.eztamilreader.data.Question;
import com.example.eztamilreader.data.Worksheet;
import com.example.eztamilreader.util.Prefs;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding b;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int worksheetNumber;
    private String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        worksheetNumber = getIntent().getIntExtra("ws_number", 1);

        // Load 30 questions for this worksheet
        Worksheet worksheet = DataStore.getWorksheet(this, worksheetNumber);
        questions = worksheet.direct; // All 30 questions

        // Initialize UI
        setupQuestion();

        b.btnSubmit.setOnClickListener(v -> {
            handleAnswer();
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                setupQuestion();
                clearSelection();
            } else {
                showResults();
            }
        });
    }

    private void setupQuestion() {
        Question question = questions.get(currentQuestionIndex);

        // Update progress
        b.tvProgress.setText((currentQuestionIndex + 1) + " / " + questions.size());

        // Setup question based on type
        switch (question.getType()) {
            case MCQ:
                setupMCQ(question);
                break;
            case PRONUNCIATION:
                setupPronunciation(question);
                break;
            case READING:
                setupReading(question);
                break;
            case MATCHING:
                setupMatching(question);
                break;
        }
    }

    private void setupMCQ(Question question) {
        b.layoutMcq.setVisibility(View.VISIBLE);
        b.layoutPronunciation.setVisibility(View.GONE);
        b.layoutReading.setVisibility(View.GONE);
        b.layoutMatching.setVisibility(View.GONE);

        b.tvQuestion.setText(question.getTamilQ());
        b.tvQuestionEng.setText(question.getEnglishQ());

        String[] options = question.getOptions();
        b.rbOption1.setText(options[0]);
        b.rbOption2.setText(options[1]);
        b.rbOption3.setText(options[2]);
        b.rbOption4.setText(options[3]);

        b.rgOptions.clearCheck();
    }

    private void setupPronunciation(Question question) {
        b.layoutMcq.setVisibility(View.GONE);
        b.layoutPronunciation.setVisibility(View.VISIBLE);
        b.layoutReading.setVisibility(View.GONE);
        b.layoutMatching.setVisibility(View.GONE);

        b.tvPronunciationQ.setText(question.getTamilQ());
        b.tvPronunciationQEng.setText(question.getEnglishQ());

        // TODO: Implement audio playback
        b.btnPlayAudio.setOnClickListener(v -> {
            // Play audio file: question.getAudioFileName()
            // For now, just mark as attempted
            selectedAnswer = "pronounced";
        });
    }

    private void setupReading(Question question) {
        b.layoutMcq.setVisibility(View.GONE);
        b.layoutPronunciation.setVisibility(View.GONE);
        b.layoutReading.setVisibility(View.VISIBLE);
        b.layoutMatching.setVisibility(View.GONE);

        b.tvReadingText.setText(question.getReadingText());
        b.tvReadingInstruction.setText(question.getEnglishQ());

        b.btnMarkRead.setOnClickListener(v -> {
            selectedAnswer = "read";
            b.btnMarkRead.setText("✓ Marked as Read");
            b.btnMarkRead.setEnabled(false);
        });
    }

    private void setupMatching(Question question) {
        b.layoutMcq.setVisibility(View.GONE);
        b.layoutPronunciation.setVisibility(View.GONE);
        b.layoutReading.setVisibility(View.GONE);
        b.layoutMatching.setVisibility(View.VISIBLE);

        b.tvTamilWord.setText(question.getTamilQ());
        b.etEnglishMatch.setText("");
        b.tvMatchInstruction.setText("Type the English meaning:");
    }

    private void handleAnswer() {
        Question question = questions.get(currentQuestionIndex);
        boolean isCorrect = false;

        switch (question.getType()) {
            case MCQ:
                int selectedId = b.rgOptions.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRb = findViewById(selectedId);
                    int selectedIndex = getSelectedIndex(selectedId);
                    isCorrect = (selectedIndex == question.getCorrectIndex());
                }
                break;

            case PRONUNCIATION:
                // For prototype, assume all pronunciation attempts are correct
                isCorrect = !selectedAnswer.isEmpty();
                break;

            case READING:
                // For prototype, assume all reading attempts are correct
                isCorrect = selectedAnswer.equals("read");
                break;

            case MATCHING:
                String userAnswer = b.etEnglishMatch.getText().toString().trim().toLowerCase();
                String correctAnswer = question.getMatchPair().toLowerCase();
                isCorrect = userAnswer.equals(correctAnswer);
                break;
        }

        if (isCorrect) {
            score++;
        }
    }

    private int getSelectedIndex(int selectedId) {
        if (selectedId == b.rbOption1.getId()) return 0;
        if (selectedId == b.rbOption2.getId()) return 1;
        if (selectedId == b.rbOption3.getId()) return 2;
        if (selectedId == b.rbOption4.getId()) return 3;
        return -1;
    }

    private void clearSelection() {
        b.rgOptions.clearCheck();
        selectedAnswer = "";
        b.etEnglishMatch.setText("");
        b.btnMarkRead.setText("Mark as Read");
        b.btnMarkRead.setEnabled(true);
    }

    private void showResults() {
        int percentage = (score * 100) / questions.size();
        boolean passed = percentage >= 80;

        // Update completion status
        if (passed) {
            Prefs.setWorksheetCompleted(this, worksheetNumber);
        }

        // Go to ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        intent.putExtra("percentage", percentage);
        intent.putExtra("passed", passed);
        intent.putExtra("worksheet_number", worksheetNumber);
        startActivity(intent);
        finish();
    }
}
