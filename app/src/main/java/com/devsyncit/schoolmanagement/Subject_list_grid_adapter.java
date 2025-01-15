package com.devsyncit.schoolmanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

public class Subject_list_grid_adapter extends BaseAdapter {

    Context context;

    public Subject_list_grid_adapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
        


        return convertView;
    }
}
