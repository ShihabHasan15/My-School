package com.devsyncit.schoolmanagement;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FetchedTeacherClassesThread extends Thread {

    HashMap<String, String> hashMap;
    private Context context;
    public static ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public String teacher_id;
    OnFetchCompleteListener listener;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);

    public interface OnFetchCompleteListener {
        void onFetchComplete(int totalStudents);
    }

    public FetchedTeacherClassesThread(Context context, String teacher_id, OnFetchCompleteListener listener) {
        this.context = context;
        this.teacher_id = teacher_id;
        this.listener = listener;
    }

    @Override
    public void run() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.0.105/Apps/teacher_course_list_data_get.php?t_id="+teacher_id+"&day="+dayOfTheWeek;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arrayList.clear();

                int sum_of_students = 0;

                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        hashMap = new HashMap<>();
                        hashMap.put("name", jsonObject.getString("name"));
                        hashMap.put("Class", jsonObject.getString("class"));
//                        hashMap.put("section", jsonObject.getString("section"));
                        String no_of_students = jsonObject.getString("no_of_students");
                        hashMap.put("no_of_students", no_of_students);
                        hashMap.put("course_name", jsonObject.getString("course_name"));
                        hashMap.put("id", jsonObject.getString("id"));
                        hashMap.put("course_id", jsonObject.getString("course_id"));
                        hashMap.put("class_start_time", jsonObject.getString("class_start_time"));
                        hashMap.put("class_end_time", jsonObject.getString("class_end_time"));

                        arrayList.add(hashMap);

                        sum_of_students = sum_of_students + Integer.parseInt(no_of_students.trim());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                if (listener != null) {
                    listener.onFetchComplete(sum_of_students);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onFetchComplete(0); // Return 0 if error occurs
                }
            }
        });

        queue.add(jsonArrayRequest);


    }

}
