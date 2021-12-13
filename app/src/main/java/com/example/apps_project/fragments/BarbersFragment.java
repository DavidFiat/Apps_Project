package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.activities.BarbersActivity;
import com.example.apps_project.adapters.BarbersAdapter;
import com.example.apps_project.adapters.BarbershopsAdapter;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BarbersFragment extends Fragment implements  BarbershopsAdapter.OnListBarbers{

    private RecyclerView barbersRecycler;
    private LinearLayoutManager manager;
    private BarbersAdapter barbersAdapter;

    public BarbersFragment() {
        barbersAdapter = new BarbersAdapter();
    }

    public static BarbersFragment newInstance() {
        BarbersFragment fragment = new BarbersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barbers, container, false);

        barbersRecycler = view.findViewById(R.id.barbersRecycler);
        manager = new LinearLayoutManager(view.getContext());
        barbersRecycler.setLayoutManager(manager);
        barbersRecycler.setAdapter(barbersAdapter);
        barbersRecycler.setHasFixedSize(true);
        getBarbers();
        return view;
    }

    public void getBarbers() {
        FirebaseFirestore.getInstance().collection("barbers").get().addOnCompleteListener(
                task ->{
                    barbersAdapter.clear();
                    for(DocumentSnapshot doc : task.getResult()){
                        Barber barber = doc.toObject(Barber.class);
                        barbersAdapter.addBarbers(barber);
                    }
                }
        );
    }

    @Override
    public void onListBarbers(Barbershop barbershop) {
        Intent i = new Intent(getContext(), BarbersActivity.class);
        i.putExtra("barbershop",barbershop);
        startActivity(i);
    }

}
