package com.example.apps_project.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apps_project.R;
import com.example.apps_project.fragments.BarbersFragment;
import com.example.apps_project.fragments.NewBarberFragment;
import com.example.apps_project.fragments.ProfileFragment;
import com.example.apps_project.model.Barbershop;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarbershopActivity extends AppCompatActivity {

    private Barbershop barbershop;
    private BottomNavigationView navigatorbarbershop;
    private BarbersFragment barbersFragment;
    private ProfileFragment profileFragment;
    private NewBarberFragment newBarberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop);

        navigatorbarbershop = findViewById(R.id.navigatorbarbershop);
        barbersFragment = BarbersFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        newBarberFragment = newBarberFragment.newInstance();


        showFragment(barbersFragment);

        navigatorbarbershop.setOnItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.barbersitem){
                        showFragment(barbersFragment);
                    } else if(menuItem.getItemId() == R.id.newbarberitem){
                        showFragment(newBarberFragment);
                    } else if(menuItem.getItemId() == R.id.perfilitem){
                        showFragment(profileFragment);
                    }
                    return true;
                }
        );

        FirebaseFirestore.getInstance().collection("barbershops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
                document -> {
                    barbershop = document.toObject(Barbershop.class);
                    profileFragment.setBarbershop(barbershop);
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
