package com.example.apps_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apps_project.R;
import com.example.apps_project.activities.BarbersActivity;
import com.example.apps_project.adapters.BarbershopsAdapter;
import com.example.apps_project.model.Barbershop;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarbershopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarbershopsFragment extends Fragment implements BarbershopsAdapter.OnListBarbers {


    private RecyclerView barbershopsRecycler;
    private LinearLayoutManager manager;
    private BarbershopsAdapter adapter;

    public BarbershopsFragment() {
        adapter = new BarbershopsAdapter();
    }

    public static BarbershopsFragment newInstance() {
        BarbershopsFragment fragment = new BarbershopsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barbershops, container, false);

        barbershopsRecycler = view.findViewById(R.id.reservesRecycler);
        manager = new LinearLayoutManager(view.getContext());
        barbershopsRecycler.setLayoutManager(manager);
        barbershopsRecycler.setAdapter(adapter);
        barbershopsRecycler.setHasFixedSize(true);
        adapter.setOnListBarbers(this);
        getBarbershops();
        return view;
    }

    private void getBarbershops() {
        FirebaseFirestore.getInstance().collection("barbershops").get().addOnCompleteListener(
                task ->{
                    adapter.clear();
                    for(DocumentSnapshot doc : task.getResult()){
                        Barbershop barbershop = doc.toObject(Barbershop.class);
                        adapter.addBarbershop(barbershop);
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