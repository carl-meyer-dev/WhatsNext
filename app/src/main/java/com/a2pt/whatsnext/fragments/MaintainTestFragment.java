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

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.MainActivity;
import com.a2pt.whatsnext.adapters.TestAdapter;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.dbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class MaintainTestFragment extends Fragment {


    View view;
    FloatingActionButton fabNew;
    NewTestFragment newTestFragment = new NewTestFragment();
    MaintainEditTest maintainEditTest = new MaintainEditTest();
    ListView lvTests;
    TestAdapter adapter;
    User user;
    List<Activity> tests;
    String selectedModule;
    dbManager localDB;
    MainActivity main;
    Spinner spnTests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintain_test_layout, container, false);
        localDB = new dbManager(getActivity());
        lvTests = (ListView)view.findViewById(R.id.mt_lvTests);
        spnTests = (Spinner)view.findViewById(R.id.spnModulesTest);
        main = (MainActivity) getActivity();
        user = main.getCurUser();
        final List<String> modules = new ArrayList<>();
        for (String string : user.getModules()){
            modules.add(string);
        }
        System.out.println("MODULES LIST IS = " + modules.toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modules);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTests.setAdapter(dataAdapter);

        tests = new ArrayList<>();

        spnTests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedModule = spnTests.getItemAtPosition(position).toString();
                tests = localDB.getTestsByID(selectedModule);
                adapter = new TestAdapter(getActivity(), tests);
                lvTests.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabNew = (FloatingActionButton)view.findViewById(R.id.fabNewTest);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add a bundle that can be used to push through relevant information to allow the activity to be created
                Bundle bundleToSend = new Bundle();
                bundleToSend.putString("modId", selectedModule);
                bundleToSend.putString("actType", "test");
                newTestFragment.setArguments(bundleToSend);

                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, newTestFragment).addToBackStack(null).commit();
            }
        });

        lvTests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get Assignment of Selected item
                System.out.println("ITEM IN LIST CLICKED");
                Activity test = tests.get(position);
                System.out.println("DEBUG ON MAINTAIN TEST:");
                System.out.println("SELECTED TEST IS = " + test.getTestDate() + " with actID = " + test.getActID());

                //add a bundle that can be used to push through relevant information to allow the activity to be created
                Bundle bundleToSend = new Bundle();
                bundleToSend.putString("modId", selectedModule);
                bundleToSend.putString("actType", "test");
                bundleToSend.putSerializable("test", test);
                maintainEditTest.setArguments(bundleToSend);

                //Create new fragment and send through bundle
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainEditTest).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
