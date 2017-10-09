package com.a2pt.whatsnext.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.User;

import com.a2pt.whatsnext.services.ITSdbManager;
import com.a2pt.whatsnext.services.dbManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.File;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    ITSdbManager ITSdb;
    dbManager localDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //=========================================================================================
        //Carl Code:
        ITSdb = new ITSdbManager(this);
        localDB = new dbManager(this);

        SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);

        // boolean check = preferences.getBoolean("loggedIn", false);
        String loggedin = preferences.getString("loggedIn", "false");
        System.out.println("USER ALREADY LOGGED IN? : " + loggedin);

        //Check if user already Logged in
        if (loggedin.equalsIgnoreCase("true"))
        {
            System.out.println("USER ALREADY LOGGED INTO APP");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            //Else no user logged in / first time logging in
            System.out.println("NO USER IS LOGGED INTO APP");

        }
        //=========================================================================================



    }

    public void login(View view) {

        String loginUsername, loginPassword;

        loginUsername = txtUserName.getText().toString();
        loginPassword = txtPassword.getText().toString();

        boolean validLogin = ITSdb.getCompareLoginCredentials(loginUsername, loginPassword);
        System.out.println("VALID LOGIN: " + validLogin);

        if(validLogin){

            //Get the user Object from the Database
            User user = ITSdb.getUser(loginUsername);
            System.out.println(user.toString());

            //Sets the users state to be true. This will if they have logged in before to instantly go to their main page
            setState(loginUsername, user.getUserType());

            //Adding all of the students necessary information into the local.db
            setupLocalDB(user);

            //put user inside bundle to be sent
            Bundle bundle= new Bundle();
            bundle.putSerializable("user", user);
            //create intent, put in bundle and start Main Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish(); //This removes the users ability to go back into the login screen

        }else{
            Toast toast = Toast.makeText(this, "Invalid Login!", Toast.LENGTH_LONG);
            toast.show();
            txtUserName.setText("");
            txtPassword.setText("");
        }


    }

    //Adds the user to the database
    //Pulls all assignments, tests and modules from ITSdatabase
    //adds to the local.db
    private void setupLocalDB(User userToAdd) {

        localDB.insertData(userToAdd);

        String[] moduleInfo = userToAdd.getModules();


        for (String moduleDetail: moduleInfo)
        {
            localDB.addLecture(moduleDetail, ITSdb);
            localDB.addAssignment(moduleDetail, ITSdb);
            localDB.addTest(moduleDetail, ITSdb);
        }

    }

    private void setState(String usertype, String loginUsername) {
        SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loggedIn", "true");
        editor.putString("userName", loginUsername);
        editor.putString("usertype", usertype);
        editor.commit();
    }

}

