package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.apps_project.R;
import com.example.apps_project.activities.BarbershopsActivity;
import com.example.apps_project.activities.CitaActivity;
import com.example.apps_project.activities.RatesActivity;
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileBarbershopFragment extends Fragment {


    private Button exitBarbershopBtn, rateBarbershopBtn;
    private Barbershop barbershop;
    private TextView nameBarbershopTV, emailBarbershopTV;

    public ProfileBarbershopFragment() {
        // Required empty public constructor
    }

    public void setBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public static ProfileBarbershopFragment newInstance() {
        ProfileBarbershopFragment fragment = new ProfileBarbershopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_barbershop, container, false);
        exitBarbershopBtn = view.findViewById(R.id.exitBarbershopBtn);
        rateBarbershopBtn = view.findViewById(R.id.ratesBarbershopBtn);
        emailBarbershopTV = view.findViewById(R.id.emailBarbershopTV);
        nameBarbershopTV = view.findViewById(R.id.nameBarbershopTV);
        emailBarbershopTV.setText(barbershop.getEmail());
        nameBarbershopTV.setText(barbershop.getName());
        exitBarbershopBtn.setOnClickListener(this::exitBarbershop);
        rateBarbershopBtn.setOnClickListener(this::ratesBarbershop);
        return view;
    }

    public void ratesBarbershop(View view) {
        //user = (User) getActivity().getIntent().getExtras().get("barbershop");
        Intent intent = new Intent(this.getContext(), RatesActivity.class);
        intent.putExtra("barbershop",barbershop);
        intent.putExtra("type","barbershop");
        startActivity(intent);
    }

    private void exitBarbershop(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}