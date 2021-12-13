package com.example.apps_project.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apps_project.R;
import com.example.apps_project.fragments.ProfileFragmentBarber;
import com.example.apps_project.fragments.ReserveFragmentBarber;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class BarberActivity extends AppCompatActivity {

    private Barber barber;
   private BottomNavigationView navigatorBarber;
    private ProfileFragmentBarber profileFragmentBarber;
    private ReserveFragmentBarber reserveFragmentBarber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);
        FirebaseFirestore.getInstance().collection("barbers").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
                document -> {
                    barber = document.toObject(Barber.class);
                    profileFragmentBarber.setBarber(barber);
                    reserveFragmentBarber.setBarber(barber);
                }
        );


        navigatorBarber = findViewById(R.id.navigatorBarber);
        profileFragmentBarber = ProfileFragmentBarber.newInstance();
        reserveFragmentBarber = ReserveFragmentBarber.newInstance();
        Toast.makeText(this, "Please select a fragment first", Toast.LENGTH_SHORT).show();

        navigatorBarber.setOnItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.citasItem){
                        showFragment(reserveFragmentBarber);
                    } else if(menuItem.getItemId() == R.id.perfilBarberItem){
                        showFragment(profileFragmentBarber);
                    }
                    return true;
                }
        );





    }



    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerBarber, fragment);
        transaction.commit();
    }
}