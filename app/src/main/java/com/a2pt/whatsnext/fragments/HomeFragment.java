package com.a2pt.whatsnext.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.adapters.ActivityAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.Utility;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class HomeFragment extends Fragment {

    private Activity curActivity;
    private ActivityAdapter lecturesAdapter, assignmentsAdapter, testsAdapter;
    ListView lvLectures, lvAssignments, lvTests;



    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);

        lvAssignments = (ListView)view.findViewById(R.id.home_lvAssignments);
        lvTests = (ListView)view.findViewById(R.id.home_lvTests);
        lvLectures = (ListView)view.findViewById(R.id.home_lvLectures);

        final List<Activity> Lectures = new ArrayList<>();
        final List<Activity> Assignments = new ArrayList<>();
        final List<Activity> Tests = new ArrayList<>();

        //Add Dummy Lectures
        //TODO: this will change when we integrate the Database
        Lectures.add(new Activity("MATH214", Activity.Activity_Type.LECTURE, "35 00 17", new LocalTime(7,45)));
        Lectures.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        Lectures.add(new Activity("WRL301", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(10,25)));
        Lectures.add(new Activity("MATH203", Activity.Activity_Type.LECTURE, "5 00 07", new LocalTime(14,5)));
        Lectures.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "07 02 50", new LocalTime(16,45)));

        lecturesAdapter = new ActivityAdapter(getActivity(), Lectures);
        lvLectures.setAdapter(lecturesAdapter);
        Utility.setListViewHeightBasedOnChildren(lvLectures, 319); //The second parameter is the MAGIC NUMBER which corrects the height
        //Add Dummy Assignments
        Assignments.add(new Activity("WRAP302", Activity.Activity_Type.ASSIGNMENT, "Prac 01", new LocalDate(2017,8,22), new LocalTime(23,35), Activity.Assignment_Status.PENDING));
        Assignments.add(new Activity("WRUI301", Activity.Activity_Type.ASSIGNMENT, "UI Analysis", new LocalDate(2017,8,22), new LocalTime(12,0), Activity.Assignment_Status.PENDING));
       // Assignments.add(new Activity("WRR301", Activity.Activity_Type.ASSIGNMENT, "Presentation", new LocalDate(2017,8,22), new LocalTime(14,5), Activity.Assignment_Status.PENDING));

        assignmentsAdapter = new ActivityAdapter(getActivity(), Assignments);
        lvAssignments.setAdapter(assignmentsAdapter);
        Utility.setListViewHeightBasedOnChildren(lvAssignments, 380);

        Tests.add(new Activity("MATH214", Activity.Activity_Type.TEST, "Semester Test 1",new LocalDate(2017,8,17),new LocalTime(18,0), "Heinz Benz Hall"));

        testsAdapter = new ActivityAdapter(getActivity(), Tests);
        lvTests.setAdapter(testsAdapter);
        Utility.setListViewHeightBasedOnChildren(lvTests, 300);

        return view;
    }
}
