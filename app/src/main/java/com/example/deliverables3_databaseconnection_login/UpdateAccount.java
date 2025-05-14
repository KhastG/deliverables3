package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class UpdateAccount extends AppCompatActivity {

    Spinner spinId;
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button confirmId, confirmNewPass;
    EditText newPass;
    TextView userFetch;

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

        //Text View
        userFetch = findViewById(R.id.tvUsernameFetchUpd);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinId.setAdapter(adapter);

        confirmId.setOnClickListener(v->{
            if (spinId.getSelectedItem() != null) {
                String selectedId = spinId.getSelectedItem().toString();
                usernameFetch(selectedId);
            }
        });

        confirmNewPass.setOnClickListener(v->confirmPass());
        fetchingIdUpd();
    }

    private void confirmPass() {
        String password = newPass.getText().toString().trim();
        String urlUpdPass = "http://10.0.2.2/deliv3/updateAcc.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlUpdPass,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        String status = obj.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(UpdateAccount.this, "THE PASSWORD HAS BEEN UPDATED!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateAccount.this, LoginPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String message = obj.getString("message");
                            Toast.makeText(UpdateAccount.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(UpdateAccount.this, "Exception error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(UpdateAccount.this, "Volley Error: " + error , Toast.LENGTH_SHORT).show())
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("id", spinId.getSelectedItem().toString());
                map.put("password", newPass.getText().toString().trim());
                return map;
            }
        };

        RequestQueue queue =  Volley.newRequestQueue(this);
        queue.add(request);

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
                error -> Toast.makeText(this, "Volley error loading IDs: " + error , Toast.LENGTH_SHORT).show());

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void usernameFetch(String id) {
        String urlUserRetrieve = "http://10.0.2.2/deliv3/usernameRetrieve.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlUserRetrieve,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean success = object.getBoolean("success");
                        String message = object.getString("message");
                        Toast.makeText(UpdateAccount.this, message, Toast.LENGTH_SHORT).show();
                        if (success) {
                            String username = object.getString("username");
                            userFetch.setText(username);
                        }
                    } catch (Exception e) {
                        Toast.makeText(UpdateAccount.this, "JSON error: " + e, Toast.LENGTH_LONG).show();
                    }
                }, error -> Toast.makeText(UpdateAccount.this, "Volley error: " + error, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}