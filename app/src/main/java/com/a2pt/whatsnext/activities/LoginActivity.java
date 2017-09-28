package com.a2pt.whatsnext.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.services.DatabaseHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialise();

        SharedPreferences getState = getSharedPreferences("State", MODE_PRIVATE);

        if (getState.getString("loggedIn", "").toString().equals("loggedIn"))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyDetails();
            }
        });


    }

    private void verifyDetails() {

        String password = txtPassword.getText().toString();
        String loginName = txtUserName.getText().toString();

        //Checks to see if the user has entered a username
        if(loginName == null)
        {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_LONG).show();
            return;
        }

        //Checks to see if the user has entered a password
        if (password == null)
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password == null && loginName == null)
        {
            Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_LONG).show();
            return;
        }

        //Checks the database if the user exists
        if(databaseHelper.checkUserCredentials(loginName, password) && loginName != null & password != null)
        {
            //if the user exists then they are taken to the main page
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("type", databaseHelper.getUserType(loginName));
            startActivity(intent);
        }
        else
        {
            //if they do not exit or typed an incorrect password/username then they must enter details
            Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
            txtPassword.setText(null);
            txtUserName.setText(null);
        }

    }

    private void initialise() {
        //Initialise TextFields
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUserName = (EditText) findViewById(R.id.txtUserName);

        //Initialise Button
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Initialise helper class
        databaseHelper = new DatabaseHelper(LoginActivity.this);
        //databaseHelper.insertData();
    }

}

