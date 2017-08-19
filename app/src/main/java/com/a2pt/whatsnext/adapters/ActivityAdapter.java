package com.a2pt.whatsnext.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
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

public class ActivityAdapter extends ArrayAdapter<Activity>{


    //generated constructor method
    public ActivityAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_layout ,activities);

    }

    //getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_layout, parent, false);
        }

        view.setTag(getItem(position));

        TextView tvValue1 = (TextView)view.findViewById(R.id.tvActValue1);
        TextView tvValue2 = (TextView)view.findViewById(R.id.tvActValue2);
        TextView tvValue3 = (TextView)view.findViewById(R.id.tvActValue3);
        LinearLayout lLayout = (LinearLayout)view.findViewById(R.id.linlayActivity);


        Activity activity = getItem(position);
        if(activity.getActType() == Activity.Activity_Type.ASSIGNMENT){

            //Change colour of background
            lLayout.setBackgroundColor(Color.parseColor("#8BC34A"));
            //set text view values
            tvValue1.setText(activity.getAssignmentDueDate().toString());
            tvValue2.setText(activity.getModID());
            tvValue3.setText(activity.getAssignmentTitle());

        }else if(activity.getActType() == Activity.Activity_Type.TEST){
            //Change colour of background
            lLayout.setBackgroundColor(Color.parseColor("#f44336"));
            //set text view values
            tvValue1.setText(activity.getTestDate().toString());
            tvValue2.setText(activity.getModID());
            tvValue3.setText(activity.getTestVenue());

        }else {
            //Change colour of background
            lLayout.setBackgroundColor(Color.parseColor("#1E88E5"));
            //set text view values
            tvValue1.setText(activity.getLecStartTime().toString().substring(0,5));
            tvValue2.setText(activity.getModID());
            tvValue3.setText(activity.getLectureVenue());

        }

        return view;
    }
}
