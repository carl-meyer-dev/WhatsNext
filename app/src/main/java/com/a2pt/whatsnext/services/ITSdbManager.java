package com.a2pt.whatsnext.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a2pt.whatsnext.models.User;

/**
 * Created by Carl on 2017-09-29.
 */

public class ITSdbManager extends SQLiteOpenHelper{

    // ITS Database Schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ITS.db";

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USEREMAIL = "user_email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TYPE_OF_USER = "type_of_user";
    private static final String KEY_COURSEINFO = "course_info";



    public ITSdbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_USEREMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_TYPE_OF_USER + " TEXT,"
                + KEY_COURSEINFO + " TEXT"
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void insertData(User user){
        //get data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //TODO: Here we can have a loop inserting all Dummy Accounts

        values.put(KEY_ID, user.getUserID());
        values.put(KEY_USERNAME, user.getUserName());
        values.put(KEY_USEREMAIL, user.getUserEmail());
        values.put(KEY_PASSWORD, user.getUserPassword());
        values.put(KEY_TYPE_OF_USER, user.getUserType());
        values.put(KEY_COURSEINFO, user.getCourseInfo());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User getUser(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,                                                                                            //Table Name - SELECT FROM TABLE
                new String[]{KEY_ID, KEY_USERNAME, KEY_USEREMAIL, KEY_PASSWORD, KEY_TYPE_OF_USER, KEY_COURSEINFO},      //All the Fields that you watn to capture
                KEY_ID + "=?",                                                                                    //Where Username = ?
                new String[]{ String.valueOf(username)},                                                                // Where ? = String.Value(What you are looking for)
                null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return user;
    }

    public boolean getCompareLoginCredentials(String loginUsername, String loginPassword) {

        System.out.println("CHECKING LOGIN DETAILS");
        SQLiteDatabase db = this.getReadableDatabase();



        boolean validLogin = false;

        //Get all the Usernames and Passwords from the Users Table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        System.out.println("SUCESSFULLY QUERIED DB");

        //get Column index of userID and password fields
        int userIDIndex = cursor.getColumnIndex("id");
        int passwordIndex = cursor.getColumnIndex("password");
        cursor.moveToFirst();
        System.out.println(cursor.moveToFirst());

        // loop through data to get and compare login details
        if(true){ //Go to first entry
            do {
                System.out.println("DB DEBUG: getting username and password from Table");
                String dbUsername = cursor.getString(userIDIndex);
                String dbPassword = cursor.getString(passwordIndex);
                System.out.println("DB DEBUG: dbUsername = " + dbUsername);
                System.out.println("DB DEBUG: dbPassword = " + dbPassword);


                if (dbUsername.equals(loginUsername) && dbPassword.equals(loginPassword)) {
                    validLogin = true;
                    break;
                }


            }while(cursor.moveToNext());
        }


        cursor.close();


        return validLogin;
    }

}
