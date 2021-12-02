package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.fragments.BarbershopsFragment;
import com.example.apps_project.fragments.ProfileFragment;
import com.example.apps_project.fragments.ReserveFragment;
import com.example.apps_project.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClientActivity extends AppCompatActivity {

    private Client client;
    private BottomNavigationView navigator;
    private BarbershopsFragment barbershopsFragment;
    private ProfileFragment profileFragment;
    private ReserveFragment reserveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        navigator = findViewById(R.id.navigator);
        barbershopsFragment = BarbershopsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        reserveFragment = ReserveFragment.newInstance();


        showFragment(barbershopsFragment);

        navigator.setOnItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.barbershopitem){
                        showFragment(barbershopsFragment);
                    } else if(menuItem.getItemId() == R.id.reserveitem){
                        showFragment(reserveFragment);
                    } else if(menuItem.getItemId() == R.id.perfilitem){
                        showFragment(profileFragment);
                    }
                    return true;
                }
        );

        FirebaseFirestore.getInstance().collection("clients").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
            document -> {
                client = document.toObject(Client.class);
                profileFragment.setClient(client);
                reserveFragment.setClient(client);
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