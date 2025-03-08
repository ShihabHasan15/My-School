package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class student_chatting_activity extends AppCompatActivity {
    RecyclerView messages;
    EditText message_edittext;
    Button sendBtn;
    TextView userName;
    HashMap<String, String> hashMap;
    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<String> senderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chatting);

        messages = findViewById(R.id.messages);
        message_edittext = findViewById(R.id.message_edittext);
        sendBtn = findViewById(R.id.send_btn);
        userName = findViewById(R.id.username);

        String student_name = getIntent().getStringExtra("student_name");
        String student_class_no = getIntent().getStringExtra("student_class_no");
        String student_roll = getIntent().getStringExtra("student_roll");

        String teacher_name = getIntent().getStringExtra("teacher_name");
        String teacher_id = getIntent().getStringExtra("teacher_id");
        String teacher_department = getIntent().getStringExtra("teacher_department");

        userName.setText(""+teacher_name);


        ChatAdapter adapter = new ChatAdapter();
        messages.setAdapter(adapter);
        messages.setLayoutManager(new LinearLayoutManager(student_chatting_activity.this));


//        String chat_message = message_edittext.getText().toString();

        String chat_room_id = generateChatRoomID(teacher_id, student_roll);
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Messages").child(chat_room_id);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chat_message = message_edittext.getText().toString();

                if (chat_message.isEmpty()){
                    Toast.makeText(student_chatting_activity.this, "No message typed", Toast.LENGTH_SHORT).show();
                }else {

                    DatabaseReference chatListRef = FirebaseDatabase.getInstance().getReference("TeacherChatList")
                            .child(teacher_id).child(student_roll);

                    hashMap = new HashMap<>();
                    hashMap.put("student_name", student_name);
                    hashMap.put("student_id", student_roll);
                    hashMap.put("student_class", student_class_no);

                    chatListRef.push().setValue(hashMap);


                    HashMap<String, String> messageMap = new HashMap<>();
                    messageMap.put("sender", ""+student_roll);
                    messageMap.put("message", ""+chat_message);

                    messagesRef.push().setValue(messageMap);
                    message_edittext.setText("");

                    
                }
            }
        });




        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageList.clear();
                senderList.clear();

                for (DataSnapshot messageSnap : snapshot.getChildren()){
                    String sender = messageSnap.child("sender").getValue(String.class);
                    String getMessage = messageSnap.child("message").getValue(String.class);
                    if (!sender.isBlank() && !getMessage.isBlank()){
                        messageList.add(getMessage);
                        senderList.add(sender);
                    }
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String generateChatRoomID(String teacherId, String studentRoll) {

        return teacherId+"_"+studentRoll;
    }


    public class ChatAdapter extends RecyclerView.Adapter{

        int RIGHT_VIEW = 0;
        int LEFT_VIEW = 1;

        public class RightViewHolder extends RecyclerView.ViewHolder{
            TextView textViewRightMessage;

            public RightViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewRightMessage = itemView.findViewById(R.id.textViewRightMessage);

            }
        }

        public class LeftViewHolder extends RecyclerView.ViewHolder{
            TextView textViewLeftMessage;

            public LeftViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewLeftMessage = itemView.findViewById(R.id.textViewLeftMessage);

            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();

            if (viewType == RIGHT_VIEW){
                View rightView = inflater.inflate(R.layout.item_right_message, parent, false);
                return new RightViewHolder(rightView);
            }else {
                View leftView = inflater.inflate(R.layout.item_left_message, parent, false);
                return new LeftViewHolder(leftView);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position) == RIGHT_VIEW){
                RightViewHolder rightViewHolder = (RightViewHolder) holder;

                String chat_message = messageList.get(position);

                rightViewHolder.textViewRightMessage.setText(chat_message);


            }else {
                LeftViewHolder leftViewHolder = (LeftViewHolder) holder;

                String chat_message = messageList.get(position);

                leftViewHolder.textViewLeftMessage.setText(chat_message);

            }


        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        @Override
        public int getItemViewType(int position) {



            String senderId = senderList.get(position);

            String myUserId = getIntent().getStringExtra("student_roll");

            if (senderId.equals(myUserId)){
                return RIGHT_VIEW;
            }else {
                return LEFT_VIEW;
            }

        }
    }

}