package com.example.apps_project.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.apps_project.R;
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewBarberFragment extends Fragment {


    private EditText nameTV, emailTV, passwordET, repasswordET;
    private Barbershop barbershop; //Se supone que es la barbería que está asociada a este nuevo barbero
    private Barber barber;
    private Button continueBtn;


    public NewBarberFragment() {
        // Required empty public constructor
    }



    public static NewBarberFragment newInstance() {
        NewBarberFragment fragment = new NewBarberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_barber, container, false);
        continueBtn = view.findViewById(R.id.continueBtn);
        emailTV = view.findViewById(R.id.emailET);
        nameTV = view.findViewById(R.id.nameTV);
        passwordET = view.findViewById(R.id.passwordET);
        repasswordET = view.findViewById(R.id.repasswordET);
        continueBtn.setOnClickListener(this::register);

        return view;
    }

    private void register(View view) {
        String name = nameTV.getText().toString();
        String email = emailTV.getText().toString();
        String password = passwordET.getText().toString();
        String repassword = repasswordET.getText().toString();

        if(password.equals(repassword)){
            //1. Registrarse en la db de auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                    task->{
                        //2. Registrar al usuario en la base de datos
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        User user = new User(fbUser.getUid(), "client");
                        Barber barber = new Barber(fbUser.getUid(), name, "0", email, "", barbershop.getId());
                        FirebaseFirestore.getInstance().collection("users").document(fbUser.getUid()).set(user);
                        //Estamos dudando de este, por eso le vamos a preguntar al profe por el de abajo
                        FirebaseFirestore.getInstance().collection("barbers").document(fbUser.getUid()).set(barber).addOnSuccessListener(
                                firetask->{
                                    getActivity().finish();
                                }
                        );

                        //Preguntarle al profe si esto sirve - para guardar un barbero en una barbería
                        /*FirebaseFirestore.getInstance().collection("barbershops").document(fbUser.getUid()).collection("barbers").document((fbUser.getUid())).set(barber).addOnSuccessListener(
                                firetask->{
                                    finish();
                                }
                        );*/

                    }
            ).addOnFailureListener(
                    error->{
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        } else{
            Toast.makeText(view.getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
    }


    private void exitClient(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}