package com.devsyncit.schoolmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class message_chatting_activity extends AppCompatActivity {

    TextView userName;
    RecyclerView messages;
    EditText message_edittext;
    Button send_btn;
    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<String> senderList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chatting);

        userName = findViewById(R.id.userName);
        messages = findViewById(R.id.messages);
        message_edittext = findViewById(R.id.message_edittext);
        send_btn = findViewById(R.id.message_send_btn);

    }
}