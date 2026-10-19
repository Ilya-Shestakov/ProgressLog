package com.example.progresslog;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.progresslog.ui.PlanActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout btnPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlan = findViewById(R.id.btnPlan);

        btnPlan.setOnClickListener(v ->
                startActivity(new Intent(this, PlanActivity.class)));
    }
}