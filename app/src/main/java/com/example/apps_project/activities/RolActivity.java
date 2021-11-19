package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.apps_project.R;

public class RolActivity extends AppCompatActivity {

    private ImageButton clientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);

        clientBtn = findViewById(R.id.clientBtn);

        clientBtn.setOnClickListener(
                (v)->{
                    Intent intent = new Intent(this, LoginClient.class);
                    startActivity(intent);
                }
        );
    }
}