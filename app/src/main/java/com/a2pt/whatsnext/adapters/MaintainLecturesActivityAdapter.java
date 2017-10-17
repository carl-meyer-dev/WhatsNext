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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;

import org.w3c.dom.Text;

import java.util.List;

public class MaintainLecturesActivityAdapter extends ArrayAdapter<Activity> {

    boolean[] selected;


    public MaintainLecturesActivityAdapter(@NonNull Context context, List<Activity> activities) {
        super(context,R.layout.activity_maintain_lectures_adapter,activities);
        selected = new boolean[activities.size()];

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        final ViewHolder viewHolder;


        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_maintain_lectures_adapter, parent, false);
        }

        view.setTag(position);
        TextView tvValue1 = (TextView)view.findViewById(R.id.tvLect1);
        TextView tvValue2 = (TextView)view.findViewById(R.id.tvLect2);
        TextView tvValue3 = (TextView)view.findViewById(R.id.tvLect3);
        TextView tvValue4 = (TextView)view.findViewById(R.id.tvLect4);
        CheckBox cbSelection = (CheckBox)view.findViewById(R.id.cbLectureSelection);
        LinearLayout lLayout = (LinearLayout)view.findViewById(R.id.linLayLectureSelection);

        Activity activity = getItem(position);

        //Change colour of background
        lLayout.setBackgroundColor(Color.parseColor("#1E88E5"));

        tvValue1.setText(activity.getLecStartTime().toString().substring(0,5));
        tvValue2.setText(activity.getModID());
        tvValue3.setText(activity.getLectureVenue());
        tvValue4.setText(activity.getDayOfWeek());

        if(selected[position])
        {
            cbSelection.setChecked(true);
        }
        else
        {
            cbSelection.setChecked(false);
        }

        cbSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               selected[position] = isChecked;
                System.out.println("DEBUG CHECKBOX SELECTED = " + isChecked);
            }
        });

        return view;
    }

    public boolean getSelected(int position)
    {
        return selected[position];
    }

    public int getIsCheckedLength()
    {
        return selected.length;
    }


}
