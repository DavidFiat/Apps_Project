package com.example.apps_project.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.adapters.BarbersAdapter;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarbershopsActivity extends AppCompatActivity {

    private RecyclerView barbersRecycler;
    private LinearLayoutManager manager;
    private BarbersAdapter adapter;
    private Barbershop barbershop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barbers);
        adapter = new BarbersAdapter();



        barbersRecycler = findViewById(R.id.barbersRecycler);
        manager = new LinearLayoutManager(this);
        barbersRecycler.setLayoutManager(manager);
        barbersRecycler.setAdapter(adapter);
        barbersRecycler.setHasFixedSize(true);
        //adapter.setOnCitasBarber(this);

        barbershop  = (Barbershop) getIntent().getExtras().get("barbershop");


        getBarbers();


    }

    private void getBarbers() {
        FirebaseFirestore.getInstance().collection("barbershops").document(barbershop.getId()).collection("barbers").get().addOnCompleteListener(
                task -> {
                    adapter.clear();
                    for (DocumentSnapshot doc : task.getResult()) {
                        Barber barber = doc.toObject(Barber.class);
                        adapter.addBarbers(barber);
                    }
                }
        );

    }
}
