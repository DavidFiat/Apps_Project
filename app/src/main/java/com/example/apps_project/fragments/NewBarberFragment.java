package com.example.apps_project.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.apps_project.R;
import com.example.apps_project.activities.RolActivity;
import com.example.apps_project.model.Barber;
import com.example.apps_project.model.Barbershop;
import com.example.apps_project.model.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class NewBarberFragment extends Fragment implements ImageOption.OnChoiceListener{


    private EditText nameTV, emailTV, passwordET, repasswordET;
    private ImageView photoBarber;
    private Barbershop barbershop; //Se supone que es la barbería que está asociada a este nuevo barbero
    private Barber barber;
    private File file;
    private Button continueBtn;
    private TextView linkImg;



    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    public NewBarberFragment() {
        // Required empty public constructor
        barber = new Barber("1","barber","5.0","barber@ail.com","");
    }



    public static NewBarberFragment newInstance() {
        NewBarberFragment fragment = new NewBarberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_barber, container, false);
        linkImg = view.findViewById(R.id.linkImg);

        linkImg.setOnClickListener(this::openChoice);

        continueBtn = view.findViewById(R.id.continueBtn);
        emailTV = view.findViewById(R.id.emailET);
        nameTV = view.findViewById(R.id.nameTV);
        passwordET = view.findViewById(R.id.passwordET);
        repasswordET = view.findViewById(R.id.repasswordET);
        photoBarber = view.findViewById(R.id.photoBarber);
        continueBtn.setOnClickListener(this::register);



        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onCameraResult);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onGalleryResult);


        return view;
    }

    public void onGalleryResult(ActivityResult result){
        if(result.getResultCode()==getActivity().RESULT_OK){
            Uri uri = result.getData().getData();
            photoBarber.setImageURI(uri);
            barber.setUrlImage(UtilDomi.getPath(getContext(),uri));
        }
    }

    public void onCameraResult(ActivityResult result){
        if(result.getResultCode()==getActivity().RESULT_OK){

            //Foto completa
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            photoBarber.setImageBitmap(thumbnail);
            Uri uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName(),file);
            barber.setUrlImage(file.getPath());

        }else if(result.getResultCode()==getActivity().RESULT_CANCELED){
            Toast.makeText(getContext(),"Operación cancelada", Toast.LENGTH_LONG).show();
        }
    }

    private void register(View view) {
        String name = nameTV.getText().toString();
        String email = emailTV.getText().toString();
        String password = passwordET.getText().toString();
        String repassword = repasswordET.getText().toString();

        if(password.equals(repassword)){
            //1. Registrarse en la db de auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                    task->{
                        //2. Registrar al usuario en la base de datos
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        User user = new User(fbUser.getUid(), "client");
                        Barber barber = new Barber(fbUser.getUid(), name, "0", email, "");
                        FirebaseFirestore.getInstance().collection("users").document(fbUser.getUid()).set(user);
                        //Estamos dudando de este, por eso le vamos a preguntar al profe por el de abajo
                        FirebaseFirestore.getInstance().collection("barbers").document(fbUser.getUid()).set(barber).addOnSuccessListener(
                                firetask->{
                                    getActivity().finish();
                                }
                        );

                        //Preguntarle al profe si esto sirve - para guardar un barbero en una barbería
                        /*FirebaseFirestore.getInstance().collection("barbershops").document(fbUser.getUid()).collection("barbers").document((fbUser.getUid())).set(barber).addOnSuccessListener(
                                firetask->{
                                    finish();
                                }
                        );*/

                    }
            ).addOnFailureListener(
                    error->{
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        } else{
            Toast.makeText(view.getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
    }

    public void openChoice(View view){
        ImageOption dialog = ImageOption.newInstance();
        dialog.setListener((ImageOption.OnChoiceListener) this);
        dialog.show(getActivity().getSupportFragmentManager(),"dialog");
    }

    private void exitClient(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(view.getContext(), RolActivity.class);
        startActivity(intent);
    }

   @Override
    public void onChoice(int choice) {
        if(choice == 0){
            openCamera(getView());
        }else if(choice==1){
            openGallery(getView());
        }
    }

    public void openCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(getContext().getExternalFilesDir(null)+"/photo.png");
        Uri uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName(),file);
        Toast.makeText(getContext(),"Seleccionaste camara", Toast.LENGTH_LONG).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        cameraLauncher.launch(intent);

    }

    private void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    //Borrar todo lo relacionado con la imagen del barbero.
}