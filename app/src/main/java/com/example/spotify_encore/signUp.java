package com.example.spotify_encore;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class signUp extends AppCompatActivity {
    private TextView textUsernameSignUp;
    private TextView textEmailSignUp;
    private TextView textViewPasswordSignUp;
    private EditText editUsernameSignUp;
    private EditText editTextEmailSignUp;
    private EditText editPasswordSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }



}
