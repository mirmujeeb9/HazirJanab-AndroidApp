package com.faizanahmed.i200546;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity12 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        Spinner CitySpinner = findViewById(R.id.CityInput);
        String[] Cities = {"Islamabad", "Karachi", "Lahore", "Faisalabad"};
        ArrayAdapter<String> CityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Cities);
        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySpinner.setAdapter(CityAdapter);
        CitySpinner.setSelection(0);

        LinearLayout UploadImage = findViewById(R.id.UploadImage);
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity12.this, MainActivity15.class);
                startActivity(intent);
            }
        });

        LinearLayout UploadVideo = findViewById(R.id.UploadVideo);
        UploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity12.this, MainActivity16.class);
                startActivity(intent);
            }
        });

        Button PostItemBtn = findViewById(R.id.PostItemBtn);
        PostItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity12.this, MainActivity11.class);
                startActivity(intent);
            }
        });


        ImageView BackBtn = findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity12.this, MainActivity11.class);
                startActivity(intent);
            }
        });


        Button btnPostItem = findViewById(R.id.PostItemBtn);
        btnPostItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgurl = "imgurl";
                String videourl = "videourl";
                //Get item info
                EditText itemNameEditText = findViewById(R.id.ItemNameInput);
                String itemName = itemNameEditText.getText().toString();

                EditText hourlyRateEditText = findViewById(R.id.HourlyRateInput);
                String hourlyRate = hourlyRateEditText.getText().toString();

                EditText descriptionEditText = findViewById(R.id.DescriptionInput);
                String description = descriptionEditText.getText().toString();

                String city = CitySpinner.getSelectedItem().toString();

                Intent intent = new Intent(MainActivity12.this, MainActivity7.class);
                Item newItem = new Item(itemName, hourlyRate, description, city, "imageUrl", "videoUrl");

                new PostItemTask(itemName, hourlyRate, description, city, imgurl, videourl).execute();
                intent.putExtra("newItem", newItem);
                startActivity(intent);
            }
        });
    }

        class PostItemTask extends AsyncTask<Void, Void, String> {
            private String itemName;
            private String hourlyRate;
            private String description;
            private String city;
            private String imageUrl;
            private String videoUrl;

            PostItemTask(String itemName, String hourlyRate, String description, String city, String imageUrl, String videoUrl) {
                this.itemName = itemName;
                this.hourlyRate = hourlyRate;
                this.description = description;
                this.city = city;
                this.imageUrl = imageUrl;
                this.videoUrl = videoUrl;
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // Your URL to the PHP script
                    URL url = new URL("http://192.168.18.105/smda3/insertitem.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Create the data
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("name", itemName)
                            .appendQueryParameter("hourlyrate", hourlyRate)
                            .appendQueryParameter("description", description)
                            .appendQueryParameter("city", city)
                            .appendQueryParameter("imgurl", imageUrl)
                            .appendQueryParameter("videourl", videoUrl);
                    String query = builder.build().getEncodedQuery();

                    // Set the data as the body of the POST request
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Get the response
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error!";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                // Handle the result of the POST request
                Toast.makeText(MainActivity12.this, result, Toast.LENGTH_LONG).show();
            }
        }





}