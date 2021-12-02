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
import com.example.apps_project.model.Barber;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragmentBarber extends Fragment {


    private Button exitBtn;
    private Barber barber;
    private TextView nameTV, pointTV;

    public ProfileFragmentBarber() {
    }

    public void setbarber(Barber barber) {
        this.barber = barber;
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
        exitBtn = view.findViewById(R.id.exitBtn);
        pointTV = view.findViewById(R.id.pointTV);
        nameTV = view.findViewById(R.id.nameTV);

        pointTV.setText(barber.getRate());
        nameTV.setText(barber.getName());
        exitBtn.setOnClickListener(this::exitbarber);
        return view;
    }

    private void exitbarber(View view) {
        FirebaseAuth.getInstance().signOut();
        com.facebook.login.LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

}