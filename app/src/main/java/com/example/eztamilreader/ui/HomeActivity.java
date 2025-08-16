package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.eztamilreader.databinding.ActivityHomeBinding;
import com.example.eztamilreader.util.Prefs;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding b;
    WorksheetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.btnSubscribe.setOnClickListener(v -> {
            startActivity(new Intent(this, SubscriptionActivity.class));
        });

        b.rvWorksheets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorksheetsAdapter(buildList(), wsNumber -> {
            boolean unlocked = (wsNumber <= 2) || Prefs.isSubscribed(this);
            if (unlocked) {
                Intent i = new Intent(this, WorksheetActivity.class);
                i.putExtra("ws_number", wsNumber);
                startActivity(i);
            } else {
                startActivity(new Intent(this, SubscriptionActivity.class));
            }
        });
        b.rvWorksheets.setAdapter(adapter);
    }

    private List<Item> buildList() {
        List<Item> list = new ArrayList<>();
        boolean isSub = Prefs.isSubscribed(this);
        for (int i = 1; i <= 50; i++) {
            boolean unlocked = isSub || i <= 2;
            list.add(new Item(i, "Worksheet " + i, unlocked));
        }
        return list;
    }

    public static class Item {
        public int number;
        public String title;
        public boolean unlocked;
        public Item(int n, String t, boolean u){ number=n; title=t; unlocked=u; }
    }
}
