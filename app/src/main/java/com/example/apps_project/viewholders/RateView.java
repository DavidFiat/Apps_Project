package com.example.apps_project.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Rate;

public class RateView extends RecyclerView.ViewHolder{

    private Rate rate;
    private TextView opinionTV, rateTV;


    public RateView(@NonNull View itemView) {
        super(itemView);
        opinionTV = itemView.findViewById(R.id.opinionTV);
        rateTV = itemView.findViewById(R.id.rateTV);
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public TextView getOpinionTV() {
        return opinionTV;
    }

    public void setOpinionTV(TextView opinionTV) {
        this.opinionTV = opinionTV;
    }

    public TextView getRateTV() {
        return rateTV;
    }

    public void setRateTV(TextView rateTV) {
        this.rateTV = rateTV;
    }
}
