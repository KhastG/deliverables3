package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GetStartedPane extends AppCompatActivity {
    Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started_pane);

        getStarted = findViewById(R.id.btnGetStarted);

        getStarted.setOnClickListener(v->{
            Intent intent = new Intent(GetStartedPane.this, LoginPage.class);
            startActivity(intent);
            finish();
        });
    }
}