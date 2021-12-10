package com.example.apps_project.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps_project.R;
import com.example.apps_project.model.Reserve;

public class ReserveView extends RecyclerView.ViewHolder{

    private Reserve reserve;
    private ImageButton imageBarber;
    private TextView nameTV, dateTV;
    private Button rateBtn;

    public ReserveView(@NonNull View itemView) {
        super(itemView);
        imageBarber = itemView.findViewById(R.id.imageBarber);
        nameTV = itemView.findViewById(R.id.nameTV);
        dateTV = itemView.findViewById(R.id.dateTV);
        rateBtn = itemView.findViewById(R.id.rateBtn);
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
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

    public TextView getDateTV() {
        return dateTV;
    }

    public void setDateTV(TextView dateTV) {
        this.dateTV = dateTV;
    }

    public Button getRateBtn() {
        return rateBtn;
    }

    public void setRateBtn(Button rateBtn) {
        this.rateBtn = rateBtn;
    }
}
