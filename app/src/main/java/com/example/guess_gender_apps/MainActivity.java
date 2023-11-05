package com.example.guess_gender_apps;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url = "https://api.genderize.io/?name=adib";

    EditText inputUser;
    Button button;
    TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUser = findViewById(R.id.inputUser);
                String inputUserStrings = inputUser.getText().toString();
                getData(inputUserStrings);
            }
        });

    }

    private void getData(String name) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.genderize.io/?name="+name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("api", "onResponse" + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String val = jsonObject.getString("gender");
                            results = (TextView) findViewById(R.id.results);

                            if(val == "null") {
                                results.setText("your gender is unpredictable");
                            } else {
                                results.setText("your gender is " + val);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse" + error.toString());
            }
        });
        queue.add(stringRequest);
    }

}