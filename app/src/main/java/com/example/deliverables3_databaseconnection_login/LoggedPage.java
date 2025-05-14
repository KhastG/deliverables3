package com.example.deliverables3_databaseconnection_login;

import android.content.Intent;
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
    String urlRetrieve ="http://10.0.2.2/deliv3/retrieveUser.php";

    String urlRetrieveIds = "http://10.0.2.2/deliv3/idRetrieve.php";

    Button update, delete, logOut;

    TextView users, id;


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
        id = findViewById(R.id.tvShowId);



        retrieveUser();
        idRetrieve();
        delete.setOnClickListener(v->{
            Intent intent = new Intent(LoggedPage.this, DeleteAccount.class);
            startActivity(intent);
            finish();
        });
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

    private void idRetrieve() {
        StringRequest request = new StringRequest(Request.Method.POST, urlRetrieveIds,
                responseID -> {
                    try {
                        JSONObject object = new JSONObject(responseID);
                        if(object.getBoolean("success")){
                            JSONArray idArr = object.getJSONArray("ID");
                            StringBuilder builder = new StringBuilder();
                            for(int i =0; i < idArr.length(); i++){
                                builder.append(idArr.getString(i)).append("\n");
                            }
                            id.setText(builder.toString());
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

