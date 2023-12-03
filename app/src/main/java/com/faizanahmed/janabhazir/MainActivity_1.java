package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_1 extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;

    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.BtnLogin);
        tvSignup= findViewById(R.id.tvSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get email and password from EditTexts
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                // Perform login operation
                new LoginTask().execute(email, password);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_1.this, signup_4.class);
                startActivity(intent);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String loginUrl = "http://192.168.100.17:100/hazirjanab/login.php"; // Replace with your server URL
            String result = "";
            String email = params[0];
            String password = params[1];

            try {
                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();

                // Write parameters to the request
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write("email=" + email + "&password=" + password);
                osw.flush();
                osw.close();

                // Read the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                result = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("LoginResponse", "Response from server: " + result); // Log the response

            // Parse the JSON response
            try {
                JSONObject jsonResponse = new JSONObject(result);
                int status = jsonResponse.getInt("Status");
                String message = jsonResponse.getString("Message");

                Toast.makeText(MainActivity_1.this, message, Toast.LENGTH_LONG).show();

                if(status == 1) {
                    JSONObject userData = jsonResponse.getJSONObject("UserData");

                    // Store user data in singleton class
                    UserDataSingleton.getInstance().setUserData(userData);



                    Intent intent = new Intent(MainActivity_1.this, ServiceType_2.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity_1.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
            }
        }

    }
}
