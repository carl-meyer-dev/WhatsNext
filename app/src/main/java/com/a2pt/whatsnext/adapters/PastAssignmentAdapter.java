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

public class PastAssignmentAdapter extends ArrayAdapter<Activity> {

    //generated constructor method
    public PastAssignmentAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_past_adapter,activities);

    }

    //getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_past_adapter, parent, false);
        }

        view.setTag(getItem(position));

        TextView tvDate = (TextView)view.findViewById(R.id.als_tvDatePast);
        TextView tvTime = (TextView)view.findViewById(R.id.als_tvTimePast);
        TextView tvTitle = (TextView)view.findViewById(R.id.als_tvTitlePast);
        TextView tvModule = (TextView)view.findViewById(R.id.als_tvModulePast);
        LinearLayout lLayout = (LinearLayout)view.findViewById(R.id.als_linLayPast);


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

