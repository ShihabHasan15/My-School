package com.devsyncit.schoolmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        student_dashboard_bottom_nav = findViewById(R.id.student_dashboard_bottom);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
        navigation_transaction.replace(R.id.navigation_frame, new Student_dashboard_frag());
        navigation_transaction.commit();

        student_dashboard_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.bottom_home){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
                    navigation_transaction.replace(R.id.navigation_frame, new Student_dashboard_frag());
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