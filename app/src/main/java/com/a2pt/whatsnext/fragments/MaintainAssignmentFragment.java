package com.a2pt.whatsnext.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.MainActivity;
import com.a2pt.whatsnext.adapters.AssignmentAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.Utility;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class MaintainAssignmentFragment extends Fragment {
    View view;
    FloatingActionButton fabNew;
    NewAssignmentFragment newAssignmentFragment = new NewAssignmentFragment();
    ListView lvAssignments;
    AssignmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintain_assignment_layout, container, false);

        lvAssignments = (ListView)view.findViewById(R.id.ma_lvAssignments);

        final List<Activity> assignments = new ArrayList<>();

        assignments.add(new Activity("WRAP302", "assignment", "Assignment 05", new LocalDate(2017,9,19), new LocalTime(23,35), 0));
        assignments.add(new Activity("WRAP302", "assignment", "Assignment 6", new LocalDate(2017,9,24), new LocalTime(12,5), 0));

        adapter = new AssignmentAdapter(getActivity(), assignments);

        lvAssignments.setAdapter(adapter);


        //This code is for Testing purposes, it should be replaced with a proper adapter that obtains items from Database
        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        //TODO: Change adapter correct adapter obtaining info from Database
        Spinner spinner = (Spinner)view.findViewById(R.id.spnModulesAssignment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.modules, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fabNew = (FloatingActionButton)view.findViewById(R.id.fabNewAssignment);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, newAssignmentFragment).addToBackStack(null).commit();
            }
        });



        return view;
    }

}
