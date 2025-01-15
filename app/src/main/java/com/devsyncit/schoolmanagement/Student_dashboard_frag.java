package com.devsyncit.schoolmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_student_dashboard_frag, container, false);

        imageSlider = myView.findViewById(R.id.image_slider);
        student_list_grid = myView.findViewById(R.id.subject_list_grid);

        Subject_list_grid_adapter adapter = new Subject_list_grid_adapter(getContext());

        student_list_grid.setAdapter(adapter);


        imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));

        imageSlider.setImageList(imageList);

        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        imageSlider.startSliding(3000);



        return myView;
    }
}