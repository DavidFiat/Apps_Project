package com.example.apps_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.apps_project.R;

public class ImageOption extends DialogFragment {

    private OnChoiceListener listener;

    private Button btnCamera, btnGallery;

    public ImageOption() {

    }

    public static ImageOption newInstance() {
        ImageOption fragment = new ImageOption();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_option, container, false);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnGallery = view.findViewById(R.id.btnGallery);


        btnCamera.setOnClickListener(v->{
            listener.onChoice(0);
            dismiss();
        });
        btnGallery.setOnClickListener(v->{
            listener.onChoice(1);
            dismiss();
        });
        return view;
    }

    public void setListener(OnChoiceListener listener){
        this.listener = listener;
    }

    public interface OnChoiceListener{
        void onChoice(int choice);
    }
}