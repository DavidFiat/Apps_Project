package com.example.apps_project.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apps_project.fragments.ProfileFragmentBarber;
import com.example.apps_project.model.Barber;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BarberActivity extends AppCompatActivity {

    private Barber barber;
    private BottomNavigationView navigatorBarber;
    private ProfileFragmentBarber profileFragment;
    private ReserveFragment reserveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);

        navigator = findViewById(R.id.navigator);
        barbershopsFragment = BarbershopsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        reserveFragment = ReserveFragment.newInstance();


        showFragment(barbershopsFragment);

        navigator.setOnItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.barbersitem){
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
                    barber = document.toObject(Barber.class);
                    profileFragment.setBarber(barber);
                    reserveFragment.setBarber(barber);
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