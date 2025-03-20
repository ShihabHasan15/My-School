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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class teacher_chatting extends AppCompatActivity {

    RecyclerView messages;
    EditText message_edittext;
    Button send_btn;
    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<String> senderList = new ArrayList<>();
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chatting);

        messages = findViewById(R.id.messages);
        message_edittext = findViewById(R.id.message_edittext);
        send_btn = findViewById(R.id.send_btn);
        userName = findViewById(R.id.username);

        Intent intent = getIntent();
        String teacher_id = intent.getStringExtra("teacher_id");
        String student_id = intent.getStringExtra("student_id");
        String student_name = intent.getStringExtra("student_name");


        userName.setText(""+student_name);

        Log.d("ids", ""+teacher_id);
        Log.d("ids", ""+student_id);


        chatAdapter adapter = new chatAdapter();
        messages.setLayoutManager(new LinearLayoutManager(teacher_chatting.this));
        messages.setAdapter(adapter);

        String chatRoom = teacher_id+"_"+student_id;

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoom);


        chatRef.addValueEventListener(new ValueEventListener() {
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

        Log.d("arraylist", ""+messageList);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chat_message = message_edittext.getText().toString();

                if (chat_message.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No message typed", Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, String> messageMap = new HashMap<>();
                    messageMap.put("sender", ""+teacher_id);
                    messageMap.put("message", ""+chat_message);

                    chatRef.push().setValue(messageMap);
                    message_edittext.setText("");
                    if (messageList.size() > 0) {
                        messages.post(() -> messages.smoothScrollToPosition(messageList.size() - 1));
                    }

                }
            }
        });


    }


  public class chatAdapter extends RecyclerView.Adapter{
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

          String myUserId = getIntent().getStringExtra("teacher_id");

          if (senderId.equals(myUserId)){
              return RIGHT_VIEW;
          }else {
              return LEFT_VIEW;
          }

      }
  }

}