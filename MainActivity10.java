package com.faizanahmed.i200546;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity10 extends AppCompatActivity {

    DatabaseReference databaseReference;
    String dpurl;
    String dpurl2;
    ImageView userpic;
    ImageView profilepic;

    TextView name,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        name = findViewById(R.id.tvUserName);
        city = findViewById(R.id.tvCityName);


        Integer intentValue = getIntent().getIntExtra("lastIntent",1);
        String newName = getIntent().getStringExtra("newName");
        String newCity = getIntent().getStringExtra("newCity");

        if(intentValue==13){
            name.setText(newName);
            city.setText(newCity);
        }


//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference userRef = databaseReference.child("users").child(currentUserId);
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Get the user's data
//                    String userName = dataSnapshot.child("name").getValue(String.class);
//                    String userCity = dataSnapshot.child("city").getValue(String.class);
//
//                    // Update the UI elements with the user's data
//                    TextView tvUserName = findViewById(R.id.tvUserName);
//                    TextView tvCityName = findViewById(R.id.tvCityName);
//
//                    tvUserName.setText(userName);
//                    tvCityName.setText(userCity);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });



        Button HomeBtn = findViewById(R.id.HomeBtn);
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity5.class);
                startActivity(intent);
            }
        });

        Button AddBtn = findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity12.class);
                startActivity(intent);
            }
        });

        Button SearchBtn = findViewById(R.id.SearchBtn);
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity6.class);
                startActivity(intent);
            }
        });

        Button ChatBtn = findViewById(R.id.ChatBtn);
        ChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity8.class);
                startActivity(intent);
            }
        });

        LinearLayout FeaturesItems_CardViewLinearLayout = findViewById(R.id.FeaturesItems_CardViewLinearLayout1);
        FeaturesItems_CardViewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity11.class);
                startActivity(intent);
            }
        });

        ImageView EditProfile = findViewById(R.id.EditProfile);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity13.class);
                startActivity(intent);
            }
        });

        LinearLayout CardViewLinearLayout = findViewById(R.id.CardViewLinearLayout1);
        CardViewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity11.class);
                startActivity(intent);
            }
        });
        userpic=findViewById(R.id.userPic);
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);

            }
        });
        profilepic = findViewById(R.id.greyBackground);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,110);

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Uri image=data.getData();
            ImageView userpic = findViewById(R.id.userPic);
            userpic.setImageURI(image);
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference ref=storage.getReference("abc").child(System.currentTimeMillis()+"dp.png");
            ref.putFile(image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(MainActivity10.this,uri.toString(),Toast.LENGTH_LONG);
                                    dpurl=uri.toString();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity10.this,e.getMessage().toString(),Toast.LENGTH_LONG);

                        }
                    });
        }else if(requestCode==110 && resultCode==RESULT_OK){
            Uri image=data.getData();
            ImageView profilepic = findViewById(R.id.greyBackground);
            profilepic.setImageURI(image);
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference ref=storage.getReference("abc").child(System.currentTimeMillis()+"pp.png");
            ref.putFile(image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(MainActivity10.this,uri.toString(),Toast.LENGTH_LONG);
                                    dpurl=uri.toString();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity10.this,e.getMessage().toString(),Toast.LENGTH_LONG);

                        }
                    });
        }
    }
}