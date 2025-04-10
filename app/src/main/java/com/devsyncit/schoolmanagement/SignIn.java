package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    MaterialButton sign_in_btn;
    TextInputEditText email, password;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    TextView login_as_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //=======================================================================================

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);

        sign_in_btn = findViewById(R.id.user_sign_in_btn);

        login_as_teacher = findViewById(R.id.login_as_teacher);

        login_as_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Teacher_login.class));
                finish();
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString();

                if (user_email.contains("admin@gmail.com") && user_password.contains("admin")) {
                    startActivity(new Intent(SignIn.this, Student_dashboard.class));
                    Toast.makeText(SignIn.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    finish();
                }

                if (user_email.isEmpty() || user_password.isEmpty()) {
                    Toast.makeText(SignIn.this, "Please fill out blank field", Toast.LENGTH_LONG).show();
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                        Toast.makeText(SignIn.this, "Please provide valid email address", Toast.LENGTH_LONG).show();
                    } else {
                        
                        RequestQueue queue = Volley.newRequestQueue(SignIn.this);
                        String url = "http://192.168.0.105/Apps/student_data_get.php";

                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        Log.d("jsonarray", String.valueOf(response));

                                        arrayList.clear();

                                        for (int i = 0; i < response.length(); i++) {
                                            try {
                                                JSONObject jsonObject = response.getJSONObject(i);

                                                hashMap = new HashMap<>();
                                                hashMap.put("roll", jsonObject.getString("roll"));
                                                hashMap.put("name", jsonObject.getString("name"));
                                                hashMap.put("email", jsonObject.getString("email"));
                                                hashMap.put("password", jsonObject.getString("password"));
                                                hashMap.put("mobile_number", jsonObject.getString("mobile_number"));
                                                hashMap.put("Class", jsonObject.getString("class"));
                                                arrayList.add(hashMap);

                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }

                                        boolean isMatched = false;

                                        for (int i = 0; i < arrayList.size(); i++) {
                                            HashMap<String, String> getData = arrayList.get(i);

                                            Log.d("output", String.valueOf(arrayList));

                                            String roll = getData.get("roll");
                                            String full_name = getData.get("name");
                                            String email = getData.get("email");
                                            String password = getData.get("password");
                                            String mobile_number = getData.get("mobile_number");
                                            String Class = getData.get("Class");

                                            if (user_email.contains(email) && user_password.contains(password)) {
                                                isMatched = true;
                                            }

                                            if (isMatched == true) {

                                                if (user_email.contains("admin@gmail.com") && user_password.contains("admin")) {
                                                    startActivity(new Intent(SignIn.this, Student_dashboard.class));
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(SignIn.this, Student_dashboard.class);
                                                    intent.putExtra("student_name", "" + full_name);
                                                    intent.putExtra("student_class", "" + Class);
                                                    intent.putExtra("student_roll", "" + roll);

                                                    Log.d("std_info", full_name);
                                                    Log.d("std_info",  Class);
                                                    Log.d("std_info", roll);

                                                    startActivity(intent);
                                                    Toast.makeText(SignIn.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }

                                                break;
                                            }

                                        }

                                        if (isMatched == false) {

                                            Toast.makeText(SignIn.this, "Email/Password doesn't match", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignIn.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsonArrayRequest);

                    }


                }


            }
        });


    }
}