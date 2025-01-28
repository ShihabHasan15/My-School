package com.devsyncit.schoolmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class assignment_list extends Fragment {

    ListView assignment_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View assignment_view = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        assignment_list = assignment_view.findViewById(R.id.assignment_name_list);

        assignmentAdapter adapter = new assignmentAdapter();
        assignment_list.setAdapter(adapter);


        return assignment_view;
    }




    public class assignmentAdapter extends BaseAdapter{

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

            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.assignment_list_design, parent, false);




            return convertView;
        }
    }
}