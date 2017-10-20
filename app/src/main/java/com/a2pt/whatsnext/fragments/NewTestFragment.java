package com.a2pt.whatsnext.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.ITSdbManager;
import com.a2pt.whatsnext.services.dbManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewTestFragment extends Fragment {

    //Custom Variables
    private FloatingActionButton fabDate;
    private FloatingActionButton fabTime;
    private View view;
    private TextView tvDate;
    private TextView tvTime;
    private DatePickerDialog.OnDateSetListener dataDateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button btnCreateTest;
    private Bundle bundle;

    //instatiate values from the MaintainTestFragment
    private String modId;
    private String actType;
    private EditText title;

    //instantiate the databases
    dbManager localDB;
    ITSdbManager itSdbManager;

    public NewTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.maintain_test_new_test, container, false);

        //Instantiate bundle and get values;
        bundle = this.getArguments();
        modId = bundle.getString("modId");
        actType = bundle.getString("actType");
        btnCreateTest = (Button)view.findViewById(R.id.btnCreateTest);
        //Instatiate databases
        localDB = new dbManager(getActivity());
        itSdbManager = new ITSdbManager(getActivity());
        tvDate = (TextView)view.findViewById(R.id.tvMT_Date);
        tvTime = (TextView)view.findViewById(R.id.tvMT_Time);

        title = (EditText)view.findViewById(R.id.txtMT_Title);

        fabDate = (FloatingActionButton) view.findViewById(R.id.fabTestDate);

        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dataDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        fabTime = (FloatingActionButton)view.findViewById(R.id.fabTestTime);

        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, hour, minute, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dataDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;

                tvDate.setText(date);

            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time;
                if(minute == 0){
                    time = hourOfDay + ":" + minute + "0";
                }else {
                    time = hourOfDay + ":" + minute;
                }

                tvTime.setText(time);
            }
        };

        btnCreateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testVenue = title.getText().toString();
                String dueDate = tvDate.getText().toString();
                String dueTime = tvTime.getText().toString();

                if(testVenue.equalsIgnoreCase("") || dueDate.equalsIgnoreCase("") || dueTime.equalsIgnoreCase("")){

                    //All or some of the fields are empty
                    Toast toast = Toast.makeText(getActivity(), "Invalid Entry", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    String[] temdate = dueDate.split("/"); //split date up into 2017, 10, 23
                    int year = Integer.parseInt(temdate[2]);  //save year as 2017
                    int month = Integer.parseInt(temdate[1]);  //save month as 10
                    int day = Integer.parseInt(temdate[0]); //save day as 23
                    LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                    System.out.println(dDate.toString());

                    String[] temptime = dueTime.split(":");  //Split the Time string into 17 abd 45
                    int hours = Integer.parseInt(temptime[0]); //set the hours integer
                    int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                    LocalTime dTime = new LocalTime(hours, minutes);
                    System.out.println(dTime.toString());

                    Activity newActivity = new Activity(modId, actType, "", dDate, dTime, testVenue);
                    localDB.insertTest(newActivity);
                    itSdbManager.insertTest(newActivity);

                    //returns to previous assignment
                    getFragmentManager().popBackStack();
                }



            }
        });

        //Sets orientation to portrait (User is unable to change orientation)
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        return view;
    }

}
