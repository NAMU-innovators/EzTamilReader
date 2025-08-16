package com.example.eztamilreader.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.databinding.ActivitySubscriptionBinding;
import com.example.eztamilreader.util.Prefs;

public class SubscriptionActivity extends AppCompatActivity {
    ActivitySubscriptionBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySubscriptionBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.btnActivate.setOnClickListener(v -> {
            Prefs.setSubscribed(this, true);
            Toast.makeText(this, "Membership activated! All worksheets unlocked.", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
