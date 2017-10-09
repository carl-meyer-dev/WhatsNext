package com.a2pt.whatsnext.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.adapters.ActivityAdapter;
import com.a2pt.whatsnext.adapters.AssignmentAdapter;
import com.a2pt.whatsnext.adapters.TestAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.Utility;
import com.a2pt.whatsnext.services.dbManager;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Carl on 2017-07-29.
 */

public class HomeFragment extends Fragment {

    private Activity curActivity;
    private ActivityAdapter lecturesAdapter;
    private AssignmentAdapter assignmentsAdapter;
    private TestAdapter testsAdapter;
    private ListView lvLectures, lvAssignments, lvTests;

    View view;
    TextView tvDate;
    dbManager localDB;
    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        calendar = Calendar.getInstance();
        localDB = new dbManager(getActivity());

        tvDate = (TextView)view.findViewById(R.id.home_tvDate);

        lvAssignments = (ListView)view.findViewById(R.id.home_lvAssignments);
        lvTests = (ListView)view.findViewById(R.id.home_lvTests);
        lvLectures = (ListView)view.findViewById(R.id.home_lvLectures);

        setDateHeader();
        //Get today's Day Of Week
        String dayOfWeek =  calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toLowerCase();
        String date = DateTime.now().toString("yyyy-MM-dd");
        System.out.println("DEBUG homeFragment: TODAY'S DATE IS = " + date);
        final List<Activity> Lectures = localDB.getLecturesSpecificDay(dayOfWeek); //set List to List of Lecture for Today
        final List<Activity> Assignments = localDB.getTodaysAssignments(date);
        final List<Activity> Tests = localDB.getTodayTests(date);


        lecturesAdapter = new ActivityAdapter(getActivity(), Lectures);
        lvLectures.setAdapter(lecturesAdapter);
        Utility.setListViewHeightBasedOnChildren(lvLectures, 310); //The second parameter is the MAGIC NUMBER which corrects the height


        assignmentsAdapter = new AssignmentAdapter(getActivity(), Assignments);
        lvAssignments.setAdapter(assignmentsAdapter);
        Utility.setListViewHeightBasedOnChildren(lvAssignments, 230);

        testsAdapter = new TestAdapter(getActivity(), Tests);
        lvTests.setAdapter(testsAdapter);
        Utility.setListViewHeightBasedOnChildren(lvTests, 180);



        return view;
    }

    private void setDateHeader(){


        String curDate = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + calendar.get(Calendar.YEAR);

        tvDate.setText(curDate);

    }




}
