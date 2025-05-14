package com.example.deliverables3_databaseconnection_login;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class UpdateAccount extends AppCompatActivity {

    Spinner spinId;
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button confirmId, confirmNewPass;
    EditText newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);

        //Spinner
        spinId = findViewById(R.id.idSpinnerUpd);

        //Button
        confirmId = findViewById(R.id.btnConfirmIdUpd);
        confirmNewPass = findViewById(R.id.btnConfirmNewPass);

        //editText
        newPass = findViewById(R.id.etNewPassword);

        fetchingIdUpd();
    }

    private void fetchingIdUpd() {
        String url = "http://10.0.2.2/deliv3/idRetrieve.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            JSONArray ids = response.getJSONArray("ID"); // PHP returns the IDs in 'ID'
                            idList.clear();
                            for (int i = 0; i < ids.length(); i++) {
                                idList.add(ids.getString(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "No accounts found.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "JSON error loading IDs: " + e, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Volley error loading IDs: " + error , Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}