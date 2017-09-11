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

public class AssignmentAdapter extends ArrayAdapter<Activity>{


    //generated constructor method
    public AssignmentAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_layout_assignment,activities);

    }

    //getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_layout_assignment, parent, false);
        }

        view.setTag(getItem(position));

        TextView tvDate = (TextView)view.findViewById(R.id.als_tvDate);
        TextView tvTime = (TextView)view.findViewById(R.id.als_tvTime);
        TextView tvTitle = (TextView)view.findViewById(R.id.als_tvTitle);
        TextView tvModule = (TextView)view.findViewById(R.id.als_tvModule);
        LinearLayout lLayout = (LinearLayout)view.findViewById(R.id.als_linLay);


        Activity activity = getItem(position);

            //Change colour of background
            lLayout.setBackgroundColor(Color.parseColor("#8BC34A"));
            //set text view values
            tvDate.setText(activity.getAssignmentDueDate().toString().substring(5));
            tvTime.setText(activity.getAssignmentDueTime().toString().substring(0,5));
            tvModule.setText(activity.getModID());
            tvTitle.setText(activity.getAssignmentTitle());



        return view;
    }
}
