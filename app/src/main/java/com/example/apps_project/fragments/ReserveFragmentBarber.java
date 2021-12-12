package com.example.apps_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.adapters.ReservesAdapter;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Reserve;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReserveFragmentBarber extends Fragment {

    private RecyclerView reservesRecyclerBarber;
    private LinearLayoutManager managerBarber;
    private ReservesAdapter adapterBarber;
    private Barber barber;

    public ReserveFragmentBarber() {
        adapterBarber = new ReservesAdapter();
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public static ReserveFragmentBarber newInstance() {
        ReserveFragmentBarber fragment = new ReserveFragmentBarber();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_barber, container, false);

        reservesRecyclerBarber = view.findViewById(R.id.reservesRecyclerBarber);
        managerBarber = new LinearLayoutManager(view.getContext());
        reservesRecyclerBarber.setLayoutManager(managerBarber);
        reservesRecyclerBarber.setAdapter(adapterBarber);
        reservesRecyclerBarber.setHasFixedSize(true);
        getReserves();
        return view;
    }

    private void getReserves() {
        FirebaseFirestore.getInstance().collection("barbershops").document(barber.getBarberShopId()).collection("barbers").document(barber.getId()).collection("reserves").get().addOnCompleteListener(
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
