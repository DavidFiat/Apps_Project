package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apps_project.R;
import com.example.apps_project.fragments.DateDialogFragment;
import com.example.apps_project.fragments.ReserveFragment;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Reserve;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CitaActivity extends AppCompatActivity {

    private Barber barber;
    private Barbershop barbershop;

    private ImageView imgBarber;
    private TextView nameBarber,nameBTV,citaTV;
    private Button agendarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        barber = (Barber)getIntent().getExtras().get("barber");
        barbershop = (Barbershop) getIntent().getExtras().get("barbershop");

        imgBarber= findViewById(R.id.imgBarber);
        nameBarber = findViewById(R.id.nameBarber);
        nameBTV = findViewById(R.id.nameBTV);
        citaTV =findViewById(R.id.citaTV);
        agendarBtn=findViewById(R.id.agendarBtn);

        FirebaseStorage.getInstance().getReference().child("barbers").child(barber.getUrlImage()).getDownloadUrl().addOnSuccessListener(
                url->{
                    Glide.with(imgBarber).load(url).into(imgBarber);
                }
        );
        nameBarber.setText(barber.getName());
        nameBTV.setText(barbershop.getName());
        
        
        citaTV.setOnClickListener(this::agendarCita);
        agendarBtn.setOnClickListener(this::reserve);

        




    }

    private void reserve(View view) {

        Reserve reserve = new Reserve(UUID.randomUUID().toString(),barber.getName(),barber.getUrlImage(),citaTV.getText().toString());
        FirebaseFirestore.getInstance().collection("barbershops").document(barbershop.getId()).collection("barbers").
                document(barber.getId()).collection("reserves").document(reserve.getId()).set(reserve);
        runOnUiThread(()->{
            Toast.makeText(view.getContext(),"Se ha reservado tu cita exitosamente!",Toast.LENGTH_LONG).show();

        });
        Intent intent= new Intent(view.getContext(),ClientActivity.class);
        startActivity(intent);
        finish();
    }


    private void agendarCita(View view) {

        showDatePicker(date->{
            citaTV.setText(formatDate(date));
        });
    }


    private String formatDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(new Date(date));
    }

    private void showDatePicker(DateDialogFragment.OnDateSelectedListener listener) {
        DateDialogFragment dialog = new DateDialogFragment();
        dialog.setListener(listener);
        dialog.show(this.getSupportFragmentManager(), "dialog");
    }

}