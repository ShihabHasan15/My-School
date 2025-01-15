package com.devsyncit.schoolmanagement;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Teacher_sign_up extends AppCompatActivity {

    TextInputEditText teacher_id, teacher_email, teacher_password, teacher_name
            , teacher_mobile_number, teacher_department;
    MaterialButton teacher_sign_up_btn;
    TextView sign_up_as_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up);


        teacher_id = findViewById(R.id.teacher_id);
        teacher_email = findViewById(R.id.teacher_email);
        teacher_password = findViewById(R.id.teacher_password);
        teacher_name = findViewById(R.id.teacher_name);
        teacher_mobile_number = findViewById(R.id.teacher_mobile_number);
        teacher_department = findViewById(R.id.teacher_department);
        teacher_sign_up_btn = findViewById(R.id.teacher_sign_up_btn);
        sign_up_as_student = findViewById(R.id.sign_up_as_student);



        teacher_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_full_name = teacher_name.getText().toString();
                String t_email = teacher_email.getText().toString();
                String t_password = teacher_password.getText().toString();
                String t_mobile_number = teacher_mobile_number.getText().toString();
                String t_id = teacher_id.getText().toString();
                String t_department = teacher_department.getText().toString();


                if (t_full_name.isBlank() || t_email.isBlank() || t_password.isBlank() || t_mobile_number.isBlank()
                        || t_id.isBlank() || t_department.isBlank()){

                    Toast.makeText(Teacher_sign_up.this, "Please fill out blank field", Toast.LENGTH_LONG).show();

                }else {

                    if (!Patterns.EMAIL_ADDRESS.matcher(t_email).matches()){

                        Toast.makeText(Teacher_sign_up.this, "Please Enter valid email address", Toast.LENGTH_LONG).show();

                    }else {

                        RequestQueue queue = Volley.newRequestQueue(Teacher_sign_up.this);
                        String url = "http://192.168.0.111/Apps/teacher_data.php?id="+t_id+"&name="+t_full_name+"&email="+t_email+"&password="+t_password+"&mb_number="+t_mobile_number+"&department="+t_department;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.toString().contains("Sign Up Successfull")){
                                            Toast.makeText(Teacher_sign_up.this, ""+response.toString(), Toast.LENGTH_LONG).show();
                                            onBackPressed();
                                        }else {
                                            Toast.makeText(Teacher_sign_up.this, "Something Wrong", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("error", ""+error);
                                Toast.makeText(Teacher_sign_up.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });

                        queue.add(stringRequest);
                    }


                }

            }
        });


    }
}