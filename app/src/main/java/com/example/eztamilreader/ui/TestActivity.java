package com.example.eztamilreader.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.eztamilreader.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eztamilreader.data.DataStore;
import com.example.eztamilreader.data.Question;
import com.example.eztamilreader.data.Worksheet;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private Button btnNext;
    private Button btnPrev;

    private Worksheet worksheet;
    private List<Question> questions;
    private int currentIndex = 0; // to track which question we’re on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.eztamilreader.R.layout.activity_test);


        txtQuestion = findViewById(R.id.txtQuestion);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        // Get worksheetId from intent
        int worksheetId = getIntent().getIntExtra("worksheetId", -1);
        worksheet = DataStore.getWorksheet(this, worksheetId);

        if (worksheet != null) {
            questions = worksheet.getAllQuestions(); // ✅ pulls direct + model together
        }

        if (questions == null || questions.isEmpty()) {
            txtQuestion.setText("⚠ No questions available in this worksheet");
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
        } else {
            showQuestion(currentIndex);
        }

        // Next button
        btnNext.setOnClickListener(v -> {
            if (currentIndex < questions.size() - 1) {
                currentIndex++;
                showQuestion(currentIndex);
            }
        });

        // Previous button
        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion(currentIndex);
            }
        });
    }

    private void showQuestion(int index) {
        Question q = questions.get(index);
        // Assuming your Question model has "text"
        txtQuestion.setText((index + 1) + ". " + q.getTamilQ());

        // enable/disable nav buttons
        btnPrev.setEnabled(index > 0);
        btnNext.setEnabled(index < questions.size() - 1);
    }
}
