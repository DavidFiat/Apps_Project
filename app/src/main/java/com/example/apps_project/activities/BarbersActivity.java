package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apps_project.R;
import com.example.apps_project.adapters.BarbersAdapter;
import com.example.apps_project.adapters.RatesAdapter;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Rate;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarbersActivity extends AppCompatActivity implements BarbersAdapter.OnCitasBarber {

    private RecyclerView barbersRecycler, ratesRecycler;
    private LinearLayoutManager manager, managerRates;
    private BarbersAdapter adapter;
    private RatesAdapter ratesAdapter;
    private Barbershop barbershop;
    private TextView rateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barbers);
        adapter = new BarbersAdapter();
        ratesAdapter = new RatesAdapter();
        rateTV = findViewById(R.id.rateTV);

        barbersRecycler = findViewById(R.id.barbersRecycler);
        manager = new LinearLayoutManager(this);
        managerRates = new LinearLayoutManager(this);
        barbersRecycler.setLayoutManager(manager);
        barbersRecycler.setAdapter(adapter);
        barbersRecycler.setHasFixedSize(true);
        adapter.setOnCitasBarber(this);

        ratesRecycler = findViewById(R.id.ratesRecycler);
        ratesRecycler.setLayoutManager(managerRates);
        ratesRecycler.setAdapter(ratesAdapter);
        ratesRecycler.setHasFixedSize(true);

        barbershop  = (Barbershop) getIntent().getExtras().get("barbershop");

        getBarbers();
        getRates();
    }

    private void getRates() {
        FirebaseFirestore.getInstance().collection("barbershops").document(barbershop.getId()).collection("rates").get().addOnCompleteListener(
                task -> {
                    double averageRate = 0.0;
                    ratesAdapter.clear();
                    for (DocumentSnapshot doc : task.getResult()) {
                        Rate rate = doc.toObject(Rate.class);
                        ratesAdapter.addRate(rate);
                        averageRate += rate.getRate();
                    }
                    averageRate /= task.getResult().size();
                    rateTV.setText(averageRate+"");
                }
        );
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

    @Override
    public void onCitas(Barber barber) {

        Intent intent = new Intent(this,CitaActivity.class);
        intent.putExtra("barber",barber);
        intent.putExtra("barbershop",barbershop);
        startActivity(intent);

    }
}