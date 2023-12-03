package com.faizanahmed.janabhazir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Booking_Normal_4 extends AppCompatActivity {

    TextView tvSelectServiceDate;
    Calendar calendar = Calendar.getInstance();
    ImageView imgGallery;
    private final int GALLERY_REQ_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_normal_4);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Booking_Normal_4.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Booking_Normal_4.this, Support_11.class);
                startActivity(intent);
            }
        });

        TextView etAddress = findViewById(R.id.etAddress);

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


        tvSelectServiceDate = findViewById(R.id.tvSelectServiceDate);
        tvSelectServiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //Service Items
        Spinner sService = findViewById(R.id.sServiceItems);
        ArrayList<String> serviceItems = new ArrayList<>();
        serviceItems.add(serviceName);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceItems);
        sService.setAdapter(serviceAdapter);

        //City Items
        Spinner sServiceCity = findViewById(R.id.sCityItems);
        ArrayList<String> cityItems = new ArrayList<>();
        cityItems.add(serviceCity);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityItems);
        sServiceCity.setAdapter(cityAdapter);

        //Service Time
        Spinner sServiceTime = findViewById(R.id.sServiceTimeItems);
        String[] serviceTimeItems = {"Morning", "Afternoon", "Evening", "Night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceTimeItems);
        sServiceTime.setAdapter(adapter);

        imgGallery = findViewById(R.id.uploadPicture);
        Button btnUploadImage = findViewById(R.id.btnUploadImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK);
                intentGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, GALLERY_REQ_CODE);
            }
        });

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Booking_Normal_4.this, orderPlacedCOD_10.class);
               intent.putExtra("serviceId", serviceId);
               intent.putExtra("serviceName", serviceName);
               intent.putExtra("serviceHourlyRate", serviceHourlyRate);
               intent.putExtra("serviceDescription", serviceDescription);
               intent.putExtra("serviceCity", serviceCity);
               intent.putExtra("serviceType", serviceType);
               intent.putExtra("serviceCategory", serviceCategory);
               intent.putExtra("serviceImageUrl", serviceImageUrl);
               intent.putExtra("serviceVideoUrl", serviceVideoUrl);
               intent.putExtra("address", etAddress.getText().toString());
               intent.putExtra("serviceTime", sServiceTime.getSelectedItem().toString());
               startActivity(intent);
           }
        }
        );
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvSelectServiceDate.setText(selectedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}