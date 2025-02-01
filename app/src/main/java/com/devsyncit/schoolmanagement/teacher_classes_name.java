package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class teacher_classes_name extends AppCompatActivity {
    RecyclerView classes_and_section;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classes_name);

        classes_and_section = findViewById(R.id.classes_and_section);

        Intent intent = getIntent();

        int t_id = Integer.parseInt(intent.getStringExtra("teacher_id"));

        RequestQueue queue = Volley.newRequestQueue(teacher_classes_name.this);
        String url = "http://192.168.0.108/Apps/teacher_course_list_data_get.php?t_id="+t_id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arrayList.clear();

                int sum_of_students = 0;

                for (int i=0; i<response.length(); i++){

                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        hashMap = new HashMap<>();
                        hashMap.put("name", jsonObject.getString("name"));
                        hashMap.put("Class", jsonObject.getString("class"));
                        hashMap.put("section", jsonObject.getString("section"));
                        String no_of_students = jsonObject.getString("no_of_students");
                        hashMap.put("no_of_students", no_of_students);
                        hashMap.put("course_name", jsonObject.getString("course_name"));

                        arrayList.add(hashMap);

                        sum_of_students = sum_of_students + Integer.parseInt(no_of_students);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                Teacher_dashboard.total_students = sum_of_students;

                ClassesAndSectionAdapter adapter = new ClassesAndSectionAdapter();
                classes_and_section.setAdapter(adapter);

                classes_and_section.setLayoutManager(new LinearLayoutManager(teacher_classes_name.this));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);



    }

    public class ClassesAndSectionAdapter extends RecyclerView.Adapter <ClassesAndSectionAdapter.ClassesAndSectionViewHolder>{

        public class ClassesAndSectionViewHolder extends RecyclerView.ViewHolder{

            TextView class_no_and_section_name, no_of_students, course_name;

            public ClassesAndSectionViewHolder(@NonNull View itemView) {
                super(itemView);

                class_no_and_section_name = itemView.findViewById(R.id.class_no_section_name);
                no_of_students = itemView.findViewById(R.id.no_of_students);
                course_name = itemView.findViewById(R.id.course_name);

            }
        }

        @NonNull
        @Override
        public ClassesAndSectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View MyView = inflater.inflate(R.layout.classes_and_section_design, parent, false);

            return new ClassesAndSectionViewHolder(MyView);
        }

        @Override
        public void onBindViewHolder(@NonNull ClassesAndSectionViewHolder holder, int position) {

                HashMap<String, String> get_course_data = arrayList.get(position);

                String name = get_course_data.get("name");
                String Class = get_course_data.get("Class");
                String section = get_course_data.get("section");
                String no_of_students = get_course_data.get("no_of_students");
                String course_name = get_course_data.get("course_name");


                holder.class_no_and_section_name.setText("Class : "+Class+" | Section : "+section);
                holder.no_of_students.setText(no_of_students+" Students");
                holder.course_name.setText("Course : "+course_name);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }


}