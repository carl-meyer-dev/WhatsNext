package com.a2pt.whatsnext.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.Module;
import com.a2pt.whatsnext.models.Session;
import com.a2pt.whatsnext.models.Teaches;
import com.a2pt.whatsnext.models.User;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Carl on 2017-10-04.
 */

public class dbManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "local.db";

    //Creating the User table
    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "user_id";
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
    private static final String KEY_ACT_TYPE_OF_LECTURE = "type_of_lecture";
    private static final String KEY_ACT_START_DATE = "lecture_start_date";
    private static final String KEY_ACT_END_DATE = "lecture_end_date";

    //Creating the Module Table
    private static final String TABLE_MODULE = "modules";
    private static final String KEY_MODULE_MOD_ID = "mod_id";
    private static final String KEY_MODULE_MOD_NAME = "mod_name";

    //Creating the Teaches Table
    private static final String TABLE_TEACHES = "teaches";
    private static final String KEY_TEACHES_SESSION_ID = "session_id";
    private static final String KEY_TEACHES_ID = "teaches_id"; //This is the same as the userid
    private static final String KEY_TEACHES_MOD_ID = "mod_id";

    //Creating the Session Table
    private static final String TABLE_SESSION = "sessions";
    private static final String KEY_SESSION_SESSION_ID = "session_id";
    private static final String KEY_SESSION_SESSION_START = "session_start";
    private static final String KEY_SESSION_SESSION_END = "session_end";
    private static final String KEY_SESSION_DAY_OF_WEEK = "session_day_of_week";



    public dbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Creating Tables

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + KEY_USER_ID + " TEXT PRIMARY KEY,"
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
                + "FOREIGN KEY(" + KEY_TEACHES_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_ID + "), "
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
                + KEY_ACT_LECTURE_START_TIME + " DATE,"
                + KEY_ACT_LECTURE_DAY_OF_WEEK + " TEXT,"
                + KEY_ACT_LECTURE_DUPLICATE + " INTEGER,"
                + KEY_ACT_TYPE_OF_LECTURE + " TEXT, "
                + KEY_ACT_START_DATE + " DATE, "
                + KEY_ACT_END_DATE + " DATE"
                + ")";


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
        //TODO: Here we can have a loop inserting all Dummy Accounts

        values.put(KEY_USER_ID, user.getUserID());
        values.put(KEY_USERNAME, user.getUserName());
        values.put(KEY_USEREMAIL, user.getUserEmail());
        values.put(KEY_PASSWORD, user.getUserPassword());
        values.put(KEY_TYPE_OF_USER, user.getUserType());
        values.put(KEY_COURSEINFO, user.getCourseInfo());
        values.put(KEY_MODULE_INFO, user.getModuleString());


        db.insert(TABLE_USERS, null, values);
        db.close();
    }


    private void insertLecture(Activity activity){
        System.out.println("DEBUG ITSdbManager: INSERT LECTURE");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType());
        values.put(KEY_ACT_VENUE, activity.getLectureVenue());
        values.put(KEY_ACT_LECTURE_START_TIME, activity.getLecStartTime().toString().substring(0,5));
        values.put(KEY_ACT_LECTURE_DAY_OF_WEEK, activity.getDayOfWeek());
        values.put(KEY_ACT_LECTURE_DUPLICATE, activity.getIsDuplicate());
        values.put(KEY_ACT_TYPE_OF_LECTURE, activity.getTypeOfLecture());
        values.put(KEY_ACT_START_DATE, activity.getStartDate().toString());
        values.put(KEY_ACT_END_DATE, activity.getEndDate().toString());

        db.insert(TABLE_ACTIVITY, null, values);
        db.close();

    }

    public void insertAssignment(Activity activity){
        System.out.println("DEBUG ITSdbManager: INSERT ASSIGNMENT");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        System.out.println(activity.getAssignmentDueDate().toString());

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


    public void updateAssignment(Activity assignment, int actID){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_ACTIVITY + " SET " + KEY_ACT_TITLE + " = '" + assignment.getAssignmentTitle()
                + "' , " + KEY_ACT_DUE_DATE + " = '" + assignment.getAssignmentDueDate() + "' , " + KEY_ACT_SUBMISSION_TIME + " = '" + assignment.getAssignmentDueTime()
                + "' WHERE " + KEY_ACT_ID + " = " + actID;


        db.execSQL(query);
    }

    public void updateTest(Activity test, int actID){

        SQLiteDatabase db = this.getWritableDatabase();


        String query = "UPDATE " + TABLE_ACTIVITY + " SET " + KEY_ACT_VENUE + " = '" + test.getTestVenue()
                + "' , " + KEY_ACT_TEST_DATE + " = '" + test.getTestDate() + "' , " + KEY_ACT_TEST_TIME + " = '" + test.getTestTime()
                + "' WHERE " + KEY_ACT_ID + " = " + actID;


        db.execSQL(query);
    }

    public void deleteActivity(int actID){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_ACTIVITY +" WHERE " + KEY_ACT_ID +" = " + actID;
        db.execSQL(query);

    }

    //Get Activity ID
    public int getLectureActID(Activity lecture)
    {
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT " + KEY_ACT_ID
                + " FROM " + TABLE_ACTIVITY
                + " WHERE " + KEY_ACT_ACT_TYPE + " = '" + lecture.getActType()
                + "' AND " + KEY_ACT_LECTURE_START_TIME +" = '" + lecture.getLecStartTime().toString().substring(0,5)
                + "' AND " + KEY_ACT_LECTURE_DAY_OF_WEEK + " = '" + lecture.getDayOfWeek()
                + "' AND " + KEY_ACT_MOD_ID + " = '" + lecture.getModID() + "' AND " + KEY_ACT_VENUE + " = '" + lecture.getLectureVenue() + "'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){
            int ActID = cursor.getInt(cursor.getColumnIndex(KEY_ACT_ID));

            return ActID;
        }

        return -1;

    }

    public User getUser(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,                                                                                            //Table Name - SELECT FROM TABLE
                new String[]{KEY_USER_ID, KEY_USERNAME, KEY_USEREMAIL, KEY_PASSWORD, KEY_TYPE_OF_USER, KEY_COURSEINFO, KEY_MODULE_INFO},      //All the Fields that you watn to capture
                KEY_USER_ID + "=?",                                                                                    //Where Username = ?
                new String[]{ String.valueOf(username)},                                                                // Where ? = String.Value(What you are looking for)
                null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

        cursor.close();
        return user;
    }

    public User getUser(){ //Since the local DB will only have 1 user in Database we can just get the first item in the SELECT * statement

        String query = "SELECT * FROM " + TABLE_USERS;

        Cursor cursor = null;
        cursor = this.getReadableDatabase().rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            String userID = cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            String userEmail = cursor.getString(cursor.getColumnIndex(KEY_USEREMAIL));
            String userPassword = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            String userType = cursor.getString(cursor.getColumnIndex(KEY_TYPE_OF_USER));
            String courseInfo = cursor.getString(cursor.getColumnIndex(KEY_COURSEINFO));
            String moduleString = cursor.getString(cursor.getColumnIndex(KEY_MODULE_INFO));
            System.out.println("DEBUG LOGGIN INTO MAIN ACTIVITY: MODULE INFO = " + moduleString);

            cursor.close();
            return new User(userID,userName,userEmail,userPassword,userType,courseInfo,moduleString);


        }else {

            return new User("","","","","","","?,?,?,?");

        }

    }

    //Takes in a module code and the itsdatabase
    //traverses throughout the itsdatabase looking for assignments matching the module
    //adds the module into the database
    public void addAssignment(String modCode, ITSdbManager itsDatabase)
    {

        System.out.println(modCode);
        String type = "assignment";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
            + KEY_ACT_MOD_ID + " = ?";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;


        cursor = dbToCheck.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {


                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(id, typeOfActivity, title, dDate, dTime, status);

                insertAssignment(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }
    }

    public void addTest(String modCode, ITSdbManager itsDatabase)
    {
        String type = "test";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
                + KEY_ACT_MOD_ID + " = ?";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;

        cursor = dbToCheck.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(id, typeOfActivity, title, tDate, startTime, venue);

                insertTest(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

    }

    public void addLecture(String modCode, ITSdbManager itsDatabase)
    {
        System.out.println("DEBUG in dbManager. Adding Lecture for module = " + modCode);
        String type = "lecture";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
                + KEY_ACT_MOD_ID + " = ? AND " + KEY_ACT_START_DATE + " <= DATE() AND " + KEY_ACT_END_DATE + " >= DATE()";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;


     //   try
       // {
            cursor = dbToCheck.rawQuery(query, values);

            if(cursor != null &&  cursor.moveToFirst()) {


                do {
                    String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                    String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                    String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));
                    String lectureTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_START_TIME));
                    String lectureDayOfWeek = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DAY_OF_WEEK));
                    int duplicate = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DUPLICATE)));
                    String typeOfLecture = cursor.getString(cursor.getColumnIndex(KEY_ACT_TYPE_OF_LECTURE));
                    String startDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_START_DATE));
                    String endDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_END_DATE));

                    System.out.println(startDateString);
                    System.out.println(endDateString);
                    //Note that in the Database the time is stores like this - 17:45
                    //Note that Local Time takes in (int Hours, int Minutes)_ as parameters

                    String[] temptime = lectureTime.split(":");  //Split the Time string into 17 abd 45
                    int hours = Integer.parseInt(temptime[0]); //set the hours integer
                    int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                    LocalTime startTime = new LocalTime(hours, minutes);

                    String[] temdateStart = startDateString.split("-"); //split date up into 2017, 10, 23
                    int yearStart = Integer.parseInt(temdateStart[0]);  //save year as 2017
                    int monthStart = Integer.parseInt(temdateStart[1]);  //save month as 10
                    int dayStart = Integer.parseInt(temdateStart[2]); //save day as 23
                    LocalDate startDate = new LocalDate(yearStart, monthStart, dayStart);  //create new local date

                    String[] temdateEnd = endDateString.split("-"); //split date up into 2017, 10, 23
                    int yearEnd = Integer.parseInt(temdateEnd[0]);  //save year as 2017
                    int monthEnd = Integer.parseInt(temdateEnd[1]);  //save month as 10
                    int dayEnd = Integer.parseInt(temdateEnd[2]); //save day as 23
                    LocalDate endDate = new LocalDate(yearEnd, monthEnd, dayEnd);  //create new local date

                    Activity activity = new Activity(id, typeOfActivity, venue, startTime, lectureDayOfWeek, duplicate, typeOfLecture, startDate, endDate);

                    insertLecture(activity);

                } while (cursor.moveToNext());

                cursor.close();


            }

    }

    public List<Activity> getLecturesSpecificDay(String dayOfWeek){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> lectures = new ArrayList<>();

        Cursor cursor = null;

        String query = "SELECT * FROM " + TABLE_ACTIVITY +
                " WHERE " + KEY_ACT_ACT_TYPE +" = ? AND " + KEY_ACT_LECTURE_DAY_OF_WEEK + " = ? ORDER BY " + KEY_ACT_LECTURE_START_TIME + " ASC";

        String[] values = { "lecture", dayOfWeek};
        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()){
            System.out.println("DEBUG getLecturesSpecificDay: Number of entries for Monday is: " + cursor.getCount());
            do{

                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));
                String lectureTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_START_TIME));
                String lectureDayOfWeek = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DAY_OF_WEEK));
                int duplicate = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DUPLICATE)));
                String typeOfLecture = cursor.getString(cursor.getColumnIndex(KEY_ACT_TYPE_OF_LECTURE));
                String startDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_START_DATE));
                String endDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_END_DATE));


                //Note that in the Database the time is stores like this - 17:45
                //Note that Local Time takes in (int Hours, int Minutes)_ as parameters

                String[] temptime = lectureTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                String[] temdateStart = startDateString.split("-"); //split date up into 2017, 10, 23
                int yearStart = Integer.parseInt(temdateStart[0]);  //save year as 2017
                int monthStart = Integer.parseInt(temdateStart[1]);  //save month as 10
                int dayStart = Integer.parseInt(temdateStart[2]); //save day as 23
                LocalDate startDate = new LocalDate(yearStart, monthStart, dayStart);  //create new local date

                String[] temdateEnd = endDateString.split("-"); //split date up into 2017, 10, 23
                int yearEnd = Integer.parseInt(temdateEnd[0]);  //save year as 2017
                int monthEnd = Integer.parseInt(temdateEnd[1]);  //save month as 10
                int dayEnd = Integer.parseInt(temdateEnd[2]); //save day as 23
                LocalDate endDate = new LocalDate(yearEnd, monthEnd, dayEnd);  //create new local date

                Activity activity = new Activity(id, typeOfActivity, venue, startTime, lectureDayOfWeek, duplicate, typeOfLecture, startDate, endDate);
                lectures.add(activity);

            }while (cursor.moveToNext());

        }

        return lectures;

    }

    public List<Activity> getUpcomingAssignments(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> assignments = new ArrayList<>();
        String type = "assignment";
/*
        final Calendar c = Calendar.getInstance();
        int yearC = c.get(Calendar.YEAR);
        int monthC = c.get(Calendar.MONTH);
        int dayC = c.get(Calendar.DAY_OF_MONTH);
        String date = dayC + "-" + monthC + "-"+yearC;
        */


        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND " + KEY_ACT_DUE_DATE + " >= DATE() ORDER BY " + KEY_ACT_DUE_DATE + ", " +  KEY_ACT_SUBMISSION_TIME+ " ASC";

        //String[] values = { type, date};
        String[] values = { type};

        Cursor cursor = null;


        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {


                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(id, typeOfActivity, title, dDate, dTime, status);
                assignments.add(activity);


            } while (cursor.moveToNext());

            cursor.close();

        }
        return assignments;
    }


    public List<Activity> getAllAssignments(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> assignments = new ArrayList<>();
        String type = "assignment";
/*
        final Calendar c = Calendar.getInstance();
        int yearC = c.get(Calendar.YEAR);
        int monthC = c.get(Calendar.MONTH);
        int dayC = c.get(Calendar.DAY_OF_MONTH);
        String date = dayC + "-" + monthC + "-"+yearC;
        */


        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? ORDER BY " + KEY_ACT_DUE_DATE + ", " +  KEY_ACT_SUBMISSION_TIME+ " ASC";

        //String[] values = { type, date};
        String[] values = { type};

        Cursor cursor = null;


        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {


                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(id, typeOfActivity, title, dDate, dTime, status);
                assignments.add(activity);


            } while (cursor.moveToNext());

            cursor.close();

        }
        return assignments;
    }

    public List<Activity> getUpcomingTests(){
        String type = "test";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND " + KEY_ACT_TEST_DATE + " >= DATE() ORDER BY " +  KEY_ACT_TEST_DATE+ ", " + KEY_ACT_TEST_TIME + " ASC";
        List<Activity> tests = new ArrayList<>();
/*      INSTEAD USE DATE() FUNCTION IN SQL

        final Calendar c = Calendar.getInstance();
        int yearC = c.get(Calendar.YEAR);
        int monthC = c.get(Calendar.MONTH);
        int dayC = c.get(Calendar.DAY_OF_MONTH);
        String date = dayC + "-" + monthC + "-"+yearC;

        String[] values = { type, date};
        */
        String[] values = { type};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(id, typeOfActivity, title, tDate, startTime, venue);

                tests.add(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

        return tests;
    }

    public List<Activity> getAllTests(){
        String type = "test";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? ORDER BY " +  KEY_ACT_TEST_DATE+ ", " + KEY_ACT_TEST_TIME + " ASC";
        List<Activity> tests = new ArrayList<>();
/*      INSTEAD USE DATE() FUNCTION IN SQL

        final Calendar c = Calendar.getInstance();
        int yearC = c.get(Calendar.YEAR);
        int monthC = c.get(Calendar.MONTH);
        int dayC = c.get(Calendar.DAY_OF_MONTH);
        String date = dayC + "-" + monthC + "-"+yearC;

        String[] values = { type, date};
        */
        String[] values = { type};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(id, typeOfActivity, title, tDate, startTime, venue);

                tests.add(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

        return tests;
    }

    public List<Activity> getTodaysAssignments(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> assignments = new ArrayList<>();
        String type = "assignment";

        //TODO: Filter out assignments that have due dates that is already over

        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND " + KEY_ACT_DUE_DATE + " = DATE() ORDER BY " + KEY_ACT_SUBMISSION_TIME + " ASC";

        //String[] values = {type, date};
        String[] values = {type};

        Cursor cursor = null;


        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {


                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(id, typeOfActivity, title, dDate, dTime, status);
                assignments.add(activity);


            } while (cursor.moveToNext());

            cursor.close();

        }
        return assignments;

    }

    public List<Activity> getTodayTests(){
        String type = "test";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND " + KEY_ACT_TEST_DATE + " = DATE() ORDER BY " + KEY_ACT_TEST_TIME + " ASC";
        List<Activity> tests = new ArrayList<>();
        //String[] values = { type, date};
        String[] values = { type};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(id, typeOfActivity, title, tDate, startTime, venue);

                tests.add(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

        return tests;
    }

    public List<Activity> getAssignmentsByID(String modID){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> assignments = new ArrayList<>();
        String type = "assignment";
        //TODO: Filter out assignments that have due dates that is already over

        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " =? AND " +
                KEY_ACT_MOD_ID + " = ? AND " + KEY_ACT_DUE_DATE + " >= DATE() ORDER BY " + KEY_ACT_DUE_DATE  + ", "+ KEY_ACT_SUBMISSION_TIME + " ASC";

        String[] values = {type, modID};

        Cursor cursor = null;


        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ACT_ID));
                String mod_id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(mod_id, typeOfActivity, title, dDate, dTime, status);
                activity.setActID(id);
                assignments.add(activity);


            } while (cursor.moveToNext());

            cursor.close();

        }
        return assignments;
    }

    public List<Activity> getPastAssignmentsByID(String modID){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> assignments = new ArrayList<>();
        String type = "assignment";
        //TODO: Filter out assignments that have due dates that is already over

        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " =? AND " +
                KEY_ACT_MOD_ID + " = ? AND " + KEY_ACT_DUE_DATE + " < DATE() ORDER BY " + KEY_ACT_DUE_DATE  + ", "+ KEY_ACT_SUBMISSION_TIME + " ASC";

        String[] values = {type, modID};

        Cursor cursor = null;


        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ACT_ID));
                String mod_id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS)));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = dueDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate dDate = new LocalDate(year, month, day);  //create new local date
                System.out.println(dDate.toString());

                String[] temptime = submissionTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime dTime = new LocalTime(hours, minutes);
                System.out.println(dTime.toString());

                Activity activity = new Activity(mod_id, typeOfActivity, title, dDate, dTime, status);
                activity.setActID(id);
                assignments.add(activity);


            } while (cursor.moveToNext());

            cursor.close();

        }
        return assignments;
    }

    public List<Activity> getTestsByID(String modID){
        String type = "test";

        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " =? AND " +
                KEY_ACT_MOD_ID + " = ? AND " + KEY_ACT_TEST_DATE + " >= DATE() ORDER BY " + KEY_ACT_TEST_DATE  + ", "+ KEY_ACT_TEST_TIME + " ASC";

        List<Activity> tests = new ArrayList<>();
        String[] values = { type, modID};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ACT_ID));
                String mod_id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(mod_id, typeOfActivity, title, tDate, startTime, venue);
                activity.setActID(id);
                tests.add(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

        return tests;
    }

    public List<Activity> getPastTestsByID(String modID){
        String type = "test";

        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " =? AND " +
                KEY_ACT_MOD_ID + " = ? AND " + KEY_ACT_TEST_DATE + " < DATE() ORDER BY " + KEY_ACT_TEST_DATE  + ", "+ KEY_ACT_TEST_TIME + " ASC";
        List<Activity> tests = new ArrayList<>();
        String[] values = { type, modID};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()) {


            do {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ACT_ID));
                String mod_id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String testDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String testTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));


                //The Dates in the databse will be like 2017/10/23
                //The TIme in the databse will be like 17:45

                String[] temdate = testDate.split("-"); //split date up into 2017, 10, 23
                int year = Integer.parseInt(temdate[0]);  //save year as 2017
                int month = Integer.parseInt(temdate[1]);  //save month as 10
                int day = Integer.parseInt(temdate[2]); //save day as 23
                LocalDate tDate = new LocalDate(year, month, day);  //create new local date


                String[] temptime = testTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                Activity activity = new Activity(mod_id, typeOfActivity, title, tDate, startTime, venue);
                activity.setActID(id);
                tests.add(activity);
            } while (cursor.moveToNext());

            cursor.close();

        }

        return tests;
    }

    public void clearLocalDB(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_USERS);
        db.execSQL("delete from "+ TABLE_ACTIVITY); //doesnt delete lectures
        db.execSQL("delete from "+ TABLE_MODULE);
        db.execSQL("delete from "+ TABLE_SESSION);
        db.execSQL("delete from "+ TABLE_TEACHES);

    }
    public void clearLocalAssignmentsTests(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_USERS);
        db.execSQL("delete from "+ TABLE_ACTIVITY + " WHERE NOT " + KEY_ACT_ACT_TYPE + " = 'lecture' "); //doesnt delete lectures
        db.execSQL("delete from "+ TABLE_MODULE);
        db.execSQL("delete from "+ TABLE_SESSION);
        db.execSQL("delete from "+ TABLE_TEACHES);

    }

    public List<Activity> getDuplicateLectures(String selectedModule) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Activity> lectures = new ArrayList<>();

        Cursor cursor = null;

        String query = "SELECT * FROM " + TABLE_ACTIVITY +
                " WHERE " + KEY_ACT_ACT_TYPE +" = ? AND " + KEY_ACT_LECTURE_DUPLICATE + " = ? AND " + KEY_ACT_MOD_ID + " = ? ORDER BY " + KEY_ACT_LECTURE_START_TIME + " ASC";

        String[] values = { "lecture", "1", selectedModule};
        cursor = db.rawQuery(query, values);

        if(cursor != null && cursor.moveToFirst()){
            System.out.println("DEBUG Duplicate Lectures is:" + cursor.getCount());
            do{

                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_VENUE));
                String lectureTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_START_TIME));
                String lectureDayOfWeek = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DAY_OF_WEEK));
                int duplicate = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DUPLICATE)));
                String typeOfLecture = cursor.getString(cursor.getColumnIndex(KEY_ACT_TYPE_OF_LECTURE));
                String startDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_START_DATE));
                String endDateString = cursor.getString(cursor.getColumnIndex(KEY_ACT_END_DATE));


                //Note that in the Database the time is stores like this - 17:45
                //Note that Local Time takes in (int Hours, int Minutes)_ as parameters

                String[] temptime = lectureTime.split(":");  //Split the Time string into 17 abd 45
                int hours = Integer.parseInt(temptime[0]); //set the hours integer
                int minutes = Integer.parseInt(temptime[1]); //set the minutes integer
                LocalTime startTime = new LocalTime(hours, minutes);

                String[] temdateStart = startDateString.split("-"); //split date up into 2017, 10, 23
                int yearStart = Integer.parseInt(temdateStart[0]);  //save year as 2017
                int monthStart = Integer.parseInt(temdateStart[1]);  //save month as 10
                int dayStart = Integer.parseInt(temdateStart[2]); //save day as 23
                LocalDate startDate = new LocalDate(yearStart, monthStart, dayStart);  //create new local date

                String[] temdateEnd = endDateString.split("-"); //split date up into 2017, 10, 23
                int yearEnd = Integer.parseInt(temdateEnd[0]);  //save year as 2017
                int monthEnd = Integer.parseInt(temdateEnd[1]);  //save month as 10
                int dayEnd = Integer.parseInt(temdateEnd[2]); //save day as 23
                LocalDate endDate = new LocalDate(yearEnd, monthEnd, dayEnd);  //create new local date

                Activity activity = new Activity(id, typeOfActivity, venue, startTime, lectureDayOfWeek, duplicate, typeOfLecture, startDate, endDate);
                lectures.add(activity);

            }while (cursor.moveToNext());

        }

        return lectures;
    }
}
