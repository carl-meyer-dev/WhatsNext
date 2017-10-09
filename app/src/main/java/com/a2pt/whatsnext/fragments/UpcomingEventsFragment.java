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
import com.a2pt.whatsnext.services.dbManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class UpcomingEventsFragment extends Fragment {

    View view;
    dbManager localDB;
    private AssignmentAdapter assignmentsAdapter;
    private TestAdapter testsAdapter;
    private ListView lvAssignments, lvTests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_events_layout, container, false);

        localDB = new dbManager(getActivity());

        lvTests = (ListView)view.findViewById(R.id.ue_lvTests);
        lvAssignments = (ListView)view.findViewById(R.id.ue_lvAssignments);

        final List<Activity> assignments = localDB.getUpcomingAssignments();
        final List<Activity> tests = localDB.getUpcomingTests();

        assignmentsAdapter = new AssignmentAdapter(getActivity(), assignments);
        testsAdapter = new TestAdapter(getActivity(), tests);

        lvAssignments.setAdapter(assignmentsAdapter);
        lvTests.setAdapter(testsAdapter);

        Utility.setListViewHeightBasedOnChildren(lvAssignments, 240);
        Utility.setListViewHeightBasedOnChildren(lvTests, 240);

        return view;
    }
}
