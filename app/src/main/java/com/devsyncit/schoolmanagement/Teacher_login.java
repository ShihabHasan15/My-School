package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Teacher_login extends AppCompatActivity {
    MaterialButton sign_in_btn;
    TextInputEditText email, password;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    TextView login_as_student;
    int apiResponses = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);

        sign_in_btn = findViewById(R.id.user_sign_in_btn);

        login_as_student = findViewById(R.id.login_as_student);

        /////////////////////////

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
//        System.out.println(dayOfTheWeek);

        ///////////////////////////


        login_as_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Teacher_login.this, SignIn.class));
                finish();
            }
        });


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString();

                if (user_email.isEmpty() || user_password.isEmpty()) {
                    Toast.makeText(Teacher_login.this, "Please fill out blank field", Toast.LENGTH_LONG).show();
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                        Toast.makeText(Teacher_login.this, "Please provide valid email address", Toast.LENGTH_LONG).show();
                    } else {

                        RequestQueue queue = Volley.newRequestQueue(Teacher_login.this);
                        String url = "http://192.168.0.108/Apps/teacher_data_get.php";

                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                                null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                arrayList.clear();

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);

                                        hashMap = new HashMap<>();
                                        hashMap.put("id", String.valueOf(jsonObject.getInt("id")));
                                        hashMap.put("name", jsonObject.getString("name"));
                                        hashMap.put("email", jsonObject.getString("email"));
                                        hashMap.put("password", jsonObject.getString("password"));
                                        hashMap.put("mobile_number", jsonObject.getString("mobile_number"));
                                        hashMap.put("department", jsonObject.getString("department"));

                                        arrayList.add(hashMap);

                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                                boolean isMatched = false;

                                for (int i = 0; i < arrayList.size(); i++) {

                                    HashMap<String, String> getData = arrayList.get(i);

                                    Log.d("output", String.valueOf(arrayList));

                                    String t_id = getData.get("id");
                                    String full_name = getData.get("name");
                                    String email = getData.get("email");
                                    String password = getData.get("password");
                                    String mobile_number = getData.get("mobile_number");
                                    String Class = getData.get("department");


                                    if (user_email.contains(email) && user_password.contains(password)) {
                                        isMatched = true;
                                    }

                                    if (isMatched == true) {

                                        if (user_email.equals("admin@gmail.com") && user_password.equals("admin")) {
                                            startActivity(new Intent(Teacher_login.this, Teacher_dashboard.class));
                                            finish();
                                        } else {
                                            Intent intent = new Intent(Teacher_login.this, Teacher_dashboard.class);
//                                            intent.putExtra("teacher_name", "" + full_name);
//                                            intent.putExtra("teacher_email", "" + email);
//                                            intent.putExtra("teacher_phone_number", "" + mobile_number);
                                            intent.putExtra("teacher_id", "" + t_id);

                                            Toast.makeText(Teacher_login.this, "Welcome Back", Toast.LENGTH_SHORT).show();

                                            startActivity(intent);

                                        }

                                        break;

                                    }

                                }

                                if (isMatched == false) {

                                    Toast.makeText(Teacher_login.this, "Email/Password doesn't match", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Teacher_login.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsonArrayRequest);


                    }
                }





            }
        });


    }


    public void api1(String user_email, String user_password){

    }
}