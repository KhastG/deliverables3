package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteAccount extends AppCompatActivity {

    Spinner idSpinner;
    Button deleteButton;
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);

        idSpinner = findViewById(R.id.idSpinner);
        deleteButton = findViewById(R.id.deleteButton);
        queue = Volley.newRequestQueue(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idSpinner.setAdapter(adapter);

        fetchAccountIds();

        deleteButton.setOnClickListener(v -> {
            if (idSpinner.getSelectedItem() != null) {
                String selectedId = idSpinner.getSelectedItem().toString();
                deleteAccountById(selectedId);
                Intent intent = new Intent(this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchAccountIds() {
        String url = "http://192.168.15.24/deliv3/idRetrieve.php"; // your PHP file

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
                        Toast.makeText(this, "JSON error loading IDs", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Volley error loading IDs", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });

        queue.add(request);
    }

    private void deleteAccountById(String id) {
        String urlDelete = "http://192.168.15.24/deliv3/deleteAccount.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlDelete,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean success = object.getBoolean("success");
                        String message = object.getString("message");

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                        if (success) {
                            fetchAccountIds(); // Refresh list after delete
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "JSON error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Volley error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };

        queue.add(request);
    }
}
