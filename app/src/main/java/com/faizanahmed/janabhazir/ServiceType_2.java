package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ServiceType_2 extends AppCompatActivity {

    DrawerLayout SideBarDrawer;
    ImageView SideBarMenu;
    NavigationView NavigationDrawer1, NavigationDrawer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_type_2);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
//        String serviceName = getIntent().getStringExtra("serviceName");
//        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
//        String serviceDescription = getIntent().getStringExtra("serviceDescription");
//        String serviceCity = getIntent().getStringExtra("serviceCity");
//        String serviceCategory = getIntent().getStringExtra("serviceCategory");
//        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
//        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
//
//        //Display all the intents that were received in toast messages
//        Toast.makeText(this, "serviceName: " + serviceName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceHourlyRate: " + serviceHourlyRate, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceDescription: " + serviceDescription, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCity: " + serviceCity, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCategory: " + serviceCategory, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceImageUrl: " + serviceImageUrl, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceVideoUrl: " + serviceVideoUrl, Toast.LENGTH_SHORT).show();

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, Support_11.class);
                startActivity(intent);
            }
        });

        LinearLayout Emergency = findViewById(R.id.llcvEmergency);
        LinearLayout Appointment = findViewById(R.id.llcvAppointment);
        LinearLayout Inquiry = findViewById(R.id.llcvInquiry);
        LinearLayout Emarket = findViewById(R.id.llcvEmarket);

        String serviceType = null;

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Emergency", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Emergency");
//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });

        Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Appointment");
//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });

        Inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Inquiry");
//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });
    }
}