package com.example.apps_project.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apps_project.R;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.viewholders.BarbersView;
import com.example.apps_project.viewholders.BarbershopView;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class BarbersAdapter extends  RecyclerView.Adapter<BarbersView>{

    private ArrayList<Barber> barbers;
    private OnCitasBarber onCitasBarber;


    public BarbersAdapter() {
        barbers = new ArrayList<>();
    }

    @NonNull
    @Override
    public BarbersView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.barbershop_row, parent, false);
        BarbersView skeleton = new BarbersView(row);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull BarbersView skeleton, int position) {

        Barber barber = barbers.get(position);
        skeleton.setBarber(barber);
        skeleton.getNameTV().setText(barber.getName());
        skeleton.getRateTV().setText(barber.getRate());
        if(barber.getUrlImage()!= null && !barber.getUrlImage().equals("")){
            FirebaseStorage.getInstance().getReference().child("barbers").child(barber.getUrlImage()).getDownloadUrl().addOnSuccessListener(
                    url->{
                        Glide.with(skeleton.getImageBarber()).load(url).into(skeleton.getImageBarber());
                    }
            );
        }
        skeleton.getImageBarber().setOnClickListener(

                v->{
                    onCitasBarber.onCitas(barber);
                }
        );

    }


    @Override
    public int getItemCount() {
       return barbers.size();
    }


    public void clear() {
        int size = barbers.size();
        barbers.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addBarbers(Barber barber){
        barbers.add(barber);
        notifyItemInserted(barbers.size()-1);
    }

    public interface OnCitasBarber{
         void onCitas(Barber barber);
    }

    public void setOnCitasBarber(OnCitasBarber onCitasBarber) {
        this.onCitasBarber = onCitasBarber;
    }
}
