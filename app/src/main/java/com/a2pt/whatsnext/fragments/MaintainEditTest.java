package com.a2pt.whatsnext.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.services.ITSdbManager;
import com.a2pt.whatsnext.services.dbManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Calendar;


public class MaintainEditTest extends Fragment {

    //Custom Variables
    private FloatingActionButton fabDate;
    private FloatingActionButton fabTime;
    private View view;
    private TextView tvDate;
    private TextView tvTime;
    private DatePickerDialog.OnDateSetListener dataDateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Bundle bundle;
    private Activity test;
    //instatiate values from the MaintainTestFragment
    private String modId;
    private String actType;
    private EditText venue;

    //instantiate the databases
    dbManager localDB;
    ITSdbManager itSdbManager;

    public MaintainEditTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.maintain_edit_test, container, false);

        //Instantiate bundle and get values;
        bundle = this.getArguments();
        modId = bundle.getString("modId");
        actType = bundle.getString("actType");
        test = (Activity) bundle.getSerializable("test");

        Button btnSaveTest = (Button) view.findViewById(R.id.btnET_Save);
        Button btnDeleteTest = (Button) view.findViewById(R.id.btnET_Delete);
        //Instatiate databases
        localDB = new dbManager(getActivity());
        itSdbManager = new ITSdbManager(getActivity());
        tvDate = (TextView) view.findViewById(R.id.tvET_Date);
        tvTime = (TextView) view.findViewById(R.id.tvET_Time);
        venue = (EditText) view.findViewById(R.id.txtET_Title);

        tvDate.setText(test.getTestDateString());
        tvTime.setText(test.getTestTime().toString().substring(0,5));
        venue.setText(test.getTestVenue());



        fabDate = (FloatingActionButton) view.findViewById(R.id.fabEditTestDate);

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


        fabTime = (FloatingActionButton) view.findViewById(R.id.fabEditTestTime);

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

        dataDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;

                tvDate.setText(date);

            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time;
                if (minute == 0) {
                    time = hourOfDay + ":" + minute + "0";
                } else {
                    time = hourOfDay + ":" + minute;
                }

                tvTime.setText(time);
            }
        };

        btnSaveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String testVenue = venue.getText().toString();
                String dueDate = tvDate.getText().toString();
                String dueTime = tvTime.getText().toString();

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
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
                System.out.println("NEW TEST TIME OF TEST IS = " + newActivity.getTestTime());
                //TODO: instead of insert, Update current Assignment

                localDB.updateTest(newActivity, test.getActID());
                System.out.println("DEBUG EDIT TEST: ITS TEST ID IS = " + itSdbManager.getTestID(test));
                itSdbManager.updateTest(newActivity, itSdbManager.getTestID(test));

                //returns to previous assignment
                getFragmentManager().popBackStack();
            }
        });

        btnDeleteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localDB.deleteActivity(test.getActID());
                itSdbManager.deleteActivity(itSdbManager.getTestID(test));

                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
