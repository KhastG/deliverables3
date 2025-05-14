package com.example.deliverables3_databaseconnection_login;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class UpdatePassword extends AppCompatActivity {

    Spinner idSpinner;
    EditText newPasswordEditText;
    Button updateButton;
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        idSpinner = findViewById(R.id.idSpinner);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        updateButton = findViewById(R.id.UpdateButton);
        queue = Volley.newRequestQueue(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        idSpinner.setAdapter(adapter);

        loadIDs();

        updateButton.setOnClickListener(v -> {
            String user = idSpinner.getSelectedItem().toString();
            String pass = newPasswordEditText.getText().toString();

            if (pass.isEmpty()) {
                Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show();
            } else {
                updatePassword(user, pass);
            }
        });
    }

    void loadIDs() {
        String url = "http://10.0.2.2/deliv3/idRetrieve.php";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                res -> {
                    try {
                        JSONArray arr = res.getJSONArray("ID");
                        idList.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            idList.add(arr.getString(i));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Load error", Toast.LENGTH_SHORT).show();
                    }
                },
                err -> Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
        );

        queue.add(req);
    }

    void updatePassword(String user, String pass) {
        String url = "http://10.0.2.2/deliv3/updatePassword.php";

        StringRequest req = new StringRequest(Request.Method.POST, url,
                res -> {
                    try {
                        JSONObject obj = new JSONObject(res);
                        if (obj.getString("status").equals("success")) {
                            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                            newPasswordEditText.setText("");
                        } else {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                },
                err -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("username", user);
                map.put("newPassword", pass);
                return map;
            }
        };

        queue.add(req);
    }
}
