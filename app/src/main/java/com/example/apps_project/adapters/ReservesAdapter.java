package com.example.apps_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apps_project.R;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.Reserve;
import com.example.apps_project.viewholders.BarbershopView;
import com.example.apps_project.viewholders.ReserveView;
import com.google.firebase.storage.FirebaseStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservesAdapter extends RecyclerView.Adapter<ReserveView>{

    private ArrayList<Reserve> reserves;

    public ReservesAdapter() {
        reserves = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReserveView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.reserve_row, parent, false);
        ReserveView skeleton = new ReserveView(row);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveView skeleton, int position) {
        Reserve reserve = reserves.get(position);
        skeleton.setReserve(reserve);
        skeleton.getNameTV().setText(reserve.getName());
        skeleton.getDateTV().setText(reserve.getDate());
        FirebaseStorage.getInstance().getReference().child("barbers").child(reserve.getUrlImageBarber()).getDownloadUrl().addOnSuccessListener(
                url->{
                    Glide.with(skeleton.getImageBarber()).load(url).into(skeleton.getImageBarber());
                }
        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(reserve.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        long now = Calendar.getInstance().getTimeInMillis();
        if(millis < now){
            skeleton.getRateBtn().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reserves.size();
    }

    public void clear() {
        int size = reserves.size();
        reserves.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addReserve(Reserve reserve){
        reserves.add(reserve);
        notifyItemInserted(reserves.size()-1);
    }
}
