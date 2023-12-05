package com.faizanahmed.janabhazir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Booking_Normal_4 extends AppCompatActivity {

    TextView tvSelectServiceDate;
    Calendar calendar = Calendar.getInstance();
    ImageView imgGallery;
    private final int GALLERY_REQ_CODE = 1;
    private Uri imageUri;
    private Bitmap bitmap;
   Integer serviceId;
    TextView etAddress;
    String serviceType;
    private Spinner sCityItems, sServiceTimeItems;
    private EditText etServiceDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_normal_4);
        sCityItems = findViewById(R.id.sCityItems);
        sServiceTimeItems = findViewById(R.id.sServiceTimeItems);
        etAddress = findViewById(R.id.etAddress);
        etServiceDescription = findViewById(R.id.etServiceDescription);
        tvSelectServiceDate = findViewById(R.id.tvSelectServiceDate);

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

         etAddress = findViewById(R.id.etAddress);

         serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
         serviceType = getIntent().getStringExtra("serviceType");

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
                // Before starting the next activity, you want to upload the data
                try {
                    uploadData(); // Call the uploadData method to send the data to the server
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(Booking_Normal_4.this, "Failed to load image for upload", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed if there's no image to upload
                }

                // Start the next activity after data upload
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
        });

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

    private void uploadData() throws FileNotFoundException {
        Log.d("UploadData", "uploadData method is called."); // Log to confirm method call


        // Convert image to Base64
        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Get other details from user input
        final String address = etAddress.getText().toString();
        final String city = sCityItems.getSelectedItem().toString();
        final String serviceDate = tvSelectServiceDate.getText().toString();
        final String serviceTime = sServiceTimeItems.getSelectedItem().toString();
        final String serviceDescription = etServiceDescription.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.17:100/hazirjanab/form.php", // Replace with your API endpoint
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UploadData", "Volley onResponse: " + response); // Log the response
                        Toast.makeText(Booking_Normal_4.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                        // Handle the server response here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("UploadData", "Volley error: " + error.toString()); // Log the error
                        Toast.makeText(Booking_Normal_4.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        // Handle errors here
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "1");
                params.put("service_id", String.valueOf(serviceId));
                params.put("vendor_id", "1");
                params.put("city", city);
                params.put("address", address);
                params.put("date", serviceDate); // changed from service_date to date
                params.put("time", serviceTime); // changed from service_time to time
                params.put("description", serviceDescription);
                params.put("image", encodedImage); // Ensure this is the actual Base64 encoded image string
                params.put("type", serviceType);
                Log.d("UploadData", "Parameters: " + params.toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE && data != null) {
            imageUri = data.getData();
            imgGallery.setImageURI(imageUri);
            Log.d("UploadData", "Image selected, URI: " + imageUri); // Confirm image selection
        } else {
            Log.e("UploadData", "Image not selected or an error occurred.");
        }
    }

}