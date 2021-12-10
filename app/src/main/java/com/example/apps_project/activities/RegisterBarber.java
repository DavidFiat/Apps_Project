package com.example.apps_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegisterBarber extends AppCompatActivity {

    private EditText nameET, emailET, passwordET, repasswordET;
    private Spinner spinner;
    private Button continueBtn;
    private Barbershop barbershop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_barber);
        spinner = findViewById(R.id.spinner);
        nameET = findViewById(R.id.nameTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        repasswordET = findViewById(R.id.repasswordET);
        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this::register);
        loadBarberShopsName();
    }

    public void loadBarberShopsName() {
        List<String> barberShopsName = new ArrayList<String>();
        FirebaseFirestore.getInstance().collection("barbershops").get().addOnCompleteListener(
                task ->{
                    for(DocumentSnapshot doc : task.getResult()){
                        Barbershop barbershop = doc.toObject(Barbershop.class);
                        barberShopsName.add(barbershop.getName());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                            RegisterBarber.this, android.R.layout.simple_dropdown_item_1line, barberShopsName);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String barbershopName = adapterView.getItemAtPosition(i).toString();
                            Query query = FirebaseFirestore.getInstance().collection("barbershops").whereEqualTo("name", barbershopName);
                            query.get().addOnCompleteListener(
                                    task -> {
                                        for (DocumentSnapshot b : task.getResult()) {
                                            barbershop = b.toObject(Barbershop.class);
                                            break;
                                        }
                                    }
                            );
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
        );
    }


    private void register(View view) {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String repassword = repasswordET.getText().toString();

        if (password.equals(repassword)) {
            //1. Registrarse en la db de auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                    task -> {
                        //2. Registrar al usuario en la base de datos
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        User user = new User(fbUser.getUid(), "barber");
                        Barber barber = new Barber(fbUser.getUid(), name, "", email, "Foto");
                        FirebaseFirestore.getInstance().collection("users").document(fbUser.getUid()).set(user);
                        FirebaseFirestore.getInstance().collection("barbers").document(fbUser.getUid()).set(barber).addOnSuccessListener(
                                firetask -> {
                                    finish();
                                }
                        );
                    }
            ).addOnFailureListener(
                    error -> {
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
    }


}