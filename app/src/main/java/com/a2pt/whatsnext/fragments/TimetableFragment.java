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
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.Utility;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class TimetableFragment extends Fragment {
    View view;

    private ListView lvMonday, lvTuesday, lvWednesday, lvThursday, lvFriday;
    private ActivityAdapter monAdapter, tueAdapter, wedAdapter, thuAdapter, friAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timetable_layout, container, false);

        lvMonday = (ListView)view.findViewById(R.id.tt_lvMonday);
        lvTuesday = (ListView)view.findViewById(R.id.tt_lvTuesday);
        lvWednesday = (ListView)view.findViewById(R.id.tt_lvWednesday);
        lvThursday = (ListView)view.findViewById(R.id.tt_lvThursday);
        lvFriday = (ListView)view.findViewById(R.id.tt_lvFriday);

        //make a list for each list view corresponding to each day of the week
        final List<Activity> monday = new ArrayList<>();
        final List<Activity> tuesday = new ArrayList<>();
        final List<Activity> wednesday = new ArrayList<>();
        final List<Activity> thursday = new ArrayList<>();
        final List<Activity> friday = new ArrayList<>();

        ArrayList<List<Activity>> weekSchedule = new ArrayList<>();
            //Don't know if this will be useful yet, but I think we could use this for indexing or if we will have to do if statements
            weekSchedule.add(monday);
            weekSchedule.add(tuesday);
            weekSchedule.add( wednesday);
            weekSchedule.add(thursday);
            weekSchedule.add(friday);


        //Add Dummy Lectures
        //TODO: this will change when we integrate the Database
        monday.add(new Activity("MATH203", Activity.Activity_Type.LECTURE, "35 00 17", new LocalTime(7,45)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        /*
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        monday.add(new Activity("WRAP302", Activity.Activity_Type.LECTURE, "35 01 01", new LocalTime(9,5)));
        */
        tuesday.add(new Activity("MATH2114", Activity.Activity_Type.LECTURE, "35 00 17", new LocalTime(7,45)));
        tuesday.add(new Activity("MATH2114 Tut", Activity.Activity_Type.LECTURE, "04 00 01", new LocalTime(9,5)));
        tuesday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "04 00 3", new LocalTime(14,5)));
        tuesday.add(new Activity("STAT203 Prac", Activity.Activity_Type.LECTURE, "07 02 48", new LocalTime(15,30)));
        tuesday.add(new Activity("STAT203 Tut", Activity.Activity_Type.LECTURE, "07 02 48", new LocalTime(16,45)));
        /*
        tuesday.add(new Activity("STAT203 Tut", Activity.Activity_Type.LECTURE, "07 02 48", new LocalTime(16,45)));
        tuesday.add(new Activity("STAT203 Tut", Activity.Activity_Type.LECTURE, "07 02 48", new LocalTime(16,45)));
        tuesday.add(new Activity("STAT203 Tut", Activity.Activity_Type.LECTURE, "07 02 48", new LocalTime(16,45)));
*/

        wednesday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 17", new LocalTime(7,45)));
        wednesday.add(new Activity("MATH203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(9,5)));
        wednesday.add(new Activity("WRR301", Activity.Activity_Type.LECTURE, "09 02 02", new LocalTime(10,25)));
        wednesday.add(new Activity("MATH214", Activity.Activity_Type.LECTURE, "07 02 50", new LocalTime(14,5)));
        wednesday.add(new Activity("WRAP302 Prac", Activity.Activity_Type.LECTURE, "09 02 04", new LocalTime(15,35)));
        /*
        wednesday.add(new Activity("WRAP302 Prac", Activity.Activity_Type.LECTURE, "09 02 04", new LocalTime(15,35)));
        wednesday.add(new Activity("WRAP302 Prac", Activity.Activity_Type.LECTURE, "09 02 04", new LocalTime(15,35)));
        wednesday.add(new Activity("WRAP302 Prac", Activity.Activity_Type.LECTURE, "09 02 04", new LocalTime(15,35)));
*/
        thursday.add(new Activity("MATH214", Activity.Activity_Type.LECTURE, "07 02 50", new LocalTime(7,45)));
        thursday.add(new Activity("WRL301", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(10,25)));
        thursday.add(new Activity("WRL301 Tut", Activity.Activity_Type.LECTURE, "35 00 16", new LocalTime(14,5)));
        thursday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(16,45)));
        //
        /*
        thursday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(16,45)));
        thursday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(16,45)));
        thursday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(16,45)));
        thursday.add(new Activity("STAT203", Activity.Activity_Type.LECTURE, "35 00 18", new LocalTime(16,45)));

*/

        //no classes on monday so I will add an empty activity just so that the array isn't null
        //TODO: cater for when there are no classes on a day

        friday.add(new Activity("WRMS302", Activity.Activity_Type.LECTURE, "09 02 02", new LocalTime(9,5)));
/*
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
        friday.add(new Activity("", Activity.Activity_Type.LECTURE, "", new LocalTime(0,0)));
*/
        //set adapters
        monAdapter = new ActivityAdapter(getActivity(), monday);
        tueAdapter = new ActivityAdapter(getActivity(), tuesday);
        wedAdapter = new ActivityAdapter(getActivity(), wednesday);
        thuAdapter = new ActivityAdapter(getActivity(), thursday);
        friAdapter = new ActivityAdapter(getActivity(), friday);



        lvMonday.setAdapter(monAdapter);
        lvTuesday.setAdapter(tueAdapter);
        lvWednesday.setAdapter(wedAdapter);
        lvThursday.setAdapter(thuAdapter);
        lvFriday.setAdapter(friAdapter);

        Utility.setListViewHeightBasedOnChildren(lvMonday, 320);
        Utility.setListViewHeightBasedOnChildren(lvTuesday, 315);
        Utility.setListViewHeightBasedOnChildren(lvWednesday, 320);
        Utility.setListViewHeightBasedOnChildren(lvThursday, 320);
        Utility.setListViewHeightBasedOnChildren(lvFriday, 10);

        return view;
    }
}
