package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.data.DataStore;
import com.example.eztamilreader.data.Question;
import com.example.eztamilreader.data.Worksheet;
import com.example.eztamilreader.databinding.ActivityTestBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding b;
    int wsNumber;
    Worksheet worksheet;
    List<Question> testQs = new ArrayList<>();
    int idx = 0;
    int score = 0;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        wsNumber = getIntent().getIntExtra("ws_number", 1);
        worksheet = DataStore.getWorksheet(this, wsNumber);
        buildTest();

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Tamil (India) — may fall back if device lacks voice
                tts.setLanguage(new Locale("ta", "IN"));
            }
        });

        b.btnPlayAudio.setOnClickListener(v -> {
            Question q = testQs.get(idx);
            tts.speak(q.tamilQuestion, TextToSpeech.QUEUE_FLUSH, null, "q_ta");
        });

        render();

        b.btnNext.setOnClickListener(v -> {
            checkAndScore();
            idx++;
            if (idx >= testQs.size()) idx = testQs.size() - 1;
            render();
        });

        b.btnSubmit.setOnClickListener(v -> {
            checkAndScore();
            Intent i = new Intent(this, ResultActivity.class);
            i.putExtra("score", score);
            i.putExtra("total", testQs.size());
            startActivity(i);
            finish();
        });
    }

    private void buildTest() {
        if (worksheet == null) return;
        // 15 direct + 15 model (or all if fewer in JSON)
        List<Question> d = new ArrayList<>(worksheet.direct);
        List<Question> m = new ArrayList<>(worksheet.model);
        Collections.shuffle(d);
        Collections.shuffle(m);

        int takeD = Math.min(15, d.size());
        int takeM = Math.min(15, m.size());
        testQs.addAll(d.subList(0, takeD));
        testQs.addAll(m.subList(0, takeM));
        Collections.shuffle(testQs);
    }

    private void render() {
        Question q = testQs.get(idx);
        b.tvQNo.setText("Q " + (idx + 1) + "/" + testQs.size());
        b.tvTamilQ.setText(q.tamilQuestion);
        b.tvEnglishQ.setText(q.englishQuestion);

        b.rgOptions.clearCheck();
        ((RadioButton) b.rgOptions.findViewById(com.example.eztamilreader.R.id.rb1)).setText(q.optionsTamil.get(0));
        ((RadioButton) b.rgOptions.findViewById(com.example.eztamilreader.R.id.rb2)).setText(q.optionsTamil.get(1));
        ((RadioButton) b.rgOptions.findViewById(com.example.eztamilreader.R.id.rb3)).setText(q.optionsTamil.get(2));
    }

    private void checkAndScore() {
        int checkedId = b.rgOptions.getCheckedRadioButtonId();
        int ans = -1;
        if (checkedId == com.example.eztamilreader.R.id.rb1) ans = 0;
        else if (checkedId == com.example.eztamilreader.R.id.rb2) ans = 1;
        else if (checkedId == com.example.eztamilreader.R.id.rb3) ans = 2;

        if (ans == testQs.get(idx).correctIndex) score++;
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
