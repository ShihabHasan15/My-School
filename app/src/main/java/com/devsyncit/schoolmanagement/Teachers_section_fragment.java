package com.devsyncit.schoolmanagement;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Teachers_section_fragment extends Fragment {

    ListView course_teachers_list;
    HashMap<String, String> hashMap;
    ArrayList<Course_teacher_info> arrayList = new ArrayList<>();
    Course_teacher_info courseTeacherInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_teachers_section_fragment, container, false);

        course_teachers_list = myView.findViewById(R.id.course_teachers_list);

        if (getArguments() != null){
            String student_class_no = getArguments().getString("student_class");

            Log.d("class_no", ""+student_class_no);

            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = Url_Ip.ip+"/Apps/student_teacher_list_get.php?cls="+student_class_no;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url
                    , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    Log.d("response_size", ""+response.length());

                    try {
                        for (int i=0; i<response.length(); i++){
                            JSONObject object = response.getJSONObject(i);

                            String teacher_name = object.getString("name");
                            String course_name = object.getString("course_name");

                            courseTeacherInfo = new Course_teacher_info(teacher_name, course_name);

                            arrayList.add(courseTeacherInfo);
                        }

                        Toast.makeText(getContext(), "On res called, list - "+arrayList.size(), Toast.LENGTH_LONG).show();

                        Course_teachers_list_adapter adapter = new Course_teachers_list_adapter(getContext(), arrayList);

                        course_teachers_list.setAdapter(adapter);

                    }catch (Exception e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), ""+error, Toast.LENGTH_LONG).show();

                }
            });

            queue.add(jsonArrayRequest);

        }


        return myView;
    }
}