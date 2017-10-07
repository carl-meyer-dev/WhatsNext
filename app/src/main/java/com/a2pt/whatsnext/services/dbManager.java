package com.a2pt.whatsnext.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import com.a2pt.whatsnext.models.Activity;
import com.a2pt.whatsnext.models.Module;
import com.a2pt.whatsnext.models.Session;
import com.a2pt.whatsnext.models.Teaches;
import com.a2pt.whatsnext.models.User;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Carl on 2017-10-04.
 */

public class dbManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "local.db";

    //Creating the User table
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USEREMAIL = "user_email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TYPE_OF_USER = "type_of_user";
    private static final String KEY_COURSEINFO = "course_info";
    private static final String KEY_MODULE_INFO = "course_info";

    //Creating the ACTIVITY Table
    private static final String TABLE_ACTIVITY = "activity";
    private static final String KEY_ACT_MOD_ID = "mod_id";
    private static final String KEY_ACT_ACT_TYPE = "act_type";
    private static final String KEY_ACT_TITLE = "title";
    private static final String KEY_ACT_DUE_DATE = "assignment_due_date";
    private static final String KEY_ACT_SUBMISSION_TIME = "assignment_submission_time";
    private static final String KEY_ACT_STATUS = "assignment_status";
    private static final String KEY_ACT_TEST_NAME = "test_name";
    private static final String KEY_ACT_TEST_TIME = "test_time";
    private static final String KEY_ACT_TEST_DATE = "test_date";
    private static final String KEY_ACT_LECTURE_VENUE = "lecture_venue";
    private static final String KEY_ACT_SESSION_ID = "session_id";
    private static final String KEY_ACT_LECTURE_DUPLICATE = "duplicate_lecture";

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
                + KEY_ACT_MOD_ID + " TEXT PRIMARY KEY,"
                + KEY_ACT_ACT_TYPE + " TEXT,"
                + KEY_ACT_TITLE + " TEXT,"
                + KEY_ACT_DUE_DATE + " DATE,"
                + KEY_ACT_SUBMISSION_TIME + " DATE,"
                + KEY_ACT_STATUS + " boolean,"
                + KEY_ACT_TEST_NAME + " TEXT,"
                + KEY_ACT_TEST_TIME + " TIME,"
                + KEY_ACT_TEST_DATE + " DATE,"
                + KEY_ACT_LECTURE_VENUE + " TEXT,"
                + KEY_ACT_SESSION_ID + " INTEGER,"
                + KEY_ACT_LECTURE_DUPLICATE + " boolean,"
                + " FOREIGN KEY(" + KEY_ACT_SESSION_ID + ") REFERENCES " + TABLE_SESSION + "("+KEY_SESSION_SESSION_ID+"))";

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

        values.put(KEY_ID, user.getUserID());
        values.put(KEY_USERNAME, user.getUserName());
        values.put(KEY_USEREMAIL, user.getUserEmail());
        values.put(KEY_PASSWORD, user.getUserPassword());
        values.put(KEY_TYPE_OF_USER, user.getUserType());
        values.put(KEY_COURSEINFO, user.getCourseInfo());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void insertActivity(Activity activity)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ACT_MOD_ID, activity.getModID());
        values.put(KEY_ACT_ACT_TYPE, activity.getActType().toString());
        values.put(KEY_ACT_TITLE, activity.getAssignmentTitle());
        values.put(KEY_ACT_DUE_DATE, activity.getAssignmentDueDate().toString());
        values.put(KEY_ACT_SUBMISSION_TIME, activity.getAssignmentDueTime().toString());
        values.put(KEY_ACT_STATUS, activity.getAssignmentStatus().toString());
        values.put(KEY_ACT_TEST_NAME, activity.getTestDescriiption());
        values.put(KEY_ACT_TEST_TIME, activity.getTestTime().toString());
        values.put(KEY_ACT_LECTURE_VENUE, activity.getTestVenue());
        values.put(KEY_ACT_SESSION_ID, activity.getSessionID());

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

    //Takes in a module code and the itsdatabase
    //traverses throughout the itsdatabase looking for assignments matching the module
    //adds the module into the database
    public void addAssignment(String modCode, ITSdbManager itsDatabase)
    {
        String type = "ASSIGNMENT";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
            + KEY_ACT_MOD_ID + " = ?";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;

        try
        {
            cursor = dbToCheck.rawQuery(query, values);

            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TITLE));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_DUE_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                String status = cursor.getString(cursor.getColumnIndex(KEY_ACT_STATUS));

                DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTimeFormatter formatterTime = DateTimeFormat.forPattern("HH:mm:ss");

                LocalTime time = formatterTime.parseLocalTime(submissionTime);
                LocalDate date = formatterDate.parseLocalDate(dueDate);

                Activity activity = new Activity(id, Activity.Activity_Type.valueOf(typeOfActivity), title, date,time, Activity.Assignment_Status.valueOf(status));

                insertActivity(activity);
            }while(cursor.moveToNext());

            cursor.close();
            dbToCheck.close();
        }catch (Exception e)
        {
            System.out.println("Error in adding the assignment");
        }
        finally {
            cursor.close();
            dbToCheck.close();
        }
    }

    public void addTest(String modCode, ITSdbManager itsDatabase)
    {
        String type = "TEST";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
                + KEY_ACT_MOD_ID + " = ?";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;

        try
        {
            cursor = dbToCheck.rawQuery(query, values);

            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_NAME));
                String dueDate = cursor.getString(cursor.getColumnIndex(KEY_ACT_TEST_DATE));
                String submissionTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SUBMISSION_TIME));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_VENUE));

                DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTimeFormatter formatterTime = DateTimeFormat.forPattern("HH:mm:ss");

                LocalTime time = formatterTime.parseLocalTime(submissionTime);
                LocalDate date = formatterDate.parseLocalDate(dueDate);

                Activity activity = new Activity(id, Activity.Activity_Type.valueOf(typeOfActivity), title, date,time, venue);

                insertActivity(activity);
            }while(cursor.moveToNext());

            cursor.close();
            dbToCheck.close();
        }catch (Exception e)
        {
            System.out.println("Error in adding the test");
        }
        finally {
            cursor.close();
            dbToCheck.close();
        }
    }

    public void addLecture(String modCode, ITSdbManager itsDatabase)
    {
        String type = "LECTURE";
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE " + KEY_ACT_ACT_TYPE + " = ? AND "
                + KEY_ACT_MOD_ID + " = ?";

        String[] values = { type, modCode};
        SQLiteDatabase dbToCheck = itsDatabase.getReadableDatabase();
        Cursor cursor = null;
        Cursor cursorToCheckSession = null;

        try
        {
            cursor = dbToCheck.rawQuery(query, values);

            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ACT_MOD_ID));
                String typeOfActivity = cursor.getString(cursor.getColumnIndex(KEY_ACT_ACT_TYPE));
                String venue = cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_VENUE));
                String lectureTime = cursor.getString(cursor.getColumnIndex(KEY_ACT_SESSION_ID));
                boolean duplicate = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_ACT_LECTURE_DUPLICATE)));

                //Adding query for checking the session
                String queryToCheck = "SELECT " + KEY_SESSION_SESSION_START + " FROM " + TABLE_SESSION + " WHERE " + KEY_SESSION_SESSION_ID + " = ?";
                String[] args = {lectureTime};

                cursorToCheckSession = dbToCheck.rawQuery(queryToCheck, args);

                if(cursorToCheckSession != null)
                {
                    cursorToCheckSession.moveToFirst();
                }

                String timeLecture = cursorToCheckSession.getString(0);

                DateTimeFormatter formatterTime = DateTimeFormat.forPattern("HH:mm:ss");
                LocalTime time = formatterTime.parseLocalTime(timeLecture);

                Activity activity = new Activity(id, Activity.Activity_Type.valueOf(typeOfActivity), venue, time, duplicate);

                insertActivity(activity);
            }while(cursor.moveToNext());

            cursor.close();
            cursorToCheckSession.close();
            dbToCheck.close();
        }catch (Exception e)
        {
            System.out.println("Error in adding the lecture");
        }
        finally {
            cursor.close();
            cursorToCheckSession.close();
            dbToCheck.close();
        }
    }
}