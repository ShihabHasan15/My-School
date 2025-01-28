package com.devsyncit.schoolmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class student_assignments extends Fragment {

    TextView tab1, tab2;
    TextView selected_tab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View assignmentView = inflater.inflate(R.layout.fragment_student_assignments, container, false);

        tab1 = assignmentView.findViewById(R.id.tab1);
        tab2 = assignmentView.findViewById(R.id.tab2);
        selected_tab = assignmentView.findViewById(R.id.textSelected);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.assignment_list_frame,new assignment_list());
        transaction.commit();

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.assignment_list_frame,new assignment_list());
                transaction.commit();

                tab1.setTextColor(getResources().getColor(R.color.white));
                tab2.setTextColor(getResources().getColor(R.color.blue));
                selected_tab.animate().x(0).setDuration(60);

            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.assignment_list_frame,new student_assignment_list_submitted());
                transaction.commit();

                tab2.setTextColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.blue));
                int size = tab2.getWidth();
                selected_tab.animate().x(size).setDuration(60);

            }
        });


        return assignmentView;
    }
}