package com.a2pt.whatsnext.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.MainActivity;
import com.a2pt.whatsnext.adapters.AssignmentAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.Utility;
import com.a2pt.whatsnext.services.dbManager;

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
    List<Activity> assignments;
    AssignmentAdapter adapter;
    dbManager localDB;
    Spinner spnAssignments;
    MainActivity main;
    User user;
    String selectedModule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintain_assignment_layout, container, false);
        localDB = new dbManager(getActivity()); //get reference to local databse
        lvAssignments = (ListView)view.findViewById(R.id.ma_lvAssignments);

        //Set Spinner Content and grab selected item on SpinnerClick
        spnAssignments = (Spinner)view.findViewById(R.id.spnModulesAssignment);
        main = (MainActivity) getActivity(); //get reference to mainActivity so I can call getCurUser()
        user = main.getCurUser(); //get the currently logged in user (or current lecturer)
        final List<String> modules = new ArrayList<>(); //make List to be used for spinner
        for (String string: user.getModules()){ //go through Array of modules the Current Lectuer is assigned to
            modules.add(string); //add each module to the List
        }
        System.out.println("MODULES LIST IS = " + modules.toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modules);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAssignments.setAdapter(dataAdapter);
        assignments = new ArrayList<>();
        spnAssignments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedModule = spnAssignments.getItemAtPosition(position).toString();
                System.out.println("SELECTED MODULE IS = " + selectedModule);
                assignments = localDB.getAssignmentsByID(selectedModule);
                adapter = new AssignmentAdapter(getActivity(), assignments);
                lvAssignments.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //This code is for Testing purposes, it should be replaced with a proper adapter that obtains items from Database
        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        //TODO: Change adapter correct adapter obtaining info from Database

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
