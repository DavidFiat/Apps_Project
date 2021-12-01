package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterBarber extends AppCompatActivity {

    private EditText nameET, emailET, passwordET, repasswordET;
    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_barber);

        nameET = findViewById(R.id.nameTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        repasswordET = findViewById(R.id.repasswordET);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(this::register);
    }

    private void register(View view) {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String repassword = repasswordET.getText().toString();

        if(password.equals(repassword)){
            //1. Registrarse en la db de auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                    task->{
                        //2. Registrar al usuario en la base de datos
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        User user = new User(fbUser.getUid(), "barber");
                        Barber barber = new Barber(fbUser.getUid(), name,"", email,"");
                        FirebaseFirestore.getInstance().collection("users").document(fbUser.getUid()).set(user);
                        FirebaseFirestore.getInstance().collection("barbers").document(fbUser.getUid()).set(barber).addOnSuccessListener(
                                firetask->{
                                    finish();
                                }
                        );
                    }
            ).addOnFailureListener(
                    error->{
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        } else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
    }
}