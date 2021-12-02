package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.apps_project.R;

public class RolActivity extends AppCompatActivity {

    private ImageButton clientBtn;
    private ImageButton barberiaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);

        clientBtn = findViewById(R.id.clientBtn);
        barberiaBtn = findViewById(R.id.barberiaBtn);

        clientBtn.setOnClickListener(
                (v)->{

                    Intent intent = new Intent(this, LoginClient.class);
                    startActivity(intent);

                }

        );

        barberiaBtn.setOnClickListener(
                (v)->{
                    Log.i("", "Barberia");
                    Intent intent = new Intent(this, LoginBarbershop.class);
                    startActivity(intent);
                }
        );
    }
}