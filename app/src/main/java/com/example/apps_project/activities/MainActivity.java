package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apps_project.R;
import com.example.apps_project.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User loadedUser = null;
        if(FirebaseAuth.getInstance().getCurrentUser() == null || !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            Intent intent = new Intent(this, RolActivity.class);
            startActivity(intent);
            finish();
            return;
        }else{
            switch (loadedUser.getRol()){
                case "client":
                    Intent intent = new Intent(this, RolActivity.class);
                    intent.putExtra("client", loadedUser);
                    startActivity(intent);
                    break;
            }
        }
    }
}