package com.devsyncit.schoolmanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Course_teachers_list_adapter extends BaseAdapter {

    Context context;

    public Course_teachers_list_adapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return 1;
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
