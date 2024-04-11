package com.example.spotify_encore;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(authentication.this, "Enger Email", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(authentication.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }

                // Create a New User if they don't already have an account
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(authentication.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    // Current User that Has Logged In
                                    FirebaseUser user = mAuth.getCurrentUser();


                                } else {

                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(authentication.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

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
