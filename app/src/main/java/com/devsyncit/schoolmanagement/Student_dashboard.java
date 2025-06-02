package com.devsyncit.schoolmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_dashboard extends AppCompatActivity {

    BottomNavigationView student_dashboard_bottom_nav;
    StudentProfileDb profileDb;
    DatabaseReference dbRef;
    Fragment studentDashboardFrag = new Student_dashboard_frag();
    Fragment teachersSectionFragment = new Teachers_section_fragment();
    Fragment studentAssignmentsFrag = new student_assignments();
    Fragment activeFragment = studentDashboardFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        profileDb = new StudentProfileDb(Student_dashboard.this);

        //==========Getting Data from intent================

        Intent getIntentData = getIntent();
        String student_name = getIntentData.getStringExtra("student_name");
        String student_class_no = getIntentData.getStringExtra("student_class");
        String student_roll_no = getIntentData.getStringExtra("student_roll");
        String student_email = getIntentData.getStringExtra("student_email");
        String student_mb_number = getIntentData.getStringExtra("student_mb_number");

        //=============End==================================


        SQLiteDatabase profile_database = profileDb.getWritableDatabase();
        boolean isSuccessfull = profileDb.insertData(student_name, student_roll_no, student_class_no, student_email, student_mb_number);

        Toast.makeText(Student_dashboard.this, "" + isSuccessfull, Toast.LENGTH_LONG).show();

        Log.d("dash_info", "" + student_name);
        Log.d("dash_info", "" + student_class_no);
        Log.d("dash_info", "" + student_roll_no);

        student_dashboard_bottom_nav = findViewById(R.id.student_dashboard_bottom);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            bundle.putString("student_name", "" + student_name);
            bundle.putString("student_class", "" + student_class_no);
            bundle.putString("student_roll", "" + student_roll_no);
            bundle.putString("student_email", "" + student_email);
            bundle.putString("student_mb_number", "" + student_mb_number);
            studentDashboardFrag.setArguments(bundle);

            Bundle bundle2 = new Bundle();
            bundle2.putString("student_class", student_class_no);
            teachersSectionFragment.setArguments(bundle2);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.navigation_frame, studentAssignmentsFrag, "assignments").hide(studentAssignmentsFrag)
                .add(R.id.navigation_frame, teachersSectionFragment, "teachers").hide(teachersSectionFragment)
                .add(R.id.navigation_frame, studentDashboardFrag, "dashboard") // default visible
                .commit();

        student_dashboard_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//                if (item.getItemId()==R.id.bottom_home){
//
//                    if (getIntent().getExtras() != null){
//                        Bundle bundle = new Bundle();
//                        bundle.putString("student_name", ""+student_name);
//                        bundle.putString("student_class", ""+student_class_no);
//                        bundle.putString("student_roll", ""+student_roll_no);
//                        studentDashboardFrag.setArguments(bundle);
//                    }
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
//                    navigation_transaction.replace(R.id.navigation_frame, studentDashboardFrag);
//                    navigation_transaction.commit();
//
//                } else if (item.getItemId()==R.id.bottom_teachers) {
//
//                    Teachers_section_fragment teachersSectionFragment = new Teachers_section_fragment();
//                    if (getIntent().getExtras() != null){
//                        Bundle bundle = new Bundle();
//                        bundle.putString("student_class", ""+student_class_no);
//                        teachersSectionFragment.setArguments(bundle);
//                    }
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
//                    navigation_transaction.replace(R.id.navigation_frame, teachersSectionFragment);
//                    navigation_transaction.commit();
//
//                } else if (item.getItemId()==R.id.bottom_assignments) {
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction navigation_transaction = fragmentManager.beginTransaction();
//                    navigation_transaction.replace(R.id.navigation_frame, new student_assignments());
//                    navigation_transaction.commit();
//
//                } else if (item.getItemId()==R.id.bottom_widget_menu) {
//
//                    LayoutInflater inflater = getLayoutInflater();
//
//                    View bottom_sheet_view = inflater.inflate(R.layout.menu_bottom_sheet, null);
//
//                    TextView menu_bottom_student_name = bottom_sheet_view.findViewById(R.id.menu_bottom_student_name);
//                    TextView menu_bottom_class = bottom_sheet_view.findViewById(R.id.menu_bottom_class);
//                    TextView menu_bottom_class_roll = bottom_sheet_view.findViewById(R.id.menu_bottom_class_roll);
//                    CircleImageView menu_profile = bottom_sheet_view.findViewById(R.id.menu_profile);
//                    LinearLayout menu_profile_view = bottom_sheet_view.findViewById(R.id.profile_view);
//
//                    ImageButton attendence = bottom_sheet_view.findViewById(R.id.student_payment);
//
//                    attendence.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(Student_dashboard.this, Payment_activity.class));
//                        }
//                    });
//
//                    dbRef = FirebaseDatabase.getInstance().getReference("Users").child(""+student_roll_no).child("profileImage");
//
//                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()) {
//                                String base64Image = snapshot.getValue(String.class);
//
//                                Bitmap bitmap = decodeBase64ToBitmap(base64Image);
//                                menu_profile.setImageBitmap(bitmap);
//                            } else {
//                                Toast.makeText(getApplicationContext(), "No profile image found", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                    menu_bottom_student_name.setText(student_name);
//                    menu_bottom_class.setText(student_class_no);
//                    menu_bottom_class_roll.setText(student_roll_no);
//
//                    menu_profile_view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(new Intent(Student_dashboard.this, Student_profile.class));
//                        }
//                    });
//
//
//                    BottomSheetDialog dialog = new BottomSheetDialog(Student_dashboard.this);
//
//                    dialog.setContentView(bottom_sheet_view);
//
//                    dialog.getWindow().setDimAmount(0.2f);
//
//                    dialog.show();
//
//                    dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//
//                }


//                switch (item.getItemId()) {
//                    case R.id.bottom_home:
//                        switchFragment(studentDashboardFrag);
//                        return true;
//
//                    case R.id.bottom_teachers:
//                        switchFragment(teachersSectionFragment);
//                        return true;
//
//                    case R.id.bottom_assignments:
//                        switchFragment(studentAssignmentsFrag);
//                        return true;
//
//                    case R.id.bottom_widget_menu:
//                        showBottomSheet();
//                        return true;
//                }

                if (item.getItemId() == R.id.bottom_home) {
                    switchFragment(studentDashboardFrag);
                } else if (item.getItemId() == R.id.bottom_teachers) {
                    switchFragment(teachersSectionFragment);
                } else if (item.getItemId() == R.id.bottom_assignments) {
                    switchFragment(studentAssignmentsFrag);
                } else {
                    showBottomSheet();
                }

                return true;
            }
        });
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void switchFragment(Fragment targetFragment) {
        if (targetFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(targetFragment)
                    .commit();
            activeFragment = targetFragment;
        }
    }

    private void showBottomSheet() {
        LayoutInflater inflater = getLayoutInflater();

        View bottom_sheet_view = inflater.inflate(R.layout.menu_bottom_sheet, null);

        TextView menu_bottom_student_name = bottom_sheet_view.findViewById(R.id.menu_bottom_student_name);
        TextView menu_bottom_class = bottom_sheet_view.findViewById(R.id.menu_bottom_class);
        TextView menu_bottom_class_roll = bottom_sheet_view.findViewById(R.id.menu_bottom_class_roll);
        CircleImageView menu_profile = bottom_sheet_view.findViewById(R.id.menu_profile);
        LinearLayout menu_profile_view = bottom_sheet_view.findViewById(R.id.profile_view);

        ImageButton attendence = bottom_sheet_view.findViewById(R.id.student_payment);


        Intent getIntentData = getIntent();
        String student_name = getIntentData.getStringExtra("student_name");
        String student_class_no = getIntentData.getStringExtra("student_class");
        String student_roll_no = getIntentData.getStringExtra("student_roll");
        String student_email = getIntentData.getStringExtra("student_email");
        String student_mb_number = getIntentData.getStringExtra("student_mb_number");

        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_dashboard.this, Payment_activity.class));
            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference("Users").child("" + student_roll_no).child("profileImage");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String base64Image = snapshot.getValue(String.class);

                    Bitmap bitmap = decodeBase64ToBitmap(base64Image);
                    menu_profile.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getApplicationContext(), "No profile image found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        menu_bottom_student_name.setText(student_name);
        menu_bottom_class.setText(student_class_no);
        menu_bottom_class_roll.setText(student_roll_no);

        menu_profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_dashboard.this, Student_profile.class));
            }
        });


        BottomSheetDialog dialog = new BottomSheetDialog(Student_dashboard.this);

        dialog.setContentView(bottom_sheet_view);

        dialog.getWindow().setDimAmount(0.2f);

        dialog.show();

        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
    }

}