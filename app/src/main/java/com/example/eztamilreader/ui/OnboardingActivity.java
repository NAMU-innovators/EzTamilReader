package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.databinding.ActivityOnboardingBinding;
import com.example.eztamilreader.util.Prefs;

public class OnboardingActivity extends AppCompatActivity {
    ActivityOnboardingBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.btnContinue.setOnClickListener(v -> {
            int id = b.rgLang.getCheckedRadioButtonId();
            RadioButton rb = findViewById(id);
            String choice = rb.getText().toString().contains("English") ? "TA_EN" : "TA";
            Prefs.setLang(this, choice);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }
}
