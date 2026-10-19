package com.example.progresslog.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progresslog.R;
import com.example.progresslog.adapter.PlanBlockAdapter;
import com.example.progresslog.model.PlanBlock;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private PlanBlockAdapter adapter;
    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        currentUserId = "test_user_123";
        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView rvBlocks = findViewById(R.id.rvBlocks);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new PlanBlockAdapter();
        rvBlocks.setLayoutManager(new LinearLayoutManager(this));
        rvBlocks.setAdapter(adapter);

        startListening();

        fabAdd.setOnClickListener(v -> {
            AddPlanBlockDialog dialog = new AddPlanBlockDialog();
            dialog.setListener(this::saveBlockToFirestore);
            dialog.show(getSupportFragmentManager(), "AddPlanBlockDialog");
        });
    }

    private void startListening() {
        listenerRegistration = db.collection("plan_blocks")
                .whereEqualTo("userId", currentUserId)
                .orderBy("dayOfWeek", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Ошибка загрузки: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (snapshots == null) return;

                    List<PlanBlock> blocks = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        PlanBlock block = doc.toObject(PlanBlock.class);
                        block.setId(doc.getId());
                        blocks.add(block);
                    }
                    adapter.setItems(blocks);
                });
    }

    private void saveBlockToFirestore(PlanBlock block) {
        block.setUserId(currentUserId);
        db.collection("plan_blocks")
                .add(block)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(this, "Блок сохранён", Toast.LENGTH_SHORT).show();
                    // Список обновится сам через snapshot listener
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove(); // Отписываемся, чтобы не было утечек
        }
    }
}