package com.a2pt.whatsnext.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.MainActivity;
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.dbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2017-07-29.
 */

public class SendEmailFragment extends Fragment {
    View view;
    Button btnSend;
    EditText subject, body;
    Spinner spnEmailGroups;
    MainActivity main;
    User user;
    String[] sendTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_email_layout, container, false);

        subject = (EditText)view.findViewById(R.id.txtSubject);
        body = (EditText)view.findViewById(R.id.txtBody);
        btnSend = (Button)view.findViewById(R.id.btnSend);
        sendTo = new String[1];
        //Set the Spinner content and grab selected item on SpinnerClick
        spnEmailGroups = (Spinner)view.findViewById(R.id.spnSendEmail);
        main = (MainActivity)getActivity();
        user = main.getCurUser();

        final List<String> modules = new ArrayList<>();

        for (String module: user.getModules())
        {
            modules.add(module);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modules); //set List to Adapter
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //make the drop down list look nice
        spnEmailGroups.setAdapter(dataAdapter); //set the spinner's adapter

        spnEmailGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendTo[0] = spnEmailGroups.getItemAtPosition(i).toString().trim();
                sendTo[0] += "@mandela.ac.za";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sendTo[0] = spnEmailGroups.getItemAtPosition(0).toString().trim();
                sendTo[0] += "@mandela.ac.za";
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subjectText = subject.getText().toString();
                String bodyText = body.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, sendTo);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
                emailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished sending email.", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    System.out.println("Error");
                }
            }
        });


        return view;
    }
}
