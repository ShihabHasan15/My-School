package com.devsyncit.schoolmanagement;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_dashboard_frag extends Fragment {

    ImageSlider imageSlider;
    List<SlideModel> imageList;
    GridView student_list_grid;
    TextView student_profile_name, student_class, student_roll, progressText;
    ImageButton student_chat;
    LinearLayout student_profile;
    DatabaseReference dbRef;
    CircleImageView student_profile_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_student_dashboard_frag, container, false);

//        imageSlider = myView.findViewById(R.id.image_slider);
        student_list_grid = myView.findViewById(R.id.subject_list_grid);
        student_profile_name = myView.findViewById(R.id.student_profile_name);
        student_class = myView.findViewById(R.id.student_class);
        student_roll = myView.findViewById(R.id.student_roll);
        student_chat = myView.findViewById(R.id.student_chat);
        student_profile = myView.findViewById(R.id.student_profile);
        progressText = myView.findViewById(R.id.progressText);
        student_profile_image = myView.findViewById(R.id.student_profile_image);


        float targetProgress = 80f;

        CircularProgressBar circularProgressBar = myView.findViewById(R.id.circularProgressBar);
// Set Progress
        circularProgressBar.setProgress(0f);
// or with animation
        circularProgressBar.setProgressWithAnimation(targetProgress, 4000L); // =1s

// Set Progress Max
        circularProgressBar.setProgressMax(100f);

// Set ProgressBar Color
//        circularProgressBar.setProgressBarColor(R.color.blue);

// or with gradient
//        circularProgressBar.setProgressBarColorStart(Color.GRAY);
//        circularProgressBar.setProgressBarColorEnd(Color.RED);
//        circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set background ProgressBar Color
//        circularProgressBar.setBackgroundProgressBarColor(R.color.extra_light_blue);

// or with gradient
//        circularProgressBar.setBackgroundProgressBarColorStart(Color.WHITE);
//        circularProgressBar.setBackgroundProgressBarColorEnd(Color.RED);
//        circularProgressBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set Width
        circularProgressBar.setProgressBarWidth(15f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(25f); // in DP

// Other
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(180f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) targetProgress);
        valueAnimator.setDuration(4000L); // Same duration as progress bar

        valueAnimator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            progressText.setText(currentValue + "%");
        });

        valueAnimator.start();

        //==========Getting Data from intent================
        if (getArguments() != null) {
            String student_name = getArguments().getString("student_name");
            String student_class_no = getArguments().getString("student_class");
            String student_roll_no = getArguments().getString("student_roll");
            String student_email = getArguments().getString("student_email");
            String student_phone_number = getArguments().getString("student_mb_number");

            //=============End==================================


            student_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), Student_profile.class);

                    intent.putExtra("student_name", student_name);
                    intent.putExtra("student_class_no", student_class_no);
                    intent.putExtra("student_roll_no", student_roll_no);
                    intent.putExtra("student_email", student_email);
                    intent.putExtra("student_phone_number", student_phone_number);

                    startActivity(intent);
                }
            });

            dbRef = FirebaseDatabase.getInstance().getReference("Users").child(""+student_roll_no).child("profileImage");

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String base64Image = snapshot.getValue(String.class);

                        Bitmap bitmap = decodeBase64ToBitmap(base64Image);
                        student_profile_image.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getContext(), "No profile image found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Log.d("info", ""+student_name);
            Log.d("info", ""+student_class_no);
            Log.d("info", ""+student_roll_no);

            Subject_list_grid_adapter adapter = new Subject_list_grid_adapter(getContext());

            student_list_grid.setAdapter(adapter);

            student_profile_name.setText("" + student_name);
            student_class.setText("" + student_class_no);
            student_roll.setText("" + student_roll_no.charAt(student_roll_no.length() - 4) + student_roll_no.charAt(student_roll_no.length() - 3)
                    + student_roll_no.charAt(student_roll_no.length() - 2) + student_roll_no.charAt(student_roll_no.length() - 1));





            student_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), student_chat_activity.class);
                    intent.putExtra("student_name", ""+student_name);
                    intent.putExtra("student_class_no", ""+student_class_no);
                    intent.putExtra("student_roll", ""+student_roll_no);
                    startActivity(intent);
                }
            });
        }

//        imageList = new ArrayList<>();
//
//        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));
//        imageList.add(new SlideModel(R.drawable.notice_board, ScaleTypes.FIT));
//
//        imageSlider.setImageList(imageList);
//
//        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
//
//        imageSlider.startSliding(3000);


        return myView;
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}