package com.example.apps_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.adapters.BarbersAdapter;
import com.example.apps_project.adapters.BarbershopsAdapter;
import com.example.apps_project.adapters.RatesAdapter;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Rate;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarbershopsActivity extends AppCompatActivity implements BarbershopsAdapter.OnListBarbers {

    private RecyclerView barbersRecycler;
    private RecyclerView ratesRecycler;
    private LinearLayoutManager manager;
    private LinearLayoutManager managerRates;
    private BarbersAdapter adapter;
    private RatesAdapter ratesAdapter;
    private Barbershop barbershop;
    private Barber barber;
    private TextView rateTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barbershop);
        adapter = new BarbersAdapter();
        ratesAdapter = new RatesAdapter();
        rateTV = findViewById(R.id.rateTV);

        barbersRecycler = findViewById(R.id.barbersRecycler);
        ratesRecycler = findViewById(R.id.ratesRecyclerView);

        manager = new LinearLayoutManager(this);
        managerRates = new LinearLayoutManager(this);

        ratesRecycler.setLayoutManager(managerRates);
        barbersRecycler.setLayoutManager(manager);
        barbersRecycler.setAdapter(adapter);
        ratesRecycler.setAdapter(ratesAdapter);
        barbersRecycler.setHasFixedSize(true);
        ratesRecycler.setHasFixedSize(true);
        //adapter.setOnCitasBarber(this);

        barbershop  = (Barbershop) getIntent().getExtras().get("barbershop");

        getBarbers();
        getRates();
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
    private void getRates(){
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

    @Override
    public void onListBarbers(Barbershop barbershop) {
        Intent intent = new Intent(this,BarbershopActivity.class);
        intent.putExtra("barber",barber);
        intent.putExtra("barbershop",barbershop);
        startActivity(intent);
    }
}
