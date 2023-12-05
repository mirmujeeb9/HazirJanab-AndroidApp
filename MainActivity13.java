package com.faizanahmed.i200546;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity13 extends AppCompatActivity {

//    DatabaseReference databaseReference;
//    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();

        Spinner CitySpinner = findViewById(R.id.CityInput);
        String[] Cities = {"Islamabad", "Karachi", "Lahore", "Faisalabad"};
        ArrayAdapter<String> CityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Cities);
        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySpinner.setAdapter(CityAdapter);
        CitySpinner.setSelection(0);

        Spinner CountrySpinner = findViewById(R.id.CountryInput);
        String[] Countries = {"Select Country", "Country 1", "Country 2", "Country 3"};
        ArrayAdapter<String> CountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Countries);
        CountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CountrySpinner.setAdapter(CountryAdapter);
        CountrySpinner.setSelection(0);

        TextView SaveChanges = findViewById(R.id.SaveChanges);
        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get the user's inputs
                String newName = ((EditText)findViewById(R.id.NameInput)).getText().toString();
                String newEmail = ((EditText)findViewById(R.id.EmailInput)).getText().toString();
                String newContact = ((EditText)findViewById(R.id.ContactInput)).getText().toString();
                String newCountry = ((Spinner)findViewById(R.id.CountryInput)).getSelectedItem().toString();
                String newCity = ((Spinner)findViewById(R.id.CityInput)).getSelectedItem().toString();

                // Update the user's profile
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                        .setDisplayName(newName)
//                        .build();

//                currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // Update the user's information in Firebase Realtime Database
//                            String userId = currentUser.getUid();
//                            DatabaseReference userRef = databaseReference.child("users").child(userId);
//                            userRef.child("name").setValue(newName);
//                            userRef.child("email").setValue(newEmail);
//                            userRef.child("contactNumber").setValue(newContact);
//                            userRef.child("country").setValue(newCountry);
//                            userRef.child("city").setValue(newCity);
//
//                            // Notify the user that the changes were saved
//                            Toast.makeText(MainActivity13.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Handle error
//                            Toast.makeText(MainActivity13.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                Intent intent = new Intent(MainActivity13.this, MainActivity10.class);
                intent.putExtra("newName",newName);
                intent.putExtra("newEmail",newEmail);
                intent.putExtra("newContact",newContact);
                intent.putExtra("newCountry",newCountry);
                intent.putExtra("newCity",newCity);
                intent.putExtra("lastIntent","13");
                startActivity(intent);
            }
        });

        ImageView BackBtn = findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity13.this, MainActivity11.class);
                startActivity(intent);
            }
        });
    }
}