package com.example.apps_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apps_project.R;
import com.example.apps_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private User loadedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, RolActivity.class);
            startActivity(intent);
            finish();
            return;
        }else{
            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(
                    task ->{
                        loadedUser = task.getResult().toObject(User.class);
                        switch (loadedUser.getRol()){
                            case "client":
                                Intent intent = new Intent(this, ClientActivity.class);
                                intent.putExtra("client", loadedUser);
                                startActivity(intent);
                                break;
                        }
                    }
            );
        }
    }
}