package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apps_project.R;
import com.example.apps_project.activities.BarberActivity;
import com.example.apps_project.activities.RatesActivity;
import com.example.apps_project.activities.RegisterBarber;
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragmentBarber extends Fragment {


    private Button exitBtn,ratesButton;
    private Barber barber;
    private Barbershop barbershop;
    private TextView nameTV, pointTV;
    private ImageView image;

    public ProfileFragmentBarber() {
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }
    public void setBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }


    public static ProfileFragmentBarber newInstance() {
        ProfileFragmentBarber fragment = new ProfileFragmentBarber();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_barber, container, false);
        FirebaseFirestore.getInstance().collection("barbershops").document(barber.getBarberShopId()).get().addOnSuccessListener(
                document -> {
                    barbershop = document.toObject(Barbershop.class);
                }
        );
        exitBtn = view.findViewById(R.id.exitBtn);
        ratesButton = view.findViewById(R.id.ratesButton);
        pointTV = view.findViewById(R.id.pointTV);
        nameTV = view.findViewById(R.id.nameTV);
        image = view.findViewById(R.id.image);

        pointTV.setText(barber.getRate());
        nameTV.setText(barber.getName());
        ratesButton.setOnClickListener(this::seeRates);
        exitBtn.setOnClickListener(this::exitBarber);
        return view;
    }

    private void seeRates(View view) {
        Intent intent = new Intent(view.getContext(), RatesActivity.class);
        intent.putExtra("type","barber");
        intent.putExtra("barbershop", barbershop);
        intent.putExtra("barber",barber);
        startActivity(intent);
    }

    private void exitBarber(View view) {
        FirebaseAuth.getInstance().signOut();
        com.facebook.login.LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}