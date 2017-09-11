package com.a2pt.whatsnext.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;

import java.util.List;

/**
 * Created by Carl on 2017-08-19.
 */

public class TestAdapter extends ArrayAdapter<Activity>{


    //generated constructor method
    public TestAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_layout_test,activities);

    }

    //getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_layout_test, parent, false);
        }

        view.setTag(getItem(position));

        TextView tvDate = (TextView)view.findViewById(R.id.alt_tvDate);
        TextView tvTime = (TextView)view.findViewById(R.id.alt_tvTime);
        TextView tvVenue = (TextView)view.findViewById(R.id.alt_tvVenue);
        TextView tvModule = (TextView)view.findViewById(R.id.alt_tvModule);
        LinearLayout lLayout = (LinearLayout)view.findViewById(R.id.alt_linLay);


        Activity activity = getItem(position);

            //Change colour of background
            lLayout.setBackgroundColor(Color.parseColor("#f44336"));
            //set text view values
            tvDate.setText(activity.getTestDate().toString().substring(5));
            tvTime.setText(activity.getTestTime().toString().substring(0,5));

            tvVenue.setText(activity.getTestVenue());
            tvModule.setText(activity.getModID());



        return view;
    }
}
