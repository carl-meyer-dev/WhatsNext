package com.a2pt.whatsnext.fragments;

import android.content.pm.ActivityInfo;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        /*assignmentsAdapter = new AssignmentAdapter(getActivity(), assignments);
        testsAdapter = new TestAdapter(getActivity(), tests);

        lvAssignments.setAdapter(assignmentsAdapter);
        lvTests.setAdapter(testsAdapter);*/

        //---------------------------------------------------------------------------------------
        //Editted On this page Gerrit
        final List<Activity> updateAssignments = checkAssignmentDateandTime(assignments);
        final List<Activity> updateTest = checkTestDateandTime(tests);

        assignmentsAdapter = new AssignmentAdapter(getActivity(), updateAssignments);
        testsAdapter = new TestAdapter(getActivity(), updateTest);

        lvAssignments.setAdapter(assignmentsAdapter);
        lvTests.setAdapter(testsAdapter);
        //---------------------------------------------------------------------------------------

        Utility.setListViewHeightBasedOnChildren(lvAssignments, 240);
        Utility.setListViewHeightBasedOnChildren(lvTests, 240);

        //Sets orientation to portrait (User is unable to change orientation)
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        return view;
    }

    private List<Activity> checkAssignmentDateandTime(List<Activity> list) {
        List<Activity> newList = new ArrayList<>();

        //This is to see if we can remove past events (Date has exceeded)
        Calendar c = Calendar.getInstance(); // Create a new calendar instance
        String date = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE)+"";
        String time = c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + "";
        System.out.println("Debug: Date:" + date);
        System.out.println("Debug: Time:" + time);

        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dfDate.format(date);

        DateFormat dfTime = new SimpleDateFormat("hh:mm");
        String strTime = dfTime.format(time);
        System.out.println("Debug formatted Date: " + strDate);
        System.out.println("Debug formatted Time: " + strTime);

        for (int i = 0; i< list.size(); i++)
        {
            String tempTime = list.get(i).getAssignmentDueTime().toString();
            String tempDate = list.get(i).getAssignmentDueDateString();

            if (strDate.equals(tempDate) && strTime.equals(tempTime))
            {
                newList.add(list.get(i));
            }

        }

        return newList;
    }

    private List<Activity> checkTestDateandTime(List<Activity> list) {
        List<Activity> newList = new ArrayList<>();

        //This is to see if we can remove past events (Date has exceeded)
        Calendar c = Calendar.getInstance(); // Create a new calendar instance
        String date = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE)+"";
        String time = c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + "";
        System.out.println("Debug: Date:" + date);
        System.out.println("Debug: Time:" + time);

        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dfDate.format(date);

        DateFormat dfTime = new SimpleDateFormat("hh:mm");
        String strTime = dfTime.format(time);
        System.out.println("Debug formatted Date: " + strDate);
        System.out.println("Debug formatted Time: " + strTime);

        for (int i = 0; i< list.size(); i++)
        {
            String tempTime = list.get(i).getAssignmentDueTime().toString();
            String tempDate = list.get(i).getAssignmentDueDateString();

            if (strDate.equals(tempDate) && strTime.equals(tempTime))
            {
                newList.add(list.get(i));
            }

        }

        return newList;
    }
}
