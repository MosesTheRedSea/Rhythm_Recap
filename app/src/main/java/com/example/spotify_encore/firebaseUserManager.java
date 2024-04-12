package com.example.spotify_encore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Handles operations related to user profiles using Firebase Realtime Database or Firestore.
// As well as overall App Management

public class firebaseUserManager extends AppCompatActivity {

    /*
    ░█──░█ █▀▀█ █▀▀█ ─▀─ █▀▀█ █▀▀▄ █── █▀▀ █▀▀
    ─░█░█─ █▄▄█ █▄▄▀ ▀█▀ █▄▄█ █▀▀▄ █── █▀▀ ▀▀█
    ──▀▄▀─ ▀──▀ ▀─▀▀ ▀▀▀ ▀──▀ ▀▀▀─ ▀▀▀ ▀▀▀ ▀▀▀
     */
    public static final String CLIENT_ID = "edd6322503f640c1a8514e901e175453";
    public static final String REDIRECT_URI = "com.example.spotifyencore://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private Call mCall;
    private ImageView profileImageView;
    private TextView userNameProfile;
    private TextView userLocationProfile;
    private AppCompatButton HomeButton;
    private AppCompatButton ViewSummaryButton;
    private AppCompatButton SpotifyInfromationButton;
    private AppCompatButton friendsButton;
    private Button AccountSettingsButton;
    private EditText changeUserEmail;
    private EditText changePassword;
    private EditText oldEmail;
    private EditText oldPassword;
    FirebaseAuth auth;
    AppCompatButton signOut;
    AppCompatButton changeInfo;
    AppCompatButton deleteAccount;
    AppCompatButton connectSpotify;
    AppCompatButton spotifyInformation;
    AppCompatButton generateInfo;
    AppCompatButton nextInfo;
    TextView userSpotifyInfo;
    FirebaseUser user;
    AppCompatButton returnHomeButton;
    AppCompatButton goHomeButton;
    AppCompatButton reccomendationButton;
    AppCompatButton generateReccomendation;

    //RecyclerView userReccomendation;

    ListView userReccomendation;
    reccomendationAdapter adapter;
    List<String> recommendedTracks;




    // These are only outline methods they can be changed if needed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        /*
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), authentication.class);
            intent.putExtra("userAction", "LogIn");
            startActivity(intent);
            finish();
        }
        String action = getIntent().getStringExtra("userAction");

        if (action != null) {
            if (action.equals("accountSettings")) {
                setContentView(R.layout.account_setting);
                signOut = findViewById(R.id.signOutAccountSettings);
                deleteAccount = findViewById(R.id.deleteAccountSettings);
                changeInfo = findViewById(R.id.changeInfoAccountSettings);
                changeUserEmail = findViewById(R.id.changeEmail);
                changePassword = findViewById(R.id.changePass);
                connectSpotify = findViewById(R.id.ChangeSpotifyAccountSettings);
                oldPassword = findViewById(R.id.currentPassword);

                changeInfo.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String email, password, oldEm, oldPass;

                        email = changeUserEmail.getText().toString();
                        password = changePassword.getText().toString();
                        oldPass = oldPassword.getText().toString();

                        updateUserInfo(email, password, oldPass);

                        changeUserEmail.setText("New Email");

                        changePassword.setText("New Password");
                    }
                });

                connectSpotify.setOnClickListener((v) -> {
                    getToken();
                        //addSpotifyAccessTokenToUserProfile(mAccessToken);
                });

                deleteAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUserAccount();
                        Intent sign = new Intent(getApplicationContext(), applicationCore.class);
                        startActivity(sign);
                    }
                });

                signOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userSignOut();
                        Intent sign = new Intent(getApplicationContext(), firebaseUserManager.class);
                        String authentication = "login";
                        sign.putExtra("userAction", authentication);
                        startActivity(sign);
                    }
                });
            } else if (action.equals("homepage")) {
                setContentView(R.layout.homepage);
            } else if (action.equals("gamepage")) {
                setContentView(R.layout.game);
            }
        }
        */


        if (user == null) {
            // If user is not signed in, redirect to authentication activity (assuming authentication.class is your sign-in activity)
            Intent intent = new Intent(getApplicationContext(), authentication.class);
            intent.putExtra("userAction", "LogIn");
            startActivity(intent);
            finish();
        } else {
            // If user is signed in, proceed with the appropriate action based on the intent received
            String action = getIntent().getStringExtra("userAction");

            if (action != null) {
                if (action.equals("accountSettings")) {
                    // Set content view to account settings layout
                    setContentView(R.layout.account_setting);
                    // Initialize UI components
                    signOut = findViewById(R.id.signOutAccountSettings);
                    deleteAccount = findViewById(R.id.deleteAccountSettings);
                    changeInfo = findViewById(R.id.changeInfoAccountSettings);
                    changeUserEmail = findViewById(R.id.changeEmail);
                    changePassword = findViewById(R.id.changePass);
                    connectSpotify = findViewById(R.id.ChangeSpotifyAccountSettings);
                    oldPassword = findViewById(R.id.currentPassword);

                    // Set click listener for changing user info
                    changeInfo.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            String email, password, oldPass;
                            email = changeUserEmail.getText().toString();
                            password = changePassword.getText().toString();
                            oldPass = oldPassword.getText().toString();
                            updateUserInfo(email, password, oldPass);
                            changeUserEmail.setText("New Email");
                            changePassword.setText("New Password");
                        }
                    });

                    // Set click listener for connecting Spotify
                    connectSpotify.setOnClickListener((v) -> {
                        // Start Spotify authentication flow
                        getToken();

                    });

                    // Set click listener for deleting account
                    deleteAccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Delete user account
                            deleteUserAccount();
                            // Redirect to application core activity
                            Intent sign = new Intent(getApplicationContext(), applicationCore.class);
                            startActivity(sign);
                            finish();
                        }
                    });

                    // Set click listener for signing out
                    signOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Sign out user
                            userSignOut();
                            // Redirect to login activity
                            Intent sign = new Intent(getApplicationContext(), firebaseUserManager.class);
                            String authentication = "login";
                            sign.putExtra("userAction", authentication);
                            startActivity(sign);
                            finish();
                        }
                    });
                } else if (action.equals("homepage")) {
                    // Set content view to homepage layout
                    setContentView(R.layout.homepage);
                    reccomendationButton = findViewById(R.id.recommendationsButton);
                    reccomendationButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), firebaseUserManager.class);
                            intent.putExtra("userAction", "recommendations");
                            startActivity(intent);
                            finish();
                        }
                    });

                } else if (action.equals("gamepage")) {
                    // Set content view to gamepage layout
                    setContentView(R.layout.game);
                } else if (action.equals("profile")) {
                    setContentView(R.layout.profile);

                    spotifyInformation = findViewById(R.id.SpotifyInfromationButton);
                    spotifyInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), firebaseUserManager.class);
                            intent.putExtra("userAction", "Information");
                            startActivity(intent);
                            finish();
                        }
                    });


                } else if (action.equals("Information")) {

                    setContentView(R.layout.spotify_info);

                    generateInfo = findViewById(R.id.generateSpotifyInfo);
                    nextInfo = findViewById(R.id.traverseInfo);
                    userSpotifyInfo = findViewById(R.id.spotifyInfoTextView);

                    returnHomeButton = findViewById(R.id.returnHomeButt);

                    returnHomeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), firebaseUserManager.class);
                            intent.putExtra("userAction", "homepage");
                            startActivity(intent);
                            finish();
                        }
                    });

                    generateInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onGetUserProfileClicked();
                        }
                    });
                } else if (action.equals("recommendations")) {
                    setContentView(R.layout.recomendations);

                    goHomeButton = findViewById(R.id.returnhomeButt);
                    generateReccomendation = findViewById(R.id.generateReccoButton);

                    userReccomendation = findViewById(R.id.recommendationsListView);

                    List<String> testData = new ArrayList<>();
                    adapter = new reccomendationAdapter(testData, this);
                    userReccomendation.setAdapter(adapter);
                    adapter.setRecommendedTracks(testData); // Notify the adapter that the dataset has changed
                    adapter.notifyDataSetChanged();

                    generateReccomendation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retrieveSpotifyAccessTokenAndMakeRecommendation();
                        }
                    });
                    goHomeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), firebaseUserManager.class);
                            intent.putExtra("userAction", "homepage");
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        }
    }

    /*
    ░█▀▀█ ░█─░█ ▀▀█▀▀ ▀▀█▀▀ ░█▀▀▀█ ░█▄─░█ 　 ░█▀▀█ ░█─── ▀█▀ ░█▀▀█ ░█─▄▀ ░█▀▀▀█
    ░█▀▀▄ ░█─░█ ─░█── ─░█── ░█──░█ ░█░█░█ 　 ░█─── ░█─── ░█─ ░█─── ░█▀▄─ ─▀▀▀▄▄
    ░█▄▄█ ─▀▄▄▀ ─░█── ─░█── ░█▄▄▄█ ░█──▀█ 　 ░█▄▄█ ░█▄▄█ ▄█▄ ░█▄▄█ ░█─░█ ░█▄▄▄█
     */

    public void profileButtonClick(View view) {
        Intent sign = new Intent(this, firebaseUserManager.class);
        String authentication = "profile";
        sign.putExtra("userAction", authentication);
        startActivity(sign);
        finish();
    }

    public void accountSettingsClick(View view) {
        Intent sett = new Intent(this, firebaseUserManager.class);
        String authentication = "accountSettings";
        sett.putExtra("userAction", authentication);
        startActivity(sett);
        finish();
    }

    public void backToHomePage(View view) {
        Intent sign = new Intent(this, firebaseUserManager.class);
        String authentication = "homepage";
        sign.putExtra("userAction", authentication);
        startActivity(sign);
        finish();
    }


    /*
    ░█▀▀▀ ░█▀▀▄ ▀█▀ ▀▀█▀▀ 　 ░█─░█ ░█▀▀▀█ ░█▀▀▀ ░█▀▀█ 　 ▀█▀ ░█▄─░█ ░█▀▀▀ ░█▀▀▀█
    ░█▀▀▀ ░█─░█ ░█─ ─░█── 　 ░█─░█ ─▀▀▀▄▄ ░█▀▀▀ ░█▄▄▀ 　 ░█─ ░█░█░█ ░█▀▀▀ ░█──░█
    ░█▄▄▄ ░█▄▄▀ ▄█▄ ─░█── 　 ─▀▄▄▀ ░█▄▄▄█ ░█▄▄▄ ░█─░█ 　 ▄█▄ ░█──▀█ ░█─── ░█▄▄▄█
     */

    public void updateUserInfo(String newEmail, String newPassword, String currentPassword) {
        if (!newEmail.isEmpty()) {
            // Re-authenticate the user if they have been logged in for a while
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
                currentUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> reauthTask) {
                                if (reauthTask.isSuccessful()) {
                                    // Re-authentication successful, now update the email
                                    currentUser.verifyBeforeUpdateEmail(newEmail)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> emailTask) {
                                                    if (emailTask.isSuccessful()) {
                                                        Log.d("firebaseUserManager", "User email address updated. Verification email sent.");
                                                    } else {
                                                        Log.e("firebaseUserManager", "Failed to update user email", emailTask.getException());
                                                    }
                                                }
                                            });
                                } else {
                                    Log.e("firebaseUserManager", "Re-authentication failed", reauthTask.getException());
                                }
                            }
                        });
            }
        }

        if (!newPassword.isEmpty()) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("firebaseUserManager", "User password updated.");
                            }
                        }
                    });
        }
    }

    public void deleteUserAccount() {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(firebaseUserManager.this, "Account Deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void userSignOut() {

        auth.signOut();

        Intent intent = new Intent(getApplicationContext(), authentication.class);
        intent.putExtra("userAction", "LogIn");
        startActivity(intent);
        finish();
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    /*
    public void getUserProfile() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(authentication.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    //setTextAsync(jsonObject.toString(3), profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(authentication.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    */

    /*
        ░█▀▀▀█ █▀▀█ █▀▀█ ▀▀█▀▀ ─▀─ █▀▀ █──█ 　 ─█▀▀█ ░█▀▀█ ▀█▀
        ─▀▀▀▄▄ █──█ █──█ ──█── ▀█▀ █▀▀ █▄▄█ 　 ░█▄▄█ ░█▄▄█ ░█─
        ░█▄▄▄█ █▀▀▀ ▀▀▀▀ ──▀── ▀▀▀ ▀── ▄▄▄█ 　 ░█─░█ ░█─── ▄█▄
     */

    // Method to make a recommendation request to the Spotify API
    public void makeApiRequest(String accessToken) throws IOException {
        // Example API request to get user's profile information
        Request request = new Request.Builder()
                .url(REDIRECT_URI + "/me")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseData = response.body().string();
            System.out.println("User profile data: " + responseData);
        } else {
            System.out.println("Failed to fetch user profile data");
        }
    }

    private void makeRecommendationRequest(String spotifyAccessToken) {

        OkHttpClient client = new OkHttpClient();

        // Define the request to the Spotify API
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.spotify.com/v1/recommendations").newBuilder();
        urlBuilder.addQueryParameter("limit", "10"); // Include limit parameter
        urlBuilder.addQueryParameter("market", "ES"); // Include market parameter
        urlBuilder.addQueryParameter("seed_artists", "4NHQUGzhtTLFvgF5SZesLK"); // Example seed artist ID
        urlBuilder.addQueryParameter("seed_genres", "pop,rock"); // Example seed genres
        urlBuilder.addQueryParameter("seed_tracks", "0c6xIDDpzE81m2q797ordA"); // Example seed track ID

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + spotifyAccessToken)
                .get() // Specify that this is a GET request
                .build();

        // Send the request asynchronously
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                // Handle request failure
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }


                try {
                    // Parse response JSON
                    String responseData = response.body().string();

                    System.out.println(responseData);

                    List<String> recommendedTracks = parseRecommendations(responseData);

                    // Update UI with recommended tracks
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.setRecommendedTracks(recommendedTracks); // Update the dataset with recommended tracks
                                adapter.notifyDataSetChanged();
                            } else {
                                // If adapter is null, initialize it with the recommended tracks
                                adapter = new reccomendationAdapter(recommendedTracks, getApplicationContext()); // Pass recommended tracks to the constructor of your adapter
                                userReccomendation.setAdapter(adapter); // Set the adapter to your ListView
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON parsing error
                }
            }
        });
    }

    /*
    private void makeRecommendationRequest(String spotifyAccessToken) {

        OkHttpClient client = new OkHttpClient();

        // Define the request to the Spotify API
        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/recommendations?limit=10&market=ES") // Include limit and market parameters
                .addHeader("Authorization", "Bearer " + spotifyAccessToken)
                .get() // Specify that this is a GET request
                .build();
        // Send the request asynchronously
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                // Handle request failure
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }


                Toast.makeText(firebaseUserManager.this, "Received Reccomendation", Toast.LENGTH_SHORT).show();

                try {
                    // Parse response JSON
                    String responseData = response.body().string();
                    List<String> recommendedTracks = parseRecommendations(responseData);

                    // Update UI with recommended tracks
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.setRecommendedTracks(recommendedTracks); // Update the dataset with recommended tracks
                                adapter.notifyDataSetChanged(); // Notify the adapter that the dataset has changed
                            } else {
                                // If adapter is null, initialize it with the recommended tracks
                                adapter = new reccomendationAdapter(recommendedTracks, getApplicationContext()); // Pass recommended tracks to the constructor of your adapter
                                userReccomendation.setAdapter(adapter); // Set the adapter to your ListView
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON parsing error
                }
            }
        });
    }
     */

    // Method to parse recommendations from Spotify API response
    private List<String> parseRecommendations(String responseData) throws JSONException {
        List<String> recommendedTracks = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(responseData);
        JSONArray tracks = jsonResponse.getJSONArray("tracks");

        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.getJSONObject(i);
            String trackName = track.getString("name");
            String artistName = track.getJSONArray("artists").getJSONObject(0).getString("name");
            recommendedTracks.add(trackName + " - " + artistName);
        }

        return recommendedTracks;
    }

    // Retrieve User Recommendation's based on info
    // Update the Display witht he correct information
    private void retrieveSpotifyAccessTokenAndMakeRecommendation() {
        retrieveSpotifyAccessTokenFromUserProfile(new OnTokenRetrievedListener() {
            @Override
            public void onTokenRetrieved(String spotifyAccessToken) {
                if (spotifyAccessToken != null) {
                    // Use the retrieved Spotify access token to make the recommendation request
                    makeRecommendationRequest(spotifyAccessToken);
                    Toast.makeText(firebaseUserManager.this, "Retrieved Spotify Token For Reccomendation", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where no Spotify access token was found
                    Toast.makeText(firebaseUserManager.this, "No Spotify access token found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    ░█▀▀▀█ █▀▀█ █▀▀█ ▀▀█▀▀ ─▀─ █▀▀ █──█ 　 ─█▀▀█ ▀▀█▀▀ ░█─░█ ░█─░█
    ─▀▀▀▄▄ █──█ █──█ ──█── ▀█▀ █▀▀ █▄▄█ 　 ░█▄▄█ ─░█── ░█─░█ ░█▀▀█
    ░█▄▄▄█ █▀▀▀ ▀▀▀▀ ──▀── ▀▀▀ ▀── ▄▄▄█ 　 ░█─░█ ─░█── ─▀▄▄▀ ░█─░█
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(firebaseUserManager.this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    public void onGetUserProfileClicked() {
        // Retrieve the Spotify access token from Firebase
        retrieveSpotifyAccessTokenFromUserProfile(new OnTokenRetrievedListener() {
            @Override
            public void onTokenRetrieved(String spotifyAccessToken) {
                if (spotifyAccessToken != null) {
                    // Use the Spotify access token retrieved from Firebase

                    // Update the URL to fetch the user's profile information
                    final Request request = new Request.Builder()
                            .url("https://api.spotify.com/v1/me")
                            .addHeader("Authorization", "Bearer " + spotifyAccessToken)
                            .build();

                    cancelCall();
                    mCall = mOkHttpClient.newCall(request);

                    mCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("HTTP", "Failed to fetch data: " + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(firebaseUserManager.this, "Failed to fetch data, watch Logcat for more details",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            try {
                                final JSONObject jsonObject = new JSONObject(response.body().string());

                                // Extract user profile information from the JSON response
                                String displayName = jsonObject.optString("display_name", "Not provided");
                                String email = jsonObject.optString("email", "Not provided");
                                String country = jsonObject.optString("country", "Not provided");
                                String birthdate = jsonObject.optString("birthdate", "Not provided");
                                int followersCount = jsonObject.optInt("followers", 0);
                                String followers = "Followers: " + followersCount;
                                String product = jsonObject.optString("product", "Not provided");
                                String spotifyId = jsonObject.optString("id", "Not provided");
                                String uri = jsonObject.optString("uri", "Not provided");
                                String userType = jsonObject.optString("type", "Not provided");
                                String birthplace = jsonObject.optString("birthplace", "Not provided");
                                String spotifyUrl = jsonObject.optString("external_urls.spotify", "Not provided");

                                final String userProfileInfo = "Display Name: " + displayName +
                                        "\nEmail: " + email +
                                        "\nCountry: " + country +
                                        "\nBirthdate: " + birthdate +
                                        "\n" + followers +
                                        "\nProduct: " + product +
                                        "\nSpotify ID: " + spotifyId +
                                        "\nURI: " + uri +
                                        "\nUser Type: " + userType +
                                        "\nBirthplace: " + birthplace +
                                        "\nSpotify URL: " + spotifyUrl;

                                // Update UI on the main thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the user profile information
                                        userSpotifyInfo.setText(userProfileInfo);
                                    }
                                });
                            } catch (JSONException e) {
                                Log.d("JSON", "Failed to parse data: " + e);
                                Toast.makeText(firebaseUserManager.this, "Failed to parse data, watch Logcat for more details",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle the case where no Spotify access token was found
                    Toast.makeText(firebaseUserManager.this, "No Spotify access token found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // This code comment does not work
    /*
    public void onGetUserProfileClicked() {
        // Retrieve the Spotify access token from Firebase
        retrieveSpotifyAccessTokenFromUserProfile(new OnTokenRetrievedListener() {
            @Override
            public void onTokenRetrieved(String spotifyAccessToken) {
                if (spotifyAccessToken != null) {
                    // Use the Spotify access token retrieved from Firebase

                    // Update the URL to fetch other personal user information
                    final Request request = new Request.Builder()
                            .url("https://api.example.com/v1/user/info") // Change the URL to the endpoint for fetching other personal user information
                            .addHeader("Authorization", "Bearer " + spotifyAccessToken)
                            .build();

                    cancelCall();
                    mCall = mOkHttpClient.newCall(request);

                    mCall.enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            Log.d("HTTP", "Failed to fetch data: " + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(firebaseUserManager.this, "Failed to fetch data, watch Logcat for more details",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            try {
                                final JSONObject jsonObject = new JSONObject(response.body().string());
                                final String userInfo = jsonObject.toString(3); // Adjust parsing according to the response structure

                                // Update UI on the main thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the user information in the appropriate view
                                        userSpotifyInfo.setText(userInfo); // Change to the appropriate TextView
                                        System.out.println(userInfo);
                                    }
                                });
                            } catch (JSONException e) {
                                Log.d("JSON", "Failed to parse data: " + e);
                                Toast.makeText(firebaseUserManager.this, "Failed to parse data, watch Logcat for more details",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle the case where no Spotify access token was found
                    Toast.makeText(firebaseUserManager.this, "No Spotify access token found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

     */


    // This Code Comment Works
    /*
    public void onGetUserProfileClicked() {
        // Retrieve the Spotify access token from Firebase
        retrieveSpotifyAccessTokenFromUserProfile(new OnTokenRetrievedListener() {
            @Override
            public void onTokenRetrieved(String spotifyAccessToken) {
                if (spotifyAccessToken != null) {
                    // Use the Spotify access token retrieved from Firebase

                    // Create a request to get the user profile
                    final Request request = new Request.Builder()
                            .url("https://api.spotify.com/v1/me")
                            .addHeader("Authorization", "Bearer " + spotifyAccessToken)
                            .build();

                    cancelCall();
                    mCall = mOkHttpClient.newCall(request);

                    mCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("HTTP", "Failed to fetch data: " + e);
                            Toast.makeText(firebaseUserManager.this, "Failed to fetch data, watch Logcat for more details",
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            try {
                                final JSONObject jsonObject = new JSONObject(response.body().string());
                                final String userProfileInfo = jsonObject.toString(3);

                                // Update UI on the main thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the user profile info in the userSpotifyInfo TextView
                                        userSpotifyInfo.setText(userProfileInfo);
                                    }
                                });
                            } catch (JSONException e) {
                                Log.d("JSON", "Failed to parse data: " + e);
                                Toast.makeText(firebaseUserManager.this, "Failed to parse data, watch Logcat for more details",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle the case where no Spotify access token was found
                    Toast.makeText(firebaseUserManager.this, "No Spotify access token found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
            if (response.getType() == AuthorizationResponse.Type.TOKEN) {
                mAccessToken = response.getAccessToken();
                // Handle access token
                addSpotifyAccessTokenToUserProfile(mAccessToken);
            } else {
                // Handle authorization failure
                Log.e("firebaseUserManager", "Authorization failed: " + response.getError());
            }
        }
    }

    private void retrieveSpotifyAccessTokenFromUserProfile(OnTokenRetrievedListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            userRef.child("spotifyAccessToken").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String spotifyAccessToken = dataSnapshot.getValue(String.class);
                        // Call the listener with the Spotify access token
                        listener.onTokenRetrieved(spotifyAccessToken);
                    } else {
                        // No Spotify access token found for the user
                        // Call the listener with null or an appropriate error message
                        listener.onTokenRetrieved(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Error retrieving Spotify access token from user profile", databaseError.toException());
                    // Call the listener with null or an appropriate error message
                    listener.onTokenRetrieved(null);
                }
            });
        }
    }

    private void addSpotifyAccessTokenToUserProfile(String spotifyAccessToken) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            // Check if the user already has a Spotify access token
            userRef.child("spotifyAccessToken").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User already has a Spotify access token, update it with the new one
                        userRef.child("spotifyAccessToken").setValue(spotifyAccessToken)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Firebase", "Spotify access token updated for user profile");
                                        // You may optionally perform additional actions here upon successful update
                                        Toast.makeText(firebaseUserManager.this, "Spotify Token Updated",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firebase", "Error updating Spotify access token for user profile", e);
                                        // Handle the failure, such as displaying an error message to the user
                                        Toast.makeText(firebaseUserManager.this, "Error Updating Access Token",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // User doesn't have a Spotify access token, add the new one
                        userRef.child("spotifyAccessToken").setValue(spotifyAccessToken)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Firebase", "Spotify access token added to user profile");
                                        // You may optionally perform additional actions here upon successful addition
                                        Toast.makeText(firebaseUserManager.this, "Access Token Added to Account",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firebase", "Error adding Spotify access token to user profile", e);
                                        // Handle the failure, such as displaying an error message to the user
                                        Toast.makeText(firebaseUserManager.this, "Access Token Not Added to Account",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Error checking Spotify access token existence for user profile", databaseError.toException());
                    // Handle the cancellation, such as displaying an error message to the user
                }
            });
        }
    }

    /*
    public void onGetUserProfileClicked() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(firebaseUserManager.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    setTextAsync(jsonObject.toString(3), profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
     */

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }


    private void fetchUserProfile(String accessToken) {
        // Use the access token to make a request to the Spotify API to fetch user profile
        // You can use libraries like Retrofit, Volley, or OkHttp to make HTTP requests
        // Here's a basic example using OkHttp

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                // Parse the JSON response to extract user profile information
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String userName = jsonObject.getString("display_name");
                    String email = jsonObject.getString("email");
                    // Extract other profile information as needed
                    // Once you have the profile information, you can display it in your app
                    //displayProfileInformation(userName, email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(true)
                .setScopes(new String[] { "user-read-email" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }


}
