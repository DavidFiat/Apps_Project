package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.model.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginClient extends AppCompatActivity {

    private TextView registerTV;
    private EditText emailET, passwordET;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);

        registerTV = findViewById(R.id.registerTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);

        registerTV.setOnClickListener(
                (v)->{
                    Intent intent = new Intent(this, RegisterClient.class);
                    startActivity(intent);
                }
        );

        loginBtn.setOnClickListener(this::login);
    }

    private void login(View view) {
        String email = emailET.getText().toString();
        String pass = passwordET.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(
                        task->{
                            FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                            if(fbuser.isEmailVerified()){
                                //Le damos acceso
                                Intent intent = new Intent(this, ClientActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(this, "Su email no está verificado", Toast.LENGTH_LONG).show();
                            }
                        }
                ).addOnFailureListener(
                    error->Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}