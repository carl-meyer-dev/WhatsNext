package com.a2pt.whatsnext.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.adapters.ActivityAdapter;
import com.a2pt.whatsnext.adapters.AssignmentAdapter;
import com.a2pt.whatsnext.adapters.TestAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.Utility;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class UpcomingEventsFragment extends Fragment {

    View view;
    private AssignmentAdapter assignmentsAdapter;
    private TestAdapter testsAdapter;
    private ListView lvAssignments, lvTests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_events_layout, container, false);

        lvTests = (ListView)view.findViewById(R.id.ue_lvTests);
        lvAssignments = (ListView)view.findViewById(R.id.ue_lvAssignments);

        final List<Activity> assignments = new ArrayList<>();
        final List<Activity> tests = new ArrayList<>();

        //add dummy variables
        assignments.add(new Activity("WRAP302", Activity.Activity_Type.ASSIGNMENT, "Assignment 5", new LocalDate(2017,9,12), new LocalTime(23,35), Activity.Assignment_Status.PENDING));
        assignments.add(new Activity("STAT203", Activity.Activity_Type.ASSIGNMENT, "Prac 2", new LocalDate(2017,9,12), new LocalTime(14,5), Activity.Assignment_Status.PENDING));
        assignments.add(new Activity("WRR301", Activity.Activity_Type.ASSIGNMENT, "Presentation", new LocalDate(2017,9,20), new LocalTime(10,25), Activity.Assignment_Status.PENDING));
        assignments.add(new Activity("WRR301", Activity.Activity_Type.ASSIGNMENT, "Final", new LocalDate(2017,10,23), new LocalTime(12,0), Activity.Assignment_Status.PENDING));


        tests.add(new Activity("STAT203", Activity.Activity_Type.TEST, "Tut Test 2",new LocalDate(2017,9,12),new LocalTime(18,0), "07 02 48"));
        tests.add(new Activity("MATH214", Activity.Activity_Type.TEST, "Seme Test 2",new LocalDate(2017,9,14),new LocalTime(18,0), "Heinz Benz Hall"));
        tests.add(new Activity("WRL301", Activity.Activity_Type.TEST, "Seme Test 2",new LocalDate(2017,10,4),new LocalTime(18,0), "35 00 17"));
        tests.add(new Activity("WRAP302", Activity.Activity_Type.TEST, "Seme Test 2",new LocalDate(2017,10,6),new LocalTime(14,0), "09 02 04"));
        tests.add(new Activity("STAT203", Activity.Activity_Type.TEST, "Seme Test 2",new LocalDate(2017,10,10),new LocalTime(18,0), "07 02 48"));
        tests.add(new Activity("MATH203", Activity.Activity_Type.TEST, "Seme Test 2",new LocalDate(2017,10,12),new LocalTime(18,0), "Heinz Benz Hall"));
        tests.add(new Activity("MATH214", Activity.Activity_Type.TEST, "Seme Test 3",new LocalDate(2017,10,19),new LocalTime(18,0), "Heinz Benz Hall"));

        assignmentsAdapter = new AssignmentAdapter(getActivity(), assignments);
        testsAdapter = new TestAdapter(getActivity(), tests);

        lvAssignments.setAdapter(assignmentsAdapter);
        lvTests.setAdapter(testsAdapter);

        Utility.setListViewHeightBasedOnChildren(lvAssignments, 350);
        Utility.setListViewHeightBasedOnChildren(lvTests, 450);

        return view;
    }
}
