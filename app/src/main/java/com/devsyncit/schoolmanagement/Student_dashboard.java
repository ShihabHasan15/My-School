package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class Student_dashboard extends AppCompatActivity {

    BottomNavigationView student_dashboard_bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);


        //==========Getting Data from intent================

        Intent getIntentData = getIntent();
        String student_name = getIntentData.getStringExtra("student_name");
        String student_class_no = getIntentData.getStringExtra("student_class");
        String student_roll_no = getIntentData.getStringExtra("student_roll");

        //=============End==================================


        student_dashboard_bottom_nav = findViewById(R.id.student_dashboard_bottom);

        Student_dashboard_frag studentDashboardFrag = new Student_dashboard_frag();

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            bundle.putString("student_name", ""+student_name);
            bundle.putString("student_class", ""+student_class_no);
            bundle.putString("student_roll", ""+student_roll_no);
            studentDashboardFrag.setArguments(bundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
        navigation_transaction.replace(R.id.navigation_frame, studentDashboardFrag);
        navigation_transaction.commit();

        student_dashboard_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.bottom_home){

                    Student_dashboard_frag studentDashboardFrag = new Student_dashboard_frag();
                    if (getIntent().getExtras() != null){
                        Bundle bundle = new Bundle();
                        bundle.putString("student_name", ""+student_name);
                        bundle.putString("student_class", ""+student_class_no);
                        bundle.putString("student_roll", ""+student_roll_no);
                        studentDashboardFrag.setArguments(bundle);
                    }

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
                    navigation_transaction.replace(R.id.navigation_frame, studentDashboardFrag);
                    navigation_transaction.commit();

                } else if (item.getItemId()==R.id.bottom_teachers) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
                    navigation_transaction.replace(R.id.navigation_frame, new Teachers_section_fragment());
                    navigation_transaction.commit();

                } else if (item.getItemId()==R.id.bottom_assignments) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
                    navigation_transaction.replace(R.id.navigation_frame, new student_assignments());
                    navigation_transaction.commit();

                } else if (item.getItemId()==R.id.bottom_widget_menu) {

                    LayoutInflater inflater = getLayoutInflater();

                    View bottom_sheet_view = inflater.inflate(R.layout.menu_bottom_sheet, null);

                    TextView menu_bottom_student_name = bottom_sheet_view.findViewById(R.id.menu_bottom_student_name);
                    TextView menu_bottom_class = bottom_sheet_view.findViewById(R.id.menu_bottom_class);
                    TextView menu_bottom_class_roll = bottom_sheet_view.findViewById(R.id.menu_bottom_class_roll);

                    ImageButton attendence = bottom_sheet_view.findViewById(R.id.student_attendence);

                    attendence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Student_dashboard.this, Payment_activity.class));
                        }
                    });

                    menu_bottom_student_name.setText(student_name);
                    menu_bottom_class.setText(student_class_no);
                    menu_bottom_class_roll.setText(student_roll_no);


                    BottomSheetDialog dialog = new BottomSheetDialog(Student_dashboard.this);

                    dialog.setContentView(bottom_sheet_view);

                    dialog.getWindow().setDimAmount(0.2f);

                    dialog.show();

                    dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

                }

                return true;
            }
        });




    }
}