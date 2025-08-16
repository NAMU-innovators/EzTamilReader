package com.example.eztamilreader.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eztamilreader.databinding.ItemWorksheetBinding;
import java.util.List;

public class WorksheetsAdapter extends RecyclerView.Adapter<WorksheetsAdapter.VH> {

    public interface OnClick { void open(int wsNumber); }

    private final List<HomeActivity.Item> data;
    private final OnClick onClick;

    public WorksheetsAdapter(List<HomeActivity.Item> d, OnClick c) {
        data = d; onClick = c;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemWorksheetBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        HomeActivity.Item it = data.get(pos);
        h.b.tvTitle.setText(it.title);
        h.b.tvLock.setText(it.unlocked ? "Unlocked" : "Locked");
        h.itemView.setAlpha(it.unlocked ? 1f : 0.6f);
        h.itemView.setOnClickListener(v -> onClick.open(it.number));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ItemWorksheetBinding b;
        VH(ItemWorksheetBinding binding){ super(binding.getRoot()); b=binding; }
    }
}
