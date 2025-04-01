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

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class chat_activity extends AppCompatActivity {

    RecyclerView chat_recycle;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_recycle = findViewById(R.id.chat_recycle);

        teacherChatAdapter adapter = new teacherChatAdapter();
        chat_recycle.setAdapter(adapter);
        chat_recycle.setLayoutManager(new LinearLayoutManager(chat_activity.this));

//        String teacher_id = getIntent().getStringExtra("teacher_id");

        String teacher_id = Teacher_id.getTeacher_id();

        Log.d("teacher_id", teacher_id);

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("TeacherChatList")
                .child(teacher_id);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot studentIdSnap : snapshot.getChildren()) {

                        DataSnapshot referenceSnap = studentIdSnap.getChildren().iterator().next();
                        String studentClass = referenceSnap.child("student_class").getValue(String.class);
                        String studentId = referenceSnap.child("student_id").getValue(String.class);
                        String studentName = referenceSnap.child("student_name").getValue(String.class);

                        hashMap = new HashMap<>();
                        hashMap.put("studentName", studentName);
                        hashMap.put("studentClass", studentClass);
                        hashMap.put("studentId", studentId);

                        Log.d("FirebaseData", "Class: " + studentClass);
                        Log.d("FirebaseData", "ID: " + studentId);
                        Log.d("FirebaseData", "Name: " + studentName);

                        arrayList.add(hashMap);
                        adapter.notifyDataSetChanged();

                    }
                } else {
                    Log.d("FirebaseData", "No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Database error: " + error.getMessage());
            }
        });

    }

    public class teacherChatAdapter extends RecyclerView.Adapter<teacherChatAdapter.teacherCharViewHolder>{

        public class teacherCharViewHolder extends RecyclerView.ViewHolder{

            TextView student_name;
            MaterialCardView chatPerson;

            public teacherCharViewHolder(@NonNull View itemView) {
                super(itemView);
                student_name = itemView.findViewById(R.id.sender_name);
                chatPerson = itemView.findViewById(R.id.chatPerson);
            }
        }

        @NonNull
        @Override
        public teacherCharViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View chat_view = inflater.inflate(R.layout.teacher_chat_design, parent, false);

            return new teacherCharViewHolder(chat_view);
        }

        @Override
        public void onBindViewHolder(@NonNull teacherCharViewHolder holder, int position) {

            HashMap<String, String> getDetailsMap = arrayList.get(position);
            String studentName = getDetailsMap.get("studentName");
            String studentClass = getDetailsMap.get("studentClass");
            String studentId = getDetailsMap.get("studentId");

            holder.student_name.setText(studentName);

            String t_id = Teacher_id.getTeacher_id();


            holder.chatPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(chat_activity.this, teacher_chatting.class);
                    intent.putExtra("teacher_id", ""+t_id);
                    intent.putExtra("student_id", ""+studentId);
                    intent.putExtra("student_name", ""+studentName);

                    Log.d("id", t_id+"_"+studentId);

                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {

            Log.d("size", ""+arrayList.size());

            return arrayList.size();
        }
    }


}