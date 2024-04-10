package com.example.spotify_encore;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

// Handles operations related to user profiles using Firebase Realtime Database or Firestore.
public class firebaseUserManager extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView userNameProfile;
    private TextView userLocationProfile;
    private AppCompatButton HomeButton;
    private AppCompatButton ViewSummaryButton;
    private AppCompatButton SpotifyInfromationButton;
    private AppCompatButton friendsButton;
    private Button AccountSettingsButton;

    // These are only outline methods they can be changed if needed






    /**
     * Changes Info based on the account // subject to change(name, function, and parameters)
     *
     */
    private void changeInfo () {
        userNameProfile = findViewById(R.id.userNameProfile);
        userLocationProfile = findViewById(R.id.userLocationProfile);
        profileImageView = findViewById(R.id.profileImageView);
    }

    private void pressAccountSettingsButton() {
        AccountSettingsButton = findViewById(R.id.AccountSettingsButton);

    }


    private void pressHomeButton() {
        HomeButton = findViewById(R.id.HomeButton);

    }

    private void pressViewSummaryButton() {
        ViewSummaryButton = findViewById(R.id.ViewSummaryButton);

    }


    private void pressSpotifyInfromationButton() {
        SpotifyInfromationButton = findViewById(R.id.SpotifyInfromationButton);

    }

    private void pressfriendsButton() {
        AccountSettingsButton = findViewById(R.id.friendsButton);

    }


 




}
