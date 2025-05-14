package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {
    Button login, signup;
    EditText user, pass;
    String urlLogin = "http://192.168.254.199/deliv3/loginAcc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignUp);
        signup.setOnClickListener(v -> AccountCreation());

        //Edit Text
        user = findViewById(R.id.etLoginUsername);
        pass = findViewById(R.id.etLoginPassword);

        login.setOnClickListener(v -> {
            String username = user.getText().toString().trim();
            String password = pass.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginPage.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");

                            if (status.equals("success")) {
                                Intent intent = new Intent(LoginPage.this, LoggedPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = obj.getString("message");
                                Toast.makeText(LoginPage.this, "Error " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginPage.this, "Exception error: " + e, Toast.LENGTH_SHORT).show();
                        }
                    }, volleyError -> Toast.makeText(LoginPage.this, "Volley Error: " + volleyError, Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        });


    }

    private void AccountCreation() {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
        finish();
    }
}