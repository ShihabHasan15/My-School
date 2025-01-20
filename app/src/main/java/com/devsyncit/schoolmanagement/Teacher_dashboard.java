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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        timeLineRecyclerView = findViewById(R.id.timeLineRecyclerView);
        imageSlider = findViewById(R.id.teacher_dash_image_slider);
        view_students_btn = findViewById(R.id.view_students_btn);


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


    public class TimeLineAdapter extends RecyclerView.Adapter <TimeLineAdapter.TimeLineViewHolder> {


        public class TimeLineViewHolder extends RecyclerView.ViewHolder{

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


}