package com.devsyncit.schoolmanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class Course_teachers_list_adapter extends BaseAdapter {

    Context context;
    ArrayList<Course_teacher_info> arrayList;

    public Course_teachers_list_adapter(Context context, ArrayList<Course_teacher_info> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.teachers_details_card_design, parent, false);

        TextView teacher_profile_name = convertView.findViewById(R.id.teacher_profile_name);
        TextView teacher_subject_name = convertView.findViewById(R.id.teacher_subject_name);

        Course_teacher_info info = arrayList.get(position);
        String teacher_name = info.getTeacher_name();
        String course_name = info.getSubject_name();


        teacher_profile_name.setText(""+teacher_name);
        teacher_subject_name.setText(""+course_name);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View bottom_sheet_view = inflater.inflate(R.layout.teacher_intro_sheet_dialog, null);

                BottomSheetDialog dialog = new BottomSheetDialog(context);

                dialog.setContentView(bottom_sheet_view);

                dialog.getWindow().setDimAmount(0.2f);

                dialog.show();

                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            }
        });


        return convertView;
    }
}
