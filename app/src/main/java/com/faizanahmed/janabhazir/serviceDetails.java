package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class serviceDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(serviceDetails.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(serviceDetails.this, Support_11.class);
                startActivity(intent);
            }
        });

        Integer serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
        String serviceType = getIntent().getStringExtra("serviceType");

        //Display all the intents that were received in toast messages

        Toast.makeText(this, "serviceId: " + serviceId, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceName: " + serviceName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceHourlyRate: " + serviceHourlyRate, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceDescription: " + serviceDescription, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceCity: " + serviceCity, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceCategory: " + serviceCategory, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceImageUrl: " + serviceImageUrl, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceVideoUrl: " + serviceVideoUrl, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "serviceType: " + serviceType, Toast.LENGTH_SHORT).show();

        ImageView ivServiceImage = findViewById(R.id.ivServiceImage);
        TextView tvServiceName = findViewById(R.id.tvServiceName);
        TextView tvServiceHourlyRate = findViewById(R.id.tvHourlyRate);
        TextView tvServiceDescription = findViewById(R.id.tvServiceDescription);
        TextView tvServiceCity = findViewById(R.id.tvServiceCity);

        //set the values of the views
        tvServiceName.setText(serviceName);
        tvServiceHourlyRate.setText("PKR " + serviceHourlyRate + "/hr");
        tvServiceDescription.setText(serviceDescription);
        tvServiceCity.setText(serviceCity);

        Button btnBookNow = findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;

                switch (serviceType) {
                    case "Emergency":
                        intent = new Intent(serviceDetails.this, Booking_Emergency.class);
                        break;
                    case "Appointment":
                        intent = new Intent(serviceDetails.this, Booking_Normal_4.class);
                        break;
                    case "Inquiry":
                        intent = new Intent(serviceDetails.this, Booking_Normal_4.class);
                        break;
                    case "Emarket":
                        Toast.makeText(serviceDetails.this, "Emarket", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(serviceDetails.this, "Invalid service type", Toast.LENGTH_SHORT).show();
                        break;
                }
                //pass all the details to next activity
                intent.putExtra("serviceId", serviceId);
                intent.putExtra("serviceName", serviceName);
                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
                intent.putExtra("serviceDescription", serviceDescription);
                intent.putExtra("serviceCity", serviceCity);
                intent.putExtra("serviceCategory", serviceCategory);
                intent.putExtra("serviceImageUrl", serviceImageUrl);
                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
                intent.putExtra("serviceType", serviceType);
                // Put other data...
                startActivity(intent);
            }
        });



    }
}