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

        try{
            insertData();
        }catch (Exception e){
            //most probably the data already exists and there is a conflict in Primary Keys
            e.printStackTrace();
        }



        //=========================================================================================

        SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);

        boolean check = preferences.getBoolean("loggedIn", false);
        if (check)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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
            //setupLocalDB(user);

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
        editor.putBoolean("loggedIn", true);
        editor.putString("userName", loginUsername);
        editor.putString("usertype", usertype);
        editor.commit();
    }

    public void insertData(){
        //Gonna use Dummy Variables here but we will have to set up a fake acounts from Textfile or List or something

        User user = new User("s215006941", "Carl Meyer", "s215006941@nmmu.ac.za", "abc123", "student", "BSc Computer Science", "WRAP302,WRL301,MATH214,MATH203,STAT203, WRR301");
        ITSdb.insertData(user);
        user = new User("s215144988", "Gerrit Naude", "ss215144988@nmmu.ac.za", "def456", "student", "BSc Information Systems","WRAP302,EBM302,WRUI301,WRB302,WRR301");
        ITSdb.insertData(user);
        user = new User("Vogts.Dieter", "Dieter Vogts", "Vogts.Dieter@nmmu.ac.za", "wrap2017", "lecturer", "Computer Science", "WRAP301,WRAP302,WRA301");
        ITSdb.insertData(user);
        user = new User("Nel.Janine", "Janine Nel", "Nel.Janine@nmmu.ac.za", "wrr301", "lecturer", "Computer Science", "WRR301, WRI201, WRI202");
        ITSdb.insertData(user);
        user = new User("admin", "admin", "admin@nmmu.ac.za", "admin", "lecturer", "admin", "admin");
        ITSdb.insertData(user);
        //Insert Activities
        Activity activity = new Activity("MATH203", "lecture", "35 00 17", new LocalTime(7,45),"monday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRAP302", "lecture", "35 01 01", new LocalTime(9,5), "monday", 0);
        ITSdb.insertLecture(activity);

        activity = new Activity("MATH214 Tut", "lecture", "04 00 01", new LocalTime(9,5), "tuesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("STAT203", "lecture", "04 00 3", new LocalTime(14,5), "tuesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("STAT203 Prac","lecture", "07 02 48", new LocalTime(15,30), "tuesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("STAT203 Tut","lecture", "07 02 48", new LocalTime(16,45), "tuesday", 0);
        ITSdb.insertLecture(activity);

        activity = new Activity("STAT203", "lecture", "35 00 17", new LocalTime(7,45),"wednesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("MATH203", "lecture", "35 00 18", new LocalTime(9,5),"wednesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRR301", "lecture", "09 02 02", new LocalTime(10,25),"wednesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("MATH214", "lecture", "07 02 50", new LocalTime(14,5),"wednesday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRAP302 Prac", "lecture", "09 02 04", new LocalTime(15,35),"wednesday", 0);
        ITSdb.insertLecture(activity);

        activity = new Activity("MATH214", "lecture", "07 02 50", new LocalTime(7,45),"thursday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRL301", "lecture", "35 00 18", new LocalTime(10,25),"thursday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRL301 Tut", "lecture", "35 00 16", new LocalTime(14,5),"thursday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("STAT203", "lecture", "35 00 18", new LocalTime(16,45),"thursday", 0);
        ITSdb.insertLecture(activity);
        activity = new Activity("WRMS302", "lecture", "09 02 02", new LocalTime(9,5), "friday", 0);
        ITSdb.insertLecture(activity);

        activity = new Activity("WRAP302", "assignment", "Assignment 5", new LocalDate(2017,9,12), new LocalTime(23,35), 0);
        ITSdb.insertAssignment(activity);
        activity = new Activity("STAT203", "assignment", "Prac 2", new LocalDate(2017,9,12), new LocalTime(14,5), 0);
        ITSdb.insertAssignment(activity);
        activity = new Activity("WRR301", "assignment", "Final", new LocalDate(2017,10,23), new LocalTime(12,0), 0);
        ITSdb.insertAssignment(activity);

        activity = new Activity("STAT203", "test", "Tut Test 2",new LocalDate(2017,9,12),new LocalTime(18,0), "07 02 48");
        ITSdb.insertTest(activity);
        activity = new Activity("MATH214", "test", "Seme Test 2",new LocalDate(2017,9,14),new LocalTime(18,0), "Heinz Benz Hall");
        ITSdb.insertTest(activity);
        activity = new Activity("WRL301", "test", "Seme Test 2",new LocalDate(2017,10,4),new LocalTime(18,0), "35 00 17");
        ITSdb.insertTest(activity);
        activity = new Activity("WRAP302", "test", "Seme Test 2",new LocalDate(2017,10,6),new LocalTime(14,0), "09 02 04");
        ITSdb.insertTest(activity);
        activity = new Activity("STAT203", "test", "Seme Test 2",new LocalDate(2017,10,10),new LocalTime(18,0), "07 02 48");
        ITSdb.insertTest(activity);
        activity = new Activity("MATH203", "test", "Seme Test 2",new LocalDate(2017,10,12),new LocalTime(18,0), "Indoor Sport Centre");
        ITSdb.insertTest(activity);

    }




}

