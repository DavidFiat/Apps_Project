package com.example.apps_project.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apps_project.R;
import com.example.apps_project.fragments.ProfileFragmentBarber;
import com.example.apps_project.fragments.ReserveFragment;
import com.example.apps_project.model.Barber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarberActivity extends AppCompatActivity {

    private Barber barber;
    private BottomNavigationView navigator;
    private ProfileFragmentBarber profileFragment;
    private ReserveFragment reserveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);

        navigator = findViewById(R.id.navigator);
        profileFragment = ProfileFragmentBarber.newInstance();
        reserveFragment = ReserveFragment.newInstance();

        navigator.setOnItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.reserveitem){
                        showFragment(reserveFragment);
                    } else if(menuItem.getItemId() == R.id.perfilitem){
                        showFragment(profileFragment);
                    }
                    return true;
                }
        );

        FirebaseFirestore.getInstance().collection("barbers").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
                document -> {
                    barber = document.toObject(Barber.class);
                    profileFragment.setbarber(barber);
                }
        );
    }


    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}