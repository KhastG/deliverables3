package com.example.deliverables3_databaseconnection_login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoggedPage extends AppCompatActivity {
    String urlRetrieve ="http://192.168.15.24/deliv3/retrieveUser.php";

    Button update, delete, logOut;

    TextView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logged_page);


        //Button
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        logOut = findViewById(R.id.btnLogOut);

        //Text View
        users = findViewById(R.id.tvShowResults);


        retrieveUser();
    }

    private void retrieveUser() {
        StringRequest request = new StringRequest(Request.Method.POST, urlRetrieve,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getBoolean("success")){
                            JSONArray usernameArr = object.getJSONArray("usernames");
                            StringBuilder builder = new StringBuilder();
                            for(int i =0; i < usernameArr.length(); i++){
                                builder.append(usernameArr.getString(i)).append("\n");
                            }
                            users.setText(builder.toString());
                        }
                    }catch (Exception e){
                        Toast.makeText(LoggedPage.this, "JSON PARSING ERROR", Toast.LENGTH_SHORT).show();
                    }
                }, Error -> Toast.makeText(LoggedPage.this, "Volley Error" + Error, Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams(){
                return new HashMap<>();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(LoggedPage.this);
        queue.add(request);
    }
}