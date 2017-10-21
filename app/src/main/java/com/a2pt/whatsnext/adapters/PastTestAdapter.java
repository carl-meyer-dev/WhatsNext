package com.a2pt.whatsnext.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;

import java.util.List;

public class PastTestAdapter extends ArrayAdapter<Activity> {


    //generated constructor method
    public PastTestAdapter(@NonNull Context context, List<Activity> activities) {
        super(context, R.layout.activity_past_test_adapter, activities);

    }

    //getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_past_test_adapter, parent, false);
        }

        view.setTag(getItem(position));

        TextView tvDate = (TextView) view.findViewById(R.id.alt_tvDatePast);
        TextView tvTime = (TextView) view.findViewById(R.id.alt_tvTimePast);
        TextView tvVenue = (TextView) view.findViewById(R.id.alt_tvVenuePast);
        TextView tvModule = (TextView) view.findViewById(R.id.alt_tvModulePast);
        LinearLayout lLayout = (LinearLayout) view.findViewById(R.id.alt_linLayPast);


        Activity activity = getItem(position);

        //Change colour of background
        lLayout.setBackgroundColor(Color.parseColor("#f44336"));
        //set text view values
        tvDate.setText(activity.getTestDate().toString().substring(5));
        tvTime.setText(activity.getTestTime().toString().substring(0, 5));

        tvVenue.setText(activity.getTestVenue());
        tvModule.setText(activity.getModID());


        return view;

    }
}
