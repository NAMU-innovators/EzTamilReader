package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.eztamilreader.R;
import com.example.eztamilreader.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 30);
        int percentage = getIntent().getIntExtra("percentage", 0);
        boolean passed = getIntent().getBooleanExtra("passed", false);
        int worksheetNumber = getIntent().getIntExtra("worksheet_number", 1);

        // Display results
        b.tvScore.setText("Score: " + score + "/" + total);
        b.tvPercentage.setText("Percentage: " + percentage + "%");

        if (passed) {
            b.tvResult.setText("🎉 Congratulations! You passed!");
            // ✅ Fixed: Use ContextCompat.getColor() for API compatibility
            b.tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            b.tvMessage.setText("You can now access the next worksheet!");

            // Update progress bar
            if (b.progressBar != null) {
                b.progressBar.setProgress(percentage);
            }

            // Hide retry button for passed tests
            if (b.btnRetry != null) {
                b.btnRetry.setVisibility(android.view.View.GONE);
            }
        } else {
            b.tvResult.setText("❌ Try Again!");
            // ✅ Fixed: Use ContextCompat.getColor() for API compatibility
            b.tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            b.tvMessage.setText("You need 80% or higher to unlock the next worksheet.");

            // Update progress bar
            if (b.progressBar != null) {
                b.progressBar.setProgress(percentage);
            }

            // Show retry button for failed tests
            if (b.btnRetry != null) {
                b.btnRetry.setVisibility(android.view.View.VISIBLE);
                b.btnRetry.setOnClickListener(v -> {
                    // Go back to TestActivity to retry
                    Intent retryIntent = new Intent(this, TestActivity.class);
                    retryIntent.putExtra("ws_number", worksheetNumber);
                    startActivity(retryIntent);
                    finish();
                });
            }
        }

        b.btnClose.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }
}
