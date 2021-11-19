package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.apps_project.R;
import com.example.apps_project.model.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClientActivity extends AppCompatActivity {

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        FirebaseFirestore.getInstance().collection("clients").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
            document -> {
                client = document.toObject(Client.class);
            }
        );
    }
}