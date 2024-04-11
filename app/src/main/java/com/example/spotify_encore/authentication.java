package com.example.spotify_encore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;


// This class interacts with Firebase Authentication to perform login, signup, logout, etc.
public class authentication extends AppCompatActivity {

    // Firebase Information
    private FirebaseAuth mAuth;


    // Sign Up Information

    private TextView signUpEmail;
    private TextView signUpUsername;
    private TextView signUpPassword;

    private EditText editTextEmailSignUp;
    private EditText editTextUserNameSignUp;
    private EditText editTextPasswordSignUp;

    private AppCompatButton createAccountButton;

    private AppCompatButton connectSpotifyAccountButton;


    // Login Information
    private TextView textEmailLogin;
    private TextView textPasswordLogin;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;

    private AppCompatButton loginAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String action = getIntent().getStringExtra("userAction");

        assert action != null;

        if (action.equals("LogIn")) {
            setContentView(R.layout.login);

        } else if (action.equals("SignUp")) {
            setContentView(R.layout.signup);

        }

        mAuth = FirebaseAuth.getInstance();


        // User Logs into their account
        loginAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        // User creates their new account
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email, password;
                email = String.valueOf(editTextEmailSignUp);
                password = String.valueOf(editTextPasswordSignUp);


            }
        });




    }

    public void userSignUp(View view) {
        Intent sign = new Intent(this, authentication.class);
        String authentication = "SignUp";
        sign.putExtra("userAction", authentication);
        startActivity(sign);
    }

    public void initializeSignUpComponenets() {

    }

    //
    public void initializeLogInComponenets() {

    }



}
