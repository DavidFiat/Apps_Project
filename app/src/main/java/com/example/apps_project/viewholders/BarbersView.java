package com.example.apps_project.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;

public class BarbersView extends RecyclerView.ViewHolder{

    private Barber barber;
    private ImageButton imageBarber;
    private TextView nameTV, rateTV;



    public BarbersView(@NonNull View itemView) {
        super(itemView);
        imageBarber = itemView.findViewById(R.id.imageBarbershop);
        nameTV = itemView.findViewById(R.id.nameTV);
        rateTV = itemView.findViewById(R.id.rateTV);
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public ImageButton getImageBarber() {
        return imageBarber;
    }

    public void setImageBarber(ImageButton imageBarber) {
        this.imageBarber = imageBarber;
    }

    public TextView getNameTV() {
        return nameTV;
    }

    public void setNameTV(TextView nameTV) {
        this.nameTV = nameTV;
    }

    public TextView getRateTV() {
        return rateTV;
    }

    public void setRateTV(TextView rateTV) {
        this.rateTV = rateTV;
    }
}
