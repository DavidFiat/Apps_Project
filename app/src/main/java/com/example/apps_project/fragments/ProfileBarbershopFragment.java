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
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Client;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileBarbershopFragment extends Fragment {


    private Button exitBtn;
    private Barbershop barbershop;
    private TextView nameTV, emailTV;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        exitBtn = view.findViewById(R.id.exitBtn);
        emailTV = view.findViewById(R.id.emailTV);
        nameTV = view.findViewById(R.id.nameTV);
        emailTV.setText(barbershop.getEmail());
        nameTV.setText(barbershop.getName());
        exitBtn.setOnClickListener(this::exitBarbershop);
        return view;
    }

    private void exitBarbershop(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}