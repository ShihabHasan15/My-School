package com.devsyncit.schoolmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class Student_sign_up extends AppCompatActivity {
    TextInputEditText student_full_name, student_email, student_password, student_mobile_number
            , student_class, student_roll;
    MaterialButton student_sign_up_btn;
    TextView sign_up_as_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        student_full_name = findViewById(R.id.student_full_name);
        student_email = findViewById(R.id.student_email);
        student_password = findViewById(R.id.student_password);
        student_mobile_number = findViewById(R.id.student_mobile_number);
        student_class = findViewById(R.id.student_class);
        student_roll = findViewById(R.id.student_roll);
        student_sign_up_btn = findViewById(R.id.student_sign_up_btn);
        sign_up_as_teacher = findViewById(R.id.sign_up_as_teacher);

        sign_up_as_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_sign_up.this, Teacher_sign_up.class));
            }
        });


        student_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_full_name = student_full_name.getText().toString();
                String s_email = student_email.getText().toString();
                String s_password = student_password.getText().toString();
                String s_mobile_number = student_mobile_number.getText().toString();
                String s_class = student_class.getText().toString();
                String s_roll = student_roll.getText().toString();


                if (s_full_name.isBlank() || s_email.isBlank() || s_password.isBlank() || s_mobile_number.isBlank()
                || s_class.isBlank() || s_roll.isBlank()){

                    Toast.makeText(Student_sign_up.this, "Please fill out blank field", Toast.LENGTH_LONG).show();

                }else {

                    if (!Patterns.EMAIL_ADDRESS.matcher(s_email).matches()){

                        Toast.makeText(Student_sign_up.this, "Please Enter valid email address", Toast.LENGTH_LONG).show();

                    }else {

                        int s_int_class = Integer.parseInt(s_class);

                        if (s_int_class>0 && s_int_class<=10){

                            RequestQueue queue = Volley.newRequestQueue(Student_sign_up.this);
                            String url = "http://192.168.0.108/Apps/student_data.php?roll="+s_roll+"&name="+s_full_name+"&email="+s_email+"&password="+s_password+"&mb_number="+s_mobile_number+"&class="+s_class;

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            if (response.toString().contains("Sign Up Successfull")){
                                                Toast.makeText(Student_sign_up.this, ""+response.toString(), Toast.LENGTH_LONG).show();
                                                onBackPressed();
                                            }else {
                                                Toast.makeText(Student_sign_up.this, "Something Wrong", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("error", ""+error);
                                    Toast.makeText(Student_sign_up.this, ""+error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });

                            queue.add(stringRequest);
                        }else {
                            Toast.makeText(Student_sign_up.this, "Please Provide valid class", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            }
        });
    }
}