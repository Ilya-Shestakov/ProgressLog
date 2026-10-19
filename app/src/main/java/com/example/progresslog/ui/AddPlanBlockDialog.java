package com.example.progresslog.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.progresslog.R;
import com.example.progresslog.model.PlanBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPlanBlockDialog extends DialogFragment {

    public interface OnSaveListener {
        void onSave(PlanBlock block);
    }

    private OnSaveListener listener;

    public void setListener(OnSaveListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_plan_block, null);

        EditText etDay = view.findViewById(R.id.etDay);
        EditText etMuscles = view.findViewById(R.id.etMuscles);
        EditText etExercises = view.findViewById(R.id.etExercises);

        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_add_plan_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_save, (d, which) -> {
                    String day = etDay.getText().toString().trim();
                    String muscles = etMuscles.getText().toString().trim();
                    String exercisesRaw = etExercises.getText().toString().trim();

                    if (TextUtils.isEmpty(day)) {
                        etDay.setError(getString(R.string.error_field_required));
                        return;
                    }

                    List<String> exercises = new ArrayList<>();
                    if (!TextUtils.isEmpty(exercisesRaw)) {
                        // Разделяем упражнения по запятой или переносу строки
                        exercises.addAll(Arrays.asList(exercisesRaw.split("[,\n]")));
                        // Убираем пустые строки и пробелы
                        List<String> cleaned = new ArrayList<>();
                        for (String e : exercises) {
                            if (!e.trim().isEmpty()) cleaned.add(e.trim());
                        }
                        exercises = cleaned;
                    }

                    PlanBlock block = new PlanBlock(day, muscles, exercises);
                    if (listener != null) {
                        listener.onSave(block);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();
    }
}