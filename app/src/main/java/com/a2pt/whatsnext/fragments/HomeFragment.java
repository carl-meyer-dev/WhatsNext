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

        lvAssignments = (ListView)view.findViewById(R.id.lvAssignments);
        lvTests = (ListView)view.findViewById(R.id.lvTests);
        lvLectures = (ListView)view.findViewById(R.id.lvLectures);

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
        Utility.setListViewHeightBasedOnChildren(lvLectures);
        //Add Dummy Assignments
        Lectures.add(new Activity("WRAP302", Activity.Activity_Type.ASSIGNMENT, "Prac 01", new LocalTime(9,5)));
        Lectures.add(new Activity("WRL301", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(10,25)));
        Lectures.add(new Activity("MATH203", Activity.Activity_Type.LECTURE, "5 00 07", new LocalTime(14,5)));
        Lectures.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "07 02 50", new LocalTime(16,45)));

        lecturesAdapter = new ActivityAdapter(getActivity(), Lectures);
        lvLectures.setAdapter(lecturesAdapter);
        Utility.setListViewHeightBasedOnChildren(lvLectures);

        return view;
    }
}
