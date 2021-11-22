package com.example.apps_project.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Barbershop;

import java.util.ArrayList;

public class BarbershopsAdapter extends RecyclerView.Adapter<BarbershopView>{

    private ArrayList<Barbershop> barbershops;

    public BarbershopsAdapter() {
        barbershops = new ArrayList<>();
    }

    @NonNull
    @Override
    public BarbershopView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.barbershop_row, parent, false);
        BarbershopView skeleton = new BarbershopView(row);
        return skeleton;

    }

    @Override
    public void onBindViewHolder(@NonNull BarbershopView skeleton, int position) {
        Barbershop barbershop = barbershops.get(position);
        skeleton.setBarbershop(barbershop);
        skeleton.getNameTV().setText(barbershop.getName());
        skeleton.getRateTV().setText(barbershop.getRate());
        //skeleton.getImageBarbershop().setImageURI(Uri.parse(barbershop.getUrlImage()));
    }

    @Override
    public int getItemCount() {
        return barbershops.size();
    }

    public void addBarbershop(Barbershop barbershop){
        barbershops.add(barbershop);
        notifyItemInserted(barbershops.size()-1);
    }
}
