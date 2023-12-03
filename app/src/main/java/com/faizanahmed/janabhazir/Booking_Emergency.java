package com.faizanahmed.janabhazir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Booking_Emergency extends AppCompatActivity {

    ImageView imgGallery;
    private final int GALLERY_REQ_CODE = 1;
    private Uri imageUri;
    private EditText etAddress, etServiceDescription;
    private Spinner sCityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_emergency);

        imgGallery = findViewById(R.id.uploadPicture);
        etAddress = findViewById(R.id.etAddress);
        etServiceDescription = findViewById(R.id.etServiceDescription);
        sCityItems = findViewById(R.id.sCityItems);
        Button btnUploadImage = findViewById(R.id.btnUploadImage);
        Button btnNext = findViewById(R.id.btnNext);

        Spinner sService = findViewById(R.id.sServiceItems);
        ArrayList<String> serviceItems = new ArrayList<>();
        // Add services to the list
        serviceItems.add("Service 1");
        serviceItems.add("Service 2");
        serviceItems.add("Service 3");
        // ... Add more services as needed
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceItems);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sService.setAdapter(serviceAdapter);

        // Populate City Spinner
        Spinner sServiceCity = findViewById(R.id.sCityItems);
        ArrayList<String> cityItems = new ArrayList<>();
        // Add cities to the list
        cityItems.add("City 1");
        cityItems.add("City 2");
        cityItems.add("City 3");
        // ... Add more cities as needed
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityItems);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sServiceCity.setAdapter(cityAdapter);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, GALLERY_REQ_CODE);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadData();
                } else {
                    Toast.makeText(Booking_Emergency.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            String address = etAddress.getText().toString();
            String description = etServiceDescription.getText().toString();
            String city = sCityItems.getSelectedItem().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.17:100/hazirjanab/emergencyform.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            Toast.makeText(Booking_Emergency.this, "Response: " + response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error", error.toString());
                            Toast.makeText(Booking_Emergency.this, "Upload failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", "1");
                    params.put("service_id", "1");
                    params.put("vendor_id", "1");
                    params.put("city", city);
                    params.put("address", address);
                    params.put("description", description);
                    params.put("image", encodedImage);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE && data != null) {
            imageUri = data.getData();
            imgGallery.setImageURI(imageUri);
        }
    }
}
