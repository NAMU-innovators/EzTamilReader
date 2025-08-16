package com.example.eztamilreader.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.databinding.ActivitySettingsBinding;
import com.example.eztamilreader.util.Prefs;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        String lang = Prefs.getLang(this);
        if ("TA_EN".equals(lang)) {
            b.rbBoth.setChecked(true);
        } else {
            b.rbTamil.setChecked(true);
        }

        b.rgLangSettings.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == b.rbBoth.getId()) Prefs.setLang(this, "TA_EN");
            else Prefs.setLang(this, "TA");
        });
    }
}
