package com.example.apps_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Client;
import com.example.apps_project.model.Reserve;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReserveFragmentBarber extends Fragment {

    private RecyclerView reservesRecyvlerBarber;
    private LinearLayoutManager managerBarber;
    private ReservesAdapter adapterBarber;
    private Client client;

    public ReserveFragment() {
        adapterBarber = new ReservesAdapter();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static ReserveFragment newInstance() {
        ReserveFragment fragment = new ReserveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve, container, false);

        reservesRecyvlerBarber = view.findViewById(R.id.reservesRecyvlerBarber);
        managerBarber = new LinearLayoutManager(view.getContext());
        reservesRecyvlerBarber.setLayoutManager(managerBarber);
        reservesRecyvlerBarber.setAdapter(adapterBarber);
        reservesRecyvlerBarber.setHasFixedSize(true);
        getReserves();
        return view;
    }

    private void getReserves() {
        FirebaseFirestore.getInstance().collection("clients").document(client.getId()).collection("reserves").get().addOnCompleteListener(
                task ->{
                    adapterBarber.clear();
                    for(DocumentSnapshot doc : task.getResult()){
                        Reserve reserve = doc.toObject(Reserve.class);
                        adapterBarber.addReserve(reserve);
                    }
                }
        );
    }

}
