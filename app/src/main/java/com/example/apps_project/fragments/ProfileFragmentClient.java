package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apps_project.R;
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Client;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragmentClient extends Fragment {


    private Button exitBtn;
    private Client client;
    private TextView nameTV, emailTV;

    public ProfileFragmentClient() {
        // Required empty public constructor
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static ProfileFragmentClient newInstance() {
        ProfileFragmentClient fragment = new ProfileFragmentClient();
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
        emailTV.setText(client.getEmail());
        nameTV.setText(client.getName());
        exitBtn.setOnClickListener(this::exitClient);
        return view;
    }

    private void exitClient(View view) {
        FirebaseAuth.getInstance().signOut();
        com.facebook.login.LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}