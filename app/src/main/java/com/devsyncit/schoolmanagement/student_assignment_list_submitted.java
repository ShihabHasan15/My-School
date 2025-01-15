package com.devsyncit.schoolmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class student_assignment_list_submitted extends Fragment {

    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View submitted_view = inflater.inflate(R.layout.fragment_student_assignment_list_submitted, container, false);

        listView = submitted_view.findViewById(R.id.submitted_assignment);

        submittedListAdapter adapter = new submittedListAdapter();
        listView.setAdapter(adapter);

        return submitted_view;
    }


    public class submittedListAdapter extends BaseAdapter{

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
            convertView = inflater.inflate(R.layout.submitted_assignments_design, parent, false);




            return convertView;
        }
    }
}