package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Student_dashboard_frag extends Fragment {

    ImageSlider imageSlider;
    List<SlideModel> imageList;
    GridView student_list_grid;
    TextView student_profile_name, student_class, student_roll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_student_dashboard_frag, container, false);

        imageSlider = myView.findViewById(R.id.image_slider);
        student_list_grid = myView.findViewById(R.id.subject_list_grid);
        student_profile_name = myView.findViewById(R.id.student_profile_name);
        student_class = myView.findViewById(R.id.student_class);
        student_roll = myView.findViewById(R.id.student_roll);

        Subject_list_grid_adapter adapter = new Subject_list_grid_adapter(getContext());

        student_list_grid.setAdapter(adapter);


        //==========Getting Data from intent================
        if (getArguments() != null) {
            String student_name = getArguments().getString("student_name");
            String student_class_no = getArguments().getString("student_class");
            String student_roll_no = getArguments().getString("student_roll");

            //=============End==================================

            student_profile_name.setText("" + student_name);
            student_class.setText("" + student_class_no);
            student_roll.setText("" + student_roll_no.charAt(student_roll_no.length() - 4) + student_roll_no.charAt(student_roll_no.length() - 3)
                    + student_roll_no.charAt(student_roll_no.length() - 2) + student_roll_no.charAt(student_roll_no.length() - 1));
        }

        imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));

        imageSlider.setImageList(imageList);

        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        imageSlider.startSliding(3000);



        return myView;
    }
}