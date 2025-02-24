package com.devsyncit.schoolmanagement;

import android.content.Context;

import com.airbnb.lottie.animation.content.Content;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FetchedTeacherClassScheduleThread extends Thread{
    HashMap<String, String> hashMap;
    private Context context;
    public static ArrayList<HashMap<String, String>> scheduleArrayList = new ArrayList<>();
    public String teacher_id;
    public String day;
    OnFetchCompleteListener listener;
    public interface OnFetchCompleteListener{
        void onFetchComplete(String class_start_time, String class_end_time, String course_name, String Class);
    }
    public FetchedTeacherClassScheduleThread(Context context, String teacher_id, String day, OnFetchCompleteListener listener){
        this.context = context;
        this.teacher_id = teacher_id;
        this.day = day;
        this.listener = listener;
    }


    @Override
    public void run() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.0.104/Apps/teacher_course_schedule_data_get.php?t_id="+teacher_id+"&day="+day;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        scheduleArrayList.clear();
                        String Class="", course_name="", class_start_time="", class_end_time="";

                        for (int i=0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("Id");
                                String name = jsonObject.getString("Name");
                                String course_id = jsonObject.getString("course_id");
                                course_name = jsonObject.getString("course_name");
                                class_start_time = jsonObject.getString("class_start_time");
                                class_end_time = jsonObject.getString("class_end_time");
                                String class_day = jsonObject.getString("class_day");
                                Class = jsonObject.getString("class");


                                hashMap = new HashMap<>();

                                hashMap.put("id", id);
                                hashMap.put("name", name);
                                hashMap.put("course_id", course_id);
                                hashMap.put("course_name", course_name);
                                hashMap.put("class_start_time", class_start_time);
                                hashMap.put("class_end_time", class_end_time);
                                hashMap.put("class_day", class_day);
                                hashMap.put("Class", Class);

                                scheduleArrayList.add(hashMap);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if (listener != null){
                            listener.onFetchComplete(class_start_time, class_end_time, course_name, Class);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);


    }
}
