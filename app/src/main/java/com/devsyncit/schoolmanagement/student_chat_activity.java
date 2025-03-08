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
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class student_chat_activity extends AppCompatActivity {

    RecyclerView student_chat_recycle;

    HashMap<String, String> chatMap;

    ArrayList<HashMap<String, String>> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);

        student_chat_recycle = findViewById(R.id.student_chat_recycle);


        RequestQueue queue = Volley.newRequestQueue(student_chat_activity.this);
        String url = "http://192.168.0.104/Apps/teacher_data_get.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("output", ""+response);

                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String teacher_id = jsonObject.getString("id");
                        String teacher_name = jsonObject.getString("name");
                        String teacher_dept = jsonObject.getString("department");

                        chatMap = new HashMap<>();
                        chatMap.put("t_id", teacher_id);
                        chatMap.put("t_name", teacher_name);
                        chatMap.put("t_dept", teacher_dept);

                        chatList.add(chatMap);

                    }

                    studentChatAdapter adapter = new studentChatAdapter();
                    student_chat_recycle.setAdapter(adapter);
                    student_chat_recycle.setLayoutManager(new LinearLayoutManager(student_chat_activity.this));

                }catch (Exception e){
                    Log.d("Excep", ""+e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);





    }


    public class studentChatAdapter extends RecyclerView.Adapter<studentChatAdapter.studentViewHolder>{

        public class studentViewHolder extends RecyclerView.ViewHolder{

            TextView teacher_name, teacher_department;
            MaterialCardView teacher_card;

            public studentViewHolder(@NonNull View itemView) {
                super(itemView);

                teacher_name = itemView.findViewById(R.id.teacher_name);
                teacher_department = itemView.findViewById(R.id.teacher_department);
                teacher_card = itemView.findViewById(R.id.teacher_card);
            }
        }

        @NonNull
        @Override
        public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View chatListView = inflater.inflate(R.layout.teacher_list_design, parent, false);


            return new studentViewHolder(chatListView);
        }

        @Override
        public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {

            HashMap<String, String> getInfoMap = chatList.get(position);
            String teacher_name = getInfoMap.get("t_name");
            String teacher_id = getInfoMap.get("t_id");
            String teacher_department = getInfoMap.get("t_dept");


            String student_name = getIntent().getStringExtra("student_name");
            String student_class_no = getIntent().getStringExtra("student_class_no");
            String student_roll = getIntent().getStringExtra("student_roll");

            holder.teacher_name.setText(""+teacher_name);
            holder.teacher_department.setText("Department: "+teacher_department);

            holder.teacher_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(student_chat_activity.this, student_chatting_activity.class);
                    intent.putExtra("student_name", ""+student_name);
                    intent.putExtra("student_class_no", ""+student_class_no);
                    intent.putExtra("student_roll", ""+student_roll);

                    intent.putExtra("teacher_name", ""+teacher_name);
                    intent.putExtra("teacher_id", ""+teacher_id);
                    intent.putExtra("teacher_department", ""+teacher_department);

                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {

            return chatList.size();
        }

    }
}