package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.google.firebase.storage.FirebaseStorage;

public class CitaActivity extends AppCompatActivity {

    private Barber barber;
    private Barbershop barbershop;

    private ImageView imgBarber;
    private TextView nameBarber,nameBTV,editTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        barber = (Barber)getIntent().getExtras().get("barber");
        barbershop = (Barbershop) getIntent().getExtras().get("barbershop");

        imgBarber= findViewById(R.id.imgBarber);
        nameBarber = findViewById(R.id.nameBarber);
        nameBTV = findViewById(R.id.nameBTV);
        editTV =findViewById(R.id.editTV);

        FirebaseStorage.getInstance().getReference().child("barbers").child(barber.getUrlImage()).getDownloadUrl().addOnSuccessListener(
                url->{
                    Glide.with(imgBarber).load(url).into(imgBarber);
                }
        );
        nameBarber.setText(barber.getName());
        nameBTV.setText(barbershop.getName());




    }
}