package com.a2pt.whatsnext.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    MaintainEditAssignment maintainEditAssignmentFragment = new MaintainEditAssignment();
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modules); //set List to Adapter
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //make the drop down list look nice
        spnAssignments.setAdapter(dataAdapter); //set the spinner's adapter


        assignments = new ArrayList<>();
        spnAssignments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  //when an item on the drop down list is chosen
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedModule = spnAssignments.getItemAtPosition(position).toString(); //get the selected module
                assignments = localDB.getAssignmentsByID(selectedModule);   //query db for all assignments from that module
                adapter = new AssignmentAdapter(getActivity(), assignments);    //set the adapter data to the queried data
                lvAssignments.setAdapter(adapter);  //set List View adapter to display Assignments
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedModule = spnAssignments.getItemAtPosition(0).toString();
            }
        });


        fabNew = (FloatingActionButton)view.findViewById(R.id.fabNewAssignment);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add a bundle that can be used to push through relevant information to allow the activity to be created
                Bundle bundleToSend = new Bundle();
                bundleToSend.putString("modId", selectedModule);
                bundleToSend.putString("actType", "assignment");
                newAssignmentFragment.setArguments(bundleToSend);

                //Create new fragment and send through bundle
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, newAssignmentFragment).addToBackStack(null).commit();
            }
        });





        lvAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get Assignment of Selected item
                System.out.println("ITEM IN LIST CLICKED");
                Activity assignment = assignments.get(position);
                System.out.println("DEBUG ON MAINTAIN ASSIGNMENTS:");
                System.out.println("SELECTED ASSIGNMENT IS = " + assignment.getAssignmentTitle() + " with actID = " + assignment.getActID());

                //add a bundle that can be used to push through relevant information to allow the activity to be created
                Bundle bundleToSend = new Bundle();
                bundleToSend.putString("modId", selectedModule);
                bundleToSend.putString("actType", "assignment");
                bundleToSend.putSerializable("assignment", assignment);
                maintainEditAssignmentFragment.setArguments(bundleToSend);

                //Create new fragment and send through bundle
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainEditAssignmentFragment).addToBackStack(null).commit();
            }
        });



        //Sets orientation to portrait (User is unable to change orientation)
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        return view;
    }







}
