package com.example.eztamilreader.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.R;
import com.example.eztamilreader.data.DataStore;
import com.example.eztamilreader.data.Worksheet;
import com.example.eztamilreader.databinding.ActivityWorksheetBinding;
import com.example.eztamilreader.ui.TestActivity;

public class WorksheetActivity extends AppCompatActivity {
    ActivityWorksheetBinding b;
    int wsNumber;
    Worksheet worksheet;
    boolean completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityWorksheetBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        wsNumber = getIntent().getIntExtra("ws_number", 1);
        worksheet = DataStore.getWorksheet(this, wsNumber);

        b.tvWsTitle.setText("Worksheet " + wsNumber + " - " + (worksheet != null ? worksheet.title : ""));

        // Simple local video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_lesson);
        b.videoView.setVideoURI(videoUri);
        MediaController mc = new MediaController(this);
        b.videoView.setMediaController(mc);
        b.videoView.start();

        b.btnMarkComplete.setOnClickListener(v -> {
            completed = true;
            b.btnStartTest.setEnabled(true);
        });

        b.btnStartTest.setOnClickListener(v -> {
            Intent i = new Intent(this, TestActivity.class);
            i.putExtra("ws_number", wsNumber);
            startActivity(i);
        });
    }
}
