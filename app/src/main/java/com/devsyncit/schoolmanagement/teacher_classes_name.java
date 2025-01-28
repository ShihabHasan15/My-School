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

public class teacher_classes_name extends AppCompatActivity {

    RecyclerView classes_and_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classes_name);


        classes_and_section = findViewById(R.id.classes_and_section);

        ClassesAndSectionAdapter adapter = new ClassesAndSectionAdapter();
        classes_and_section.setAdapter(adapter);

        classes_and_section.setLayoutManager(new LinearLayoutManager(teacher_classes_name.this));

    }

    public class ClassesAndSectionAdapter extends RecyclerView.Adapter <ClassesAndSectionAdapter.ClassesAndSectionViewHolder>{

        public class ClassesAndSectionViewHolder extends RecyclerView.ViewHolder{

            public ClassesAndSectionViewHolder(@NonNull View itemView) {
                super(itemView);
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

        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }


}