package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    Button login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignUp);

        AccountCreation();
        LoggedAccount();
    }
    private void LoggedAccount() {
        Intent intent = new Intent(this, LoggedPage.class);
        startActivity(intent);
        finish();
    }
    private void AccountCreation() {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
        finish();
    }
}