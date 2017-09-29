package com.a2pt.whatsnext.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.activities.LoginActivity;
import com.a2pt.whatsnext.models.Activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Setherus on 2017/08/09.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    Context context;
    //Creating the variables needed
    private static final String databaseName = "WhatsNext.db";
    private static final int databaseVersion = 1;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //on onCreate Method setupTables is called to create the tables of the database.
        db = database;
        setupTables();
    }

    public void inputText(InputStream toReadFrom) {

        db = this.getWritableDatabase();
        /*try {
            //initialising the buffered reader to read
            BufferedReader br = new BufferedReader(new FileReader(s));

            //getting information from the text file
            String line = br.readLine().trim();

            //Runs the string until there is nothing left inside of it
            while(line != null)
            {
                db.execSQL(line);
                //reads the next line in the text file if there is one
                line = br.readLine().trim();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(toReadFrom));
            String line = br.readLine().trim();

            while (line != null)
            {
                db.execSQL(line);

                line = br.readLine().trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Setting up of each individual table
    private void setupTables() {

        //Calling inputText which reads in the SQL query from text file to create up the tables
        db.execSQL("CREATE TABLE User (UserID varchar(25) NOT NULL UNIQUE PRIMARY KEY, UserEmail varchar(50) NOT NULL, UserPass varchar(30) NOT NULL, UserType varchar(20) DEFAULT 'Student')");
        db.execSQL("CREATE TABLE Module (ModID varchar(8) NOT NULL UNIQUE PRIMARY KEY, ModName varchar(100))");
        db.execSQL("CREATE TABLE Teaches (SessionID INTEGER NOT NULL PRIMARY KEY, UserID varchar(25), ModID varchar(8), FOREIGN KEY(UserID) REFERENCES User(UserID), FOREIGN KEY(ModID) REFERENCES Module(ModID))");
        db.execSQL("CREATE TABLE Session (SessionID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, SessionStart time, SessionEnd time)");
        db.execSQL("CREATE TABLE Activity (ModID varchar(8) NOT NULL PRIMARY KEY, ActType varchar(25), AssignmentTitle varchar(50), AssignmentDueDate date, AssignmentSubmissionTime time, AssignmentStatus boolean, TestName varchar(25), TestTime time, LectureVenue varchar(50), SessionID INTEGER, FOREIGN KEY(SessionID) REFERENCES Session(SessionID))");

        db.execSQL("INSERT INTO User (UserID, UserEmail, UserPass, UserType) VALUES ('s215144988', 's215144988@nmmu.ac.za', 'logmein', 'student')");
        //Testing to read in with method inputText
        //inputText("raw/tablecreate.txt");
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
        inputText(context.getResources().openRawResource(R.raw.assignment));
        inputText(context.getResources().openRawResource(R.raw.lecture));
        inputText(context.getResources().openRawResource(R.raw.module));
        inputText(context.getResources().openRawResource(R.raw.user));
        inputText(context.getResources().openRawResource(R.raw.session));
        inputText(context.getResources().openRawResource(R.raw.test));
    }

    //Takes in a activity as input
    //The various information from the activity are read and inserted into the database
    public void insertNewTest(Activity activityTest)
    {
        //Gets the database and allows it to be editted
        db = this.getWritableDatabase();

        //Gets information from the activityTest and inserts it into the database
        db.execSQL("INSERT INTO Activity (ModID, ActType, TestName, TestTime, TestVenue) VALUES ('"+ activityTest.getModID() + "', '" + activityTest.getActType()
                + ", " + activityTest.getTestDescriiption() + "', '" + activityTest.getTestTime() + "', '" + activityTest.getTestVenue() +"')");


    }

    //The same as the above mentioned method
    public void insertNewAssignment(Activity activityAssignment)
    {
        //Gets the database and allows it to be editted
        db = this.getWritableDatabase();

        //Gets information from the activityAssignment and inserts it into the database
        db.execSQL("INSERT INTO TABLE Activity (ModID, ActType, AssignmentTitle, AssignmentDueDate, AssignmentSubmissionTime, AssignmentStatus) VALUES ('"+ activityAssignment.getModID() + "', '" + activityAssignment.getActType()
                + ", " + activityAssignment.getAssignmentTitle() + "', '" + activityAssignment.getAssignmentDueDate() + "', '" + activityAssignment.getAssignmentDueTime() + "', '" + activityAssignment.getAssignmentStatus() +"')");


    }

    public boolean checkUserCredentials(String userName, String password) {
        //Creating database to be read/write from
        db = this.getWritableDatabase();

        //Array of selection arguments that the cursor has to traverse through;
        /*String[] selectionArgs = {userName, password};
        String[] selection = {"UserID"};
        String checkDetails = "UserID = ? AND UserPass = ?";

        //Cursor traverses the column user, it applies conditions to search if the user exits
        //Stores a result in the cursor
        Cursor cursor = db.rawQ("User", selection,checkDetails, selectionArgs, null, null, null):

        //If the user exits a value will be given (eg 1) if not -1 will be returned
        int count = cursor.getCount();*/

        Cursor cursor = db.rawQuery("SELECT count(*) FROM User WHERE UserID = ? AND UserPass = ? ", new String[] {userName, password});

        int count = cursor.getCount();
        cursor.close();

        //If the User exists in the database then the count will be 1 and then true will be returned
        if (count > 0)
        {
            return true;
        }
        //If there is no user false will be returned
        return false;
    }

    //Returns a int value based on the type of user.
    // 0 for normal user and 1 for lecturer
    public int getUserType(String username)
    {
        String query = "SELECT UserType FROM User WHERE UserID = "+username;

        //Cursor traverses through the database to find the usertype
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        //get the user type
        String userType = cursor.getString(1);

        //Checks to see what type of user it is
        //Returns int value of type
        if (userType == "lecturer")
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
