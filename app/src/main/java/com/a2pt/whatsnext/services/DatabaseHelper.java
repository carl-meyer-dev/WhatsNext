package com.a2pt.whatsnext.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a2pt.whatsnext.models.Activity;

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
    private static final String databaseName = "WhatsNext.db";
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
        insertData();

        //Once all the information has been inserted into the database it should be closed.
        db.close();

    }

    public void inputText(String s) {

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

        //Calling inputText which reads in the SQL query from text file to create up the tables
        inputText("TableCreate.txt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TF EXISTS User");
        db.execSQL("DROP TF EXISTS Teaches");
        db.execSQL("DROP TF EXISTS Session");
        db.execSQL("DROP TF EXISTS Activity");
        db.execSQL("DROP TF EXISTS Module");

        onCreate(sqLiteDatabase);
    }

    //Calls in the data from textfile "Test.txt" Puts the various information from textfile into the database
    public void insertData()
    {
        inputText("Test.txt");
        inputText("Lecture.txt");
        inputText("Assignment.txt");
    }

    //Takes in a activity as input
    //The various information from the activity are read and inserted into the database
    public void insertNewTest(Activity activityTest)
    {
        //Gets the database and allows it to be editted
        db = this.getReadableDatabase();

        //Gets information from the activityTest and inserts it into the database
        db.execSQL("INSERT INTO Activity (ModID, ActType, TestName, TestTime, TestVenue) VALUES ('"+ activityTest.getModID() + "', '" + activityTest.getActType()
                + ", " + activityTest.getTestDescriiption() + "', '" + activityTest.getTestTime() + "', '" + activityTest.getTestVenue() +"')");

        db.close();
    }

    //The same as the above mentioned method
    public void insertNewAssignment(Activity activityAssignment)
    {
        //Gets the database and allows it to be editted
        db = this.getReadableDatabase();

        //Gets information from the activityAssignment and inserts it into the database
        db.execSQL("INSERT INTO TABLE Activity (ModID, ActType, AssignmentTitle, AssignmentDueDate, AssignmentSubmissionTime, AssignmentStatus) VALUES ('"+ activityAssignment.getModID() + "', '" + activityAssignment.getActType()
                + ", " + activityAssignment.getAssignmentTitle() + "', '" + activityAssignment.getAssignmentDueDate() + "', '" + activityAssignment.getAssignmentDueTime() + "', '" + activityAssignment.getAssignmentStatus() +"')");

        db.close();
    }

    public boolean checkUserCredentials(String userName, String password) {
        //Creating database to be read/write from
        SQLiteDatabase dbToCheck = this.getReadableDatabase();

        //Array of selection arguments that the cursor has to traverse through;
        String[] selectionArgs = {userName, password};
        String[] selection = {"UserID"};

        //Cursor traverses the column user, it applies conditions to search if the user exits
        //Stores a result in the cursor
        Cursor cursor = db.query("User", selection, "UserEmail = ? AND UserPass = ?", selectionArgs, null, null, null);

        //If the user exits a value will be given (eg 1) if not -1 will be returned
        int count = cursor.getCount();

        cursor.close();
        dbToCheck.close();

        //If the User exists in the database then the count will be 1 and then true will be returned
        if (count > 0)
        {
            return true;
        }
        //If there is no user false will be returned
        return false;
    }
}
