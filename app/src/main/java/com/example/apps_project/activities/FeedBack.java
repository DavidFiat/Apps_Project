package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.model.Client;
import com.example.apps_project.model.Rate;
import com.example.apps_project.model.Reserve;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class FeedBack extends AppCompatActivity {

    private Reserve reserve;
    private EditText rateET,feedBackET;
    private Button addBtn;
    private OnFeed onFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        rateET= findViewById(R.id.rateET);
        feedBackET = findViewById(R.id.feedbackET);
        addBtn =findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::feedBack);
        reserve = (Reserve) getIntent().getExtras().get("reserve");



    }

    private void feedBack(View view) {

        double rate = Double.parseDouble(rateET.getText().toString());
        if (rate>=1 && rate<=5){
            Toast.makeText(this,"Se agrego el comentario"+rate,Toast.LENGTH_LONG).show();
            Rate rate1 = new Rate(UUID.randomUUID().toString(),feedBackET.getText().toString(),rate);
            FirebaseFirestore.getInstance().collection("barbershops").document(reserve.getIdBarberShop()).collection("barbers").document(reserve.getIdBarbero()).collection("rates").document(rate1.getId()).set(rate1);
            //onFeed.onFeed(rate1);

            Intent i = new Intent(this,ClientActivity.class);
            startActivity(i);

        }else{
            Toast.makeText(this,"Por favor ingresa un numero del 1 al 5",Toast.LENGTH_LONG).show();

        }



    }

    public interface OnFeed{

        void onFeed(Rate rate);

    }

    public void setOnFeed(OnFeed onFeed) {
        this.onFeed = onFeed;
    }
}