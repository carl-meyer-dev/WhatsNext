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
import com.a2pt.whatsnext.adapters.TestAdapter;
import com.a2pt.whatsnext.models.Activity;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class MaintainTestFragment extends Fragment {


    View view;
    FloatingActionButton fabNew;
    NewTestFragment newTestFragment = new NewTestFragment();
    ListView lvTests;
    TestAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintain_test_layout, container, false);

        lvTests = (ListView)view.findViewById(R.id.mt_lvTests);

        final List<Activity> tests = new ArrayList<>();

        tests.add(new Activity("WRAP302", "test", "Semester Test 2",new LocalDate(2017,10,6),new LocalTime(14,0), "09 02 04"));

        adapter = new TestAdapter(getActivity(), tests);

        lvTests.setAdapter(adapter);

        //This code is for Testing purposes, it should be replaced with a proper adapter that obtains items from Database
        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        //TODO: Change adapter correct adapter obtaining info from Database
        Spinner spinner = (Spinner)view.findViewById(R.id.spnModulesTest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.modules, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fabNew = (FloatingActionButton)view.findViewById(R.id.fabNewTest);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, newTestFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
