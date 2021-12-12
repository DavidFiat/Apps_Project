package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.apps_project.R;
import com.example.apps_project.activities.FeedBack;
import com.example.apps_project.adapters.ReservesAdapter;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Client;
import com.example.apps_project.model.Rate;
import com.example.apps_project.model.Reserve;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReserveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserveFragment extends Fragment implements ReservesAdapter.OnFeedBack {

    private RecyclerView reservesRecycler;
    private LinearLayoutManager manager;
    private ReservesAdapter adapter;
    private Client client;


    public ReserveFragment() {
        adapter = new ReservesAdapter();
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
        reservesRecycler = view.findViewById(R.id.reservesRecycler);
        manager = new LinearLayoutManager(view.getContext());
        reservesRecycler.setLayoutManager(manager);
        adapter.setOnFeedBack(this);
        reservesRecycler.setAdapter(adapter);
        reservesRecycler.setHasFixedSize(true);

        getReserves();

        return view;
    }



    private void getReserves() {
        FirebaseFirestore.getInstance().collection("clients").document(client.getId()).collection("reserves").get().addOnCompleteListener(
                task ->{
                    adapter.clear();
                    for(DocumentSnapshot doc : task.getResult()){
                        Reserve reserve = doc.toObject(Reserve.class);
                        adapter.addReserve(reserve);
                    }
                }
        );
    }

    @Override
    public void onFeedBack(Reserve reserve) {
        Intent i  = new Intent(getContext(), FeedBack.class);
        i.putExtra("reserve",reserve);
        startActivity(i);
    }


}