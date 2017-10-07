package com.a2pt.whatsnext.services;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.Module;
import com.a2pt.whatsnext.models.Session;
import com.a2pt.whatsnext.models.Teaches;
import com.a2pt.whatsnext.models.User;

import java.io.File;

/**
 * Created by Carl on 2017-09-29.
 */

public class ITSdbManager extends SQLiteOpenHelper{

    // ITS Database Schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ITS.db";

    //Creating the User table
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USEREMAIL = "user_email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TYPE_OF_USER = "type_of_user";
    private static final String KEY_COURSEINFO = "course_info";
    private static final String KEY_MODULE_INFO = "module_info";

    //Creating the ACTIVITY Table
    private static final String TABLE_ACTIVITY = "activity";
    private static final String KEY_ACT_ID = "id";
    private static final String KEY_ACT_MOD_ID = "mod_id";
    private static final String KEY_ACT_ACT_TYPE = "act_type";
    private static final String KEY_ACT_TITLE = "title";
    private static final String KEY_ACT_DUE_DATE = "assignment_due_date";
    private static final String KEY_ACT_SUBMISSION_TIME = "assignment_submission_time";
    private static final String KEY_ACT_STATUS = "assignment_status";
    private static final String KEY_ACT_TEST_NAME = "test_name";
    private static final String KEY_ACT_TEST_TIME = "test_time";
    private static final String KEY_ACT_TEST_DATE = "test_date";
    private static final String KEY_ACT_VENUE = "venue";
    private static final String KEY_ACT_LECTURE_START_TIME = "lecture_start_time";
    private static final String KEY_ACT_LECTURE_DAY_OF_WEEK = "lecture_day_of_week";
    private static final String KEY_ACT_LECTURE_DUPLICATE = "duplicate_lecture";

    //Creating the Module Table
    private static final String TABLE_MODULE = "modules";
    private static final String KEY_MODULE_MOD_ID = "mod_id";
    private static final String KEY_MODULE_MOD_NAME = "mod_name";

    //Creating the Teaches Table
    private static final String TABLE_TEACHES = "teaches";
    private static final String KEY_TEACHES_SESSION_ID = "session_id";
    private static final String KEY_TEACHES_ID = "user_id"; //This is the same as the userid
    private static final String KEY_TEACHES_MOD_ID = "mod_id";

    //Creating the Session Table
    private static final String TABLE_SESSION = "sessions";
    private static final String KEY_SESSION_SESSION_ID = "session_id";
    private static final String KEY_SESSION_SESSION_START = "session_start";
    private static final String KEY_SESSION_SESSION_END = "session_end";
    private static final String KEY_SESSION_DAY_OF_WEEK = "session_day_of_week";



    public ITSdbManager(Context context) {

        super(context, DATABASE_NAME , null, DATABASE_VERSION); //for default location


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
                + KEY_COURSEINFO + " TEXT,"
                + KEY_MODULE_INFO + " TEXT"
                + ")";

        String CREATE_MODULE_TABLE = "CREATE TABLE " + TABLE_MODULE + " ("
                + KEY_MODULE_MOD_ID + " TEXT PRIMARY KEY,"
                + KEY_MODULE_MOD_NAME + " TEXT"
                + ")";

        String CREATE_TEACHES_TABLE = "CREATE TABLE " + TABLE_TEACHES + " ("
                + KEY_TEACHES_SESSION_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEACHES_ID + " TEXT,"
                + KEY_TEACHES_MOD_ID + " TEXT, "
                + "FOREIGN KEY(" + KEY_TEACHES_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "), "
                + "FOREIGN KEY (" + KEY_TEACHES_MOD_ID + ") REFERENCES " + TABLE_MODULE + "(" + KEY_MODULE_MOD_ID + "))";

        String CREATE_SESSION_TABLE = "CREATE TABLE " + TABLE_SESSION + " ("
                + KEY_SESSION_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SESSION_SESSION_START + " TIME,"
                + KEY_SESSION_SESSION_END + " TIME,"
                + KEY_SESSION_DAY_OF_WEEK + " TEXT)";

        String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_ACTIVITY + " ("
                + KEY_ACT_ID + " INTEGER PRIMARY KEY,"
                + KEY_ACT_MOD_ID + " TEXT ,"
                + KEY_ACT_ACT_TYPE + " TEXT,"
                + KEY_ACT_TITLE + " TEXT,"
                + KEY_ACT_DUE_DATE + " DATE,"
                + KEY_ACT_SUBMISSION_TIME + " DATE,"
                + KEY_ACT_STATUS + " INTEGER,"
                + KEY_ACT_TEST_NAME + " TEXT,"
                + KEY_ACT_TEST_TIME + " TIME,"
                + KEY_ACT_TEST_DATE + " DATE,"
                + KEY_ACT_VENUE + " TEXT,"
                + KEY_ACT_LECTURE_START_TIME + " TEXT,"
                + KEY_ACT_LECTURE_DAY_OF_WEEK + " TEXT,"
                + KEY_ACT_LECTURE_DUPLICATE + " INTEGER)";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_MODULE_TABLE);
        db.execSQL(CREATE_TEACHES_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
        db.execSQL(CREATE_ACTIVITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHES);
        onCreate(db);
    }

    public void insertData(User user){
        //get data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_ID, user.getUserID());
        values.put(KEY_USERNAME, user.getUserName());
        values.put(KEY_USEREMAIL, user.getUserEmail());
        values.put(KEY_PASSWORD, user.getUserPassword());
        values.put(KEY_TYPE_OF_USER, user.getUserType());
        values.put(KEY_COURSEINFO, user.getCourseInfo());
        values.put(KEY_MODULE_INFO, user.getModuleInfo());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    /*
    * THIS WILL NOT WORK. It causes Nullpointer Exceptions and does not insert activities correctly
    *
    public void insertActivity(Activity activity)
    {
        System.out.println("INSERT ACTIVITY!");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType());
        values.put(KEY_ACT_TITLE, activity.getAssignmentTitle());
        values.put(KEY_ACT_DUE_DATE, activity.getAssignmentDueDate().toString());
        values.put(KEY_ACT_SUBMISSION_TIME, activity.getAssignmentDueTime().toString());
        values.put(KEY_ACT_STATUS, activity.getAssignmentStatus());
        values.put(KEY_ACT_TEST_NAME, activity.getTestDescriiption());
        values.put(KEY_ACT_TEST_TIME, activity.getTestTime().toString());
        values.put(KEY_ACT_VENUE, activity.getTestVenue());
        values.put(KEY_ACT_LECTURE_START_TIME, activity.getLecStartTime().toString());
        values.put(KEY_ACT_LECTURE_DAY_OF_WEEK, activity.getDayOfWeek());

        db.insert(TABLE_ACTIVITY, null, values);
        db.close();

    }
*/

    /**
     * Instead have separate insert methods for each activity type.
     * Then there wont be null exceptions
     */
    public void insertLecture(Activity activity){
        System.out.println("DEBUG ITSdbManager: INSERT LECTURE");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType());
        values.put(KEY_ACT_VENUE, activity.getLectureVenue());
        values.put(KEY_ACT_LECTURE_START_TIME, activity.getLecStartTime().toString().substring(0,5));
        values.put(KEY_ACT_LECTURE_DAY_OF_WEEK, activity.getDayOfWeek());
        values.put(KEY_ACT_LECTURE_DUPLICATE, activity.getIsDuplicate());

        db.insert(TABLE_ACTIVITY, null, values);
        db.close();

    }

    public void insertAssignment(Activity activity){
        System.out.println("DEBUG ITSdbManager: INSERT ASSIGNMENT");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType());
        values.put(KEY_ACT_TITLE, activity.getAssignmentTitle());
        values.put(KEY_ACT_DUE_DATE, activity.getAssignmentDueDate().toString());
        values.put(KEY_ACT_SUBMISSION_TIME, activity.getAssignmentDueTime().toString().substring(0,5));
        values.put(KEY_ACT_STATUS, activity.getAssignmentStatus());

        db.insert(TABLE_ACTIVITY, null, values);
        db.close();

    }

    public void insertTest(Activity activity){
        System.out.println("DEBUG ITSdbManager: INSERT ASSIGNMENT");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType());
        values.put(KEY_ACT_TEST_NAME, activity.getTestDescriiption());
        values.put(KEY_ACT_TEST_DATE, activity.getTestDate().toString());
        values.put(KEY_ACT_TEST_TIME, activity.getTestTime().toString().substring(0,5));
        values.put(KEY_ACT_VENUE, activity.getTestVenue());

        db.insert(TABLE_ACTIVITY, null, values);
        db.close();

    }

    public void insertSession(Session session)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SESSION_SESSION_ID, session.getSessionID());
        values.put(KEY_SESSION_SESSION_START, session.getStartTime().toString());
        values.put(KEY_SESSION_SESSION_END, session.getEndTime().toString());
        values.put(KEY_SESSION_DAY_OF_WEEK, session.getDayOfWeek().toString());

        db.insert(TABLE_SESSION, null, values);
        db.close();
    }

    public void insertTeaches(Teaches teaches)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TEACHES_SESSION_ID, teaches.getSessionID());
        values.put(KEY_TEACHES_ID, teaches.getUserID());
        values.put(KEY_TEACHES_MOD_ID, teaches.getModID());

        db.insert(TABLE_TEACHES, null, values);
        db.close();
    }

    public void insertModule(Module module)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MODULE_MOD_ID, module.getModID());
        values.put(KEY_MODULE_MOD_NAME, module.getModName());

        db.insert(TABLE_MODULE, null, values);
        db.close();
    }

    public User getUser(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,                                                                                            //Table Name - SELECT FROM TABLE
                new String[]{KEY_ID, KEY_USERNAME, KEY_USEREMAIL, KEY_PASSWORD, KEY_TYPE_OF_USER, KEY_COURSEINFO, KEY_MODULE_INFO},      //All the Fields that you watn to capture
                KEY_ID + "=?",                                                                                    //Where Username = ?
                new String[]{ String.valueOf(username)},                                                                // Where ? = String.Value(What you are looking for)
                null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

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
        int userIDIndex = cursor.getColumnIndex(KEY_ID);
        int passwordIndex = cursor.getColumnIndex(KEY_PASSWORD);
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
