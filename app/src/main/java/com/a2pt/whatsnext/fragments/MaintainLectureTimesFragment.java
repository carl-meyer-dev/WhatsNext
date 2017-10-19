package com.a2pt.whatsnext.fragments;

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

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.MainActivity;
import com.a2pt.whatsnext.adapters.MaintainLecturesActivityAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.ITSdbManager;
import com.a2pt.whatsnext.services.dbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class MaintainLectureTimesFragment extends Fragment {

    View view;
    FloatingActionButton fabSaveLecture;
    ListView lvLectureTimes;
    Spinner spnModuleLectures;
    ITSdbManager itSdbManager;
    dbManager localDB;
    MainActivity main;
    User user;
    List<Activity> lectures;
    String selectedModule;

    //Alter to new Adapter
    MaintainLecturesActivityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintain_lecture_times_layout, container, false);

        localDB = new dbManager(getActivity()); //get reference to local database
        itSdbManager = new ITSdbManager(getActivity());

        fabSaveLecture = (FloatingActionButton)view.findViewById(R.id.fabSaveLectures);
        lvLectureTimes = (ListView)view.findViewById(R.id.lv_LectureTimes);
        spnModuleLectures = (Spinner)view.findViewById(R.id.spnModulesLecture);

        main = (MainActivity) getActivity(); //get reference to mainActivity so I can call getCurUser()
        user = main.getCurUser(); //get the currently logged in user (or current lecturer)
        final List<String> modules = new ArrayList<>(); //make List to be used for spinner
        for (String string: user.getModules()){ //go through Array of modules the Current Lectuer is assigned to
            modules.add(string); //add each module to the List
        }
        System.out.println("MODULES LIST IS = " + modules.toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modules); //set List to Adapter
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //make the drop down list look nice
        spnModuleLectures.setAdapter(dataAdapter); //set the spinner's adapter

        lectures = new ArrayList<>();
        spnModuleLectures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  //when an item on the drop down list is chosen
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedModule = spnModuleLectures.getItemAtPosition(position).toString(); //get the selected module
                lectures = itSdbManager.getDuplicateLectures(selectedModule);   //query db for all assignments from that module
                adapter = new MaintainLecturesActivityAdapter(getActivity(), lectures);    //set the adapter data to the queried data
                lvLectureTimes.setAdapter(adapter);  //set List View adapter to display Assignments
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedModule = spnModuleLectures.getItemAtPosition(0).toString();
            }
        });


        fabSaveLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Activity>  lecturesToRemove = new ArrayList<Activity>();


                for (int i = 0; i < adapter.getIsCheckedLength(); i++)
                {
                    if(!adapter.getSelected(i))
                    {

                        //Instead of adding it to an array, just Delete it directly

                        Activity lecture = lectures.get(i);
                        System.out.println("DEBUG THE LECTURES TO REMOVE = " + lecture.getModID() + ", " + lecture.getActType() + ", " + lecture.getLecStartTime() + ", " + lecture.getDayOfWeek());
                        int lecActID = localDB.getLectureActID(lecture);
                        System.out.println("DEBUG LECTURE ID IS = " + lecActID);
                        localDB.deleteActivity(lecActID);
                    }
                }

                getFragmentManager().popBackStack();



            }
        });

        return view;
    }
}
