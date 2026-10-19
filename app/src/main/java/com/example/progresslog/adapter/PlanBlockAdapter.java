package com.example.progresslog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progresslog.R;
import com.example.progresslog.model.PlanBlock;

import java.util.ArrayList;
import java.util.List;

public class PlanBlockAdapter extends RecyclerView.Adapter<PlanBlockAdapter.VH> {

    private final List<PlanBlock> items = new ArrayList<>();

    public void setItems(List<PlanBlock> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void add(PlanBlock block) {
        items.add(block);
        notifyItemInserted(items.size() - 1);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan_block, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private final TextView tvDay;
        private final TextView tvMuscles;
        private final TextView tvExercises;

        VH(@NonNull View itemView) {
            super(itemView);
            // Находим элементы по ID из item_plan_block.xml
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMuscles = itemView.findViewById(R.id.tvMuscles);
            tvExercises = itemView.findViewById(R.id.tvExercises);
        }

        void bind(PlanBlock block) {
            tvDay.setText(block.getDayOfWeek());

            String muscles = block.getMuscleGroups();
            tvMuscles.setText(muscles == null || muscles.isEmpty()
                    ? "Группы мышц не заданы" : muscles);

            List<String> exercises = block.getExercises();
            if (exercises == null || exercises.isEmpty()) {
                tvExercises.setText("Упражнения не заданы");
                tvExercises.setAlpha(0.5f);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < exercises.size(); i++) {
                    sb.append("• ").append(exercises.get(i));
                    if (i < exercises.size() - 1) sb.append("\n");
                }
                tvExercises.setText(sb.toString());
                tvExercises.setAlpha(1f);
            }

            itemView.setOnClickListener(v -> {
                Toast.makeText(v.getContext(),
                        "Скоро: подробности \"" + block.getDayOfWeek() + "\"",
                        Toast.LENGTH_SHORT).show();
            });
        }
    }
}