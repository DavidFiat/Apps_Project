package com.example.apps_project.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps_project.R;
import com.example.apps_project.model.Client;
import com.example.apps_project.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class LoginClient extends AppCompatActivity {

    private TextView registerTV;
    private EditText emailET, passwordET;
    private Button loginBtn;

    private LoginButton loginButton;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);

        registerTV = findViewById(R.id.registerTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);

        registerTV.setOnClickListener(
                (v)->{
                    Intent intent = new Intent(this, RegisterClient.class);
                    startActivity(intent);
                }
        );

        loginBtn.setOnClickListener(this::login);

        callbackManager=CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setOnClickListener(this::loginFB);


    }

    private void loginFB(View view) {
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              facebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void facebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener(
                task->{
                    addClientInFireBase();
                    Toast.makeText(this,"Ingreso Correctamente",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, ClientActivity.class);
                    startActivity(intent);
                    finish();
                }
        ).addOnFailureListener(
                error->{
                    Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
        );
    }

    private void addClientInFireBase() {

        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(fireUser.getUid(),"client");
        Client client = new Client(fireUser.getUid(),fireUser.getDisplayName(),fireUser.getEmail());
        FirebaseFirestore.getInstance().collection("users").document(user.getId()).set(user);
        FirebaseFirestore.getInstance().collection("clients").document(user.getId()).set(client);



    }

    private void login(View view) {
        String email = emailET.getText().toString();
        String pass = passwordET.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(
                        task->{
                            FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                            if(fbuser.isEmailVerified()){
                                //Le damos acceso
                                Intent intent = new Intent(this, ClientActivity.class);
                                startActivity(intent);


                            }else{
                                Toast.makeText(this, "Su email no está verificado", Toast.LENGTH_LONG).show();
                            }
                        }
                ).addOnFailureListener(
                    error->Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}