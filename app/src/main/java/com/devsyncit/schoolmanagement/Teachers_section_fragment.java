package com.devsyncit.schoolmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Teachers_section_fragment extends Fragment {

    ListView course_teachers_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_teachers_section_fragment, container, false);

        course_teachers_list = myView.findViewById(R.id.course_teachers_list);

        Course_teachers_list_adapter adapter = new Course_teachers_list_adapter(getContext());

        course_teachers_list.setAdapter(adapter);

        return myView;
    }
}