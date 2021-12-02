package com.example.apps_project.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Barbershop;

public class BarbershopView extends RecyclerView.ViewHolder{

    private Barbershop barbershop;
    private ImageButton imageBarbershop;
    private TextView nameTV, rateTV;


    public BarbershopView(@NonNull View itemView) {
        super(itemView);
        imageBarbershop = itemView.findViewById(R.id.imageBarber);
        nameTV = itemView.findViewById(R.id.nameTV);
        rateTV = itemView.findViewById(R.id.dateTV);

    }


    public Barbershop getBarbershop() {
        return barbershop;
    }

    public void setBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public ImageButton getImageBarbershop() {
        return imageBarbershop;
    }

    public void setImageBarbershop(ImageButton imageBarbershop) {
        this.imageBarbershop = imageBarbershop;
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
