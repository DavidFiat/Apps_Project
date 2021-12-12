package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import com.example.apps_project.R;
import com.example.apps_project.adapters.RatesAdapter;
import com.example.apps_project.model.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class RatesActivity extends AppCompatActivity {

    private Barber barber;
    private Barbershop barbershop;
    private RecyclerView ratesRecyclerView;
    private LinearLayoutManager manager;
    private RatesAdapter adapter;
    private Button returnBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        ratesRecyclerView = findViewById(R.id.ratesRecyclerView);
        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(
                view -> {
                    finish();
                }
        );
        adapter = new RatesAdapter();
        manager = new LinearLayoutManager(this);


        ratesRecyclerView.setLayoutManager(manager);
        ratesRecyclerView.setAdapter(adapter);
        String type = (String) getIntent().getExtras().get("type");
        barbershop = (Barbershop) getIntent().getExtras().get("barbershop");

        if (type.equals("barber")) {
         barber = (Barber) getIntent().getExtras().get("barber");
            FirebaseFirestore.getInstance().collection("barbershops").document(barbershop.getId()).collection("barbers").document(barber.getId()).collection("rates").get().addOnCompleteListener(
                    task -> {
                        this.createList(task);
                    }
            );
        } else {
           FirebaseFirestore.getInstance().collection("barbershops").document(barbershop.getId()).collection("rates").get().addOnCompleteListener(
                    task -> {
                        this.createList(task);
                    }
            );
        }


    }

    private void createList(Task<QuerySnapshot> task) {
        adapter.clear();

        for (DocumentSnapshot doc : task.getResult()) {
            Rate rate = doc.toObject(Rate.class);
            adapter.addRate(rate);
        }

    }
}