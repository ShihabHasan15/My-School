package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class Teacher_dashboard extends AppCompatActivity {
    RecyclerView timeLineRecyclerView;
    ImageSlider imageSlider;
    List<SlideModel> teacher_notice_board;
    MaterialCardView view_students_btn;
    TextView teacher_profile_name, teacher_profile_email;
    public static TextView students_count_under_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        timeLineRecyclerView = findViewById(R.id.timeLineRecyclerView);
        imageSlider = findViewById(R.id.teacher_dash_image_slider);
        view_students_btn = findViewById(R.id.view_students_btn);
        teacher_profile_name = findViewById(R.id.teacher_profile_name);
        teacher_profile_email = findViewById(R.id.teacher_profile_email);
        students_count_under_teacher = findViewById(R.id.students_count_under_teacher);

        Intent intent = getIntent();
        String t_name = intent.getStringExtra("teacher_name");
        String t_email = intent.getStringExtra("teacher_email");
        String t_phone_number = intent.getStringExtra("teacher_phone_number");
        String t_id = intent.getStringExtra("teacher_id");
        String totalStudents = intent.getStringExtra("total_students");

        teacher_profile_name.setText(t_name);
        teacher_profile_email.setText(t_email);
        students_count_under_teacher.setText(totalStudents);

        view_students_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(Teacher_dashboard.this, teacher_classes_name.class);

                postIntent.putExtra("teacher_name", "" + t_name);
                postIntent.putExtra("teacher_email", "" + t_email);
                postIntent.putExtra("teacher_mobile_number", "" + t_phone_number);
                postIntent.putExtra("teacher_id", "" + t_id);

                startActivity(postIntent);
            }
        });

        teacher_notice_board = new ArrayList<>();

        teacher_notice_board.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));
        teacher_notice_board.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));

        imageSlider.setImageList(teacher_notice_board);

        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        imageSlider.startSliding(3000);


        TimeLineAdapter adapter = new TimeLineAdapter();
        timeLineRecyclerView.setAdapter(adapter);
        timeLineRecyclerView.setLayoutManager(new LinearLayoutManager(Teacher_dashboard.this));


    }


    public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {


        public class TimeLineViewHolder extends RecyclerView.ViewHolder {

            public TimeLineViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        @NonNull
        @Override
        public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View TimeLineView = inflater.inflate(R.layout.time_line_view, parent, false);


            return new TimeLineViewHolder(TimeLineView);
        }

        @Override
        public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}