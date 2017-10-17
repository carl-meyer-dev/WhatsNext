package com.a2pt.whatsnext.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;

import org.w3c.dom.Text;

import java.util.List;

public class MaintainLecturesActivityAdapter extends ArrayAdapter<Activity> {

    boolean[] isChecked;

    public MaintainLecturesActivityAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_maintain_lectures_adapter,activities);
        isChecked = new boolean[activities.size()];

    }
    private static class ViewHolder
    {
        TextView tvLect1;
        TextView tvLect2;
        TextView tvLect3;
        TextView tvLect4;
        CheckBox cbSelection;
        LinearLayout lLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        final ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_maintain_lectures_adapter, parent, false);


            viewHolder.tvLect1 = (TextView)view.findViewById(R.id.tvLect1);
            viewHolder.tvLect2 = (TextView)view.findViewById(R.id.tvLect2);
            viewHolder.tvLect3 = (TextView)view.findViewById(R.id.tvLect3);
            viewHolder.tvLect4 = (TextView)view.findViewById(R.id.tvLect4);
            viewHolder.cbSelection = (CheckBox) view.findViewById(R.id.cbLectureSelection);
            viewHolder.lLayout = (LinearLayout)view.findViewById(R.id.linLayLectureSelection);

        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }


        view.setTag(getItem(position));


        Activity activity = getItem(position);

        //Change colour of background
        viewHolder.lLayout.setBackgroundColor(Color.parseColor("#1E88E5"));

        viewHolder.tvLect1.setText(activity.getLecStartTime().toString().substring(0,5));
        viewHolder.tvLect2.setText(activity.getModID());
        viewHolder.tvLect3.setText(activity.getLectureVenue());
        viewHolder.tvLect4.setText(activity.getDayOfWeek());

        if(isChecked[position])
        {
            viewHolder.cbSelection.setChecked(true);
        }
        else
        {
            viewHolder.cbSelection.setChecked(false);
        }

        return view;
    }

    public boolean getIsCheckedState(int position)
    {
        return isChecked[position];
    }

    public int getIsCheckedLength()
    {
        return isChecked.length;
    }
}
