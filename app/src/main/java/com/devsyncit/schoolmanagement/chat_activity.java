package com.devsyncit.schoolmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class chat_activity extends AppCompatActivity {

    RecyclerView chat_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_recycle = findViewById(R.id.chat_recycle);

        teacherChatAdapter adapter = new teacherChatAdapter();
        chat_recycle.setAdapter(adapter);
        chat_recycle.setLayoutManager(new LinearLayoutManager(chat_activity.this));


    }

    public class teacherChatAdapter extends RecyclerView.Adapter<teacherChatAdapter.teacherCharViewHolder>{

        public class teacherCharViewHolder extends RecyclerView.ViewHolder{

            public teacherCharViewHolder(@NonNull View itemView) {
                super(itemView);
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

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}