package com.devsyncit.schoolmanagement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Subject_list_grid_adapter extends BaseAdapter {

    Context context;

    public Subject_list_grid_adapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
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

        convertView = inflater.inflate(R.layout.subject_list_background_design, parent, false);

        MaterialCardView subject_card = convertView.findViewById(R.id.subject_card);
        ImageView subject_image = convertView.findViewById(R.id.subject_image);
        TextView subject_name = convertView.findViewById(R.id.subject_name);

        if (position==0){
            subject_card.setBackgroundTintList(ColorStateList.valueOf(Color.MAGENTA));
            subject_image.setImageResource(R.drawable.english_subject);
            subject_name.setText("English");
        } else if (position==1) {
            subject_image.setImageResource(R.drawable.math_subject_image);
            subject_name.setText("Math");
        } else if (position==2) {
            subject_card.setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
            subject_image.setImageResource(R.drawable.chemistry_subject);
            subject_name.setText("Chemistry");
        } else if (position==3) {
            subject_card.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            subject_image.setImageResource(R.drawable.biology_subject);
            subject_name.setText("Biology");
        } else if (position==4) {
            subject_card.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
            subject_image.setImageResource(R.drawable.physics_subject);
            subject_name.setText("Physics");
        } else if (position==5) {
            subject_card.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            subject_image.setImageResource(R.drawable.history_subject);
            subject_name.setText("History");
        }


        return convertView;
    }
}
