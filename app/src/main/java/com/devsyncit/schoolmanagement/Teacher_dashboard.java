package com.devsyncit.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
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
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher_dashboard extends AppCompatActivity {
    RecyclerView timeLineRecyclerView;
    ImageSlider imageSlider;
    List<SlideModel> teacher_notice_board;
    public static ArrayList<HashMap<String, String>> dashboardList = new ArrayList<>();
    HashMap<String, String> dashboardMap;
    MaterialCardView view_students_btn;
    ProgressBar progress;
    NestedScrollView scrollView;
    ImageButton chat;
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
        progress = findViewById(R.id.progress);
        scrollView = findViewById(R.id.scroll_view);
        chat = findViewById(R.id.chat);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);


        Intent intent = getIntent();
//        String t_name = intent.getStringExtra("teacher_name");
//        String t_email = intent.getStringExtra("teacher_email");
//        String t_phone_number = intent.getStringExtra("teacher_phone_number");
        String t_id = intent.getStringExtra("teacher_id");
//        String totalStudents = intent.getStringExtra("total_students");

        Teacher_id.setTeacher_id(t_id);

        Log.d("teacher_id", ""+t_id);

//                                  My code



        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(Teacher_dashboard.this, chat_activity.class);
//                intent.putExtra("teacher_id", ""+t_id);
                Log.d("intent_teacher_id", ""+t_id);
                startActivity(chatIntent);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(Teacher_dashboard.this);
        String url = "http://192.168.0.106/Apps/teacher_course_list_data_get.php?t_id=" + t_id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progress.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                        Log.d("response", "" + response);

                        try {

                            int sum_of_students = 0;


                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                String t_name = jsonObject.getString("name");
                                String t_email = jsonObject.getString("email");
                                String t_mobile_number = jsonObject.getString("mobile_number");
                                String t_department = jsonObject.getString("department");
                                String t_course_id = jsonObject.getString("course_id");
                                String class_start_time = jsonObject.getString("class_start_time");
                                String class_end_time = jsonObject.getString("class_end_time");
                                String class_day = jsonObject.getString("class_day");
                                String Class = jsonObject.getString("class");
                                String no_of_students = jsonObject.getString("no_of_students");
                                String course_name = jsonObject.getString("course_name");


                                dashboardMap = new HashMap<>();
                                dashboardMap.put("name", t_name);
                                dashboardMap.put("email", t_email);
                                dashboardMap.put("mobile_number", t_mobile_number);
                                dashboardMap.put("department", t_department);
                                dashboardMap.put("course_id", t_course_id);
                                dashboardMap.put("class_start_time", class_start_time);
                                dashboardMap.put("class_end_time", class_end_time);
                                dashboardMap.put("class_day", class_day);
                                dashboardMap.put("Class", Class);
                                dashboardMap.put("no_of_students", no_of_students);
                                dashboardMap.put("course_name", course_name);

                                dashboardList.add(dashboardMap);


                                sum_of_students = sum_of_students + Integer.parseInt(no_of_students);

                            }

                            Log.d("students", "" + sum_of_students);
                            students_count_under_teacher.setText(""+sum_of_students);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);
            }
        });

        queue.add(jsonArrayRequest);


        Log.d("list", ""+dashboardList.size());


        view_students_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(Teacher_dashboard.this, teacher_classes_name.class);

//                postIntent.putExtra("teacher_name", "" + t_name);
//                postIntent.putExtra("teacher_email", "" + t_email);
//                postIntent.putExtra("teacher_mobile_number", "" + t_phone_number);
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

            TextView class_start_time, class_end_time, class_no, subject_name;
            MaterialCardView time_line_back;

            public TimeLineViewHolder(@NonNull View itemView) {
                super(itemView);

                class_start_time = itemView.findViewById(R.id.class_start_time);
                class_end_time = itemView.findViewById(R.id.class_end_time);
                class_no = itemView.findViewById(R.id.class_number);
                subject_name = itemView.findViewById(R.id.class_subject);
                time_line_back = itemView.findViewById(R.id.time_line_back);

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

            HashMap<String, String> courseInfoMap = dashboardList.get(position);
            String class_start_time = courseInfoMap.get("class_start_time");
            String class_end_time = courseInfoMap.get("class_end_time");
            String class_no = courseInfoMap.get("Class");
            String course_name = courseInfoMap.get("course_name");

            if (position==0){
                String teacher_name = courseInfoMap.get("name");
                String teacher_email = courseInfoMap.get("email");
                teacher_profile_name.setText(teacher_name);
                teacher_profile_email.setText(teacher_email);
            }


            Log.d("List size", "" + dashboardList.size());

            holder.class_start_time.setText(class_start_time);
            holder.class_end_time.setText(class_end_time);
            holder.class_no.setText(class_no);
            holder.subject_name.setText(course_name);

        }

        @Override
        public int getItemCount() {
            return dashboardList.size();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}