package com.example.apps_project.adapters;

import static androidx.core.content.ContextCompat.startActivities;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apps_project.R;
import com.example.apps_project.activities.BarbersActivity;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Rate;
import com.example.apps_project.viewholders.BarbershopView;
import com.example.apps_project.viewholders.RateView;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class RatesAdapter extends RecyclerView.Adapter<RateView>{

    private ArrayList<Rate> rates;

    public RatesAdapter() {
        rates = new ArrayList<>();
    }

    @NonNull
    @Override
    public RateView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.rate_row, parent, false);
        RateView skeleton = new RateView(row);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull RateView skeleton, int position) {
        Rate rate = rates.get(position);
        skeleton.setRate(rate);
        skeleton.getRateTV().setText(rate.getRate()+"");
        skeleton.getOpinionTV().setText(rate.getOpinion());
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }

    public void setRates(ArrayList<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    public void addRate(Rate rate){
        rates.add(rate);
        notifyItemInserted(rates.size()-1);
    }

    public void clear() {
        int size = rates.size();
        rates.clear();
        notifyItemRangeRemoved(0, size);
    }
}
