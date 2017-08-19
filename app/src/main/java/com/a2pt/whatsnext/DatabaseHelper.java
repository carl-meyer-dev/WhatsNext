package com.a2pt.whatsnext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Setherus on 2017/08/09.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    //Creating the variables needed
    private static final String databaseName = "WhatsNext";
    private static final int databaseVersion = 1;
    private SQLiteDatabase db;

    //Creating variables to read values in from text files
    private BufferedReader br;
    private StringBuilder sb;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //on onCreate Method setupTables is called to create the tables of the database.
        db = database;
        setupTables();
        inputValuesIntoTables();

    }

    //Loads SQL queries setup in text files (Preset)
    //Injects these queries into the database to add the variables into their respective tables
    private void inputValuesIntoTables() {

        inputUser("User.txt");
        inputUser("Activity.txt");
        inputUser("Module.txt");
        inputUser("Session");
    }

    private void inputUser(String s) {

        try {
            //initialising the buffered reader to read user.txt and  string builder
            br = new BufferedReader(new FileReader(s));
            sb = new StringBuilder();

            //getting information from the text file
            String line = br.readLine();

            //Runs the string until there is nothing left inside of it
            while(line != null)
            {
                db.execSQL(line);
                //reads the next line in the text file if there is one
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Setting up of each individual table
    private void setupTables() {

        //Creating SQL strings which will build tables
        String userTableSQL = "CREATE TABLE User (UserID varchar(25) NOT NULL UNIQUE PRIMARY KEY, UserEmail varchar(50) NOT NULL, UserPass varchar(30) NOT NULL, UserType varchar(20) DEFAULT 'Student')";
        String lecturerTableSQL = "CREATE TABLE Lecturer (UserID varchar(25) NOT NULL UNIQUE PRIMARY KEY)";
        String teachesTableSQL = "CREATE TABLE Teaches (SessionID int NOT NULL PRIMARY KEY, UserID varchar(25) NOT NULL FOREIGN KEY, ModID varchar(8) NOT NULL FOREIGN KEY)";
        String sessionTableSQL = "CREATE TABLE Session (SessionID int NOT NULL PRIMARY KEY AUTO_INCREMENT, SessionStart time, SessionEnd time)";
        String activityTableSQL = "CREATE TABLE Activity (ModID varchar(8) NOT NULL PRIMARY KEY, ActType varchar(25), AssignmentTitle varchar(50), AssignmentDueDate date, AssignmentSubmissionTime time, AssignmentStatus boolean, TestName varchar(25, TestTime time, LectureVenue varchar(50), SessionID int NOT NULL FOREIGN KEY)";
        String moduleTableSQL = "CREATE TABLE Module (ModID varchar(8) NOT NULL UNIQUE PRIMARY KEY, ModName varchar(100)";

        //Executing the SQL strings above to create database tables
        db.execSQL(userTableSQL);
        db.execSQL(lecturerTableSQL);
        db.execSQL(teachesTableSQL);
        db.execSQL(sessionTableSQL);
        db.execSQL(activityTableSQL);
        db.execSQL(moduleTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
