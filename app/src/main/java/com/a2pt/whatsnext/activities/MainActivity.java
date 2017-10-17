package com.a2pt.whatsnext.activities;


import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.a2pt.whatsnext.fragments.HomeFragment;
import com.a2pt.whatsnext.fragments.MaintainAssignmentFragment;
import com.a2pt.whatsnext.fragments.MaintainLectureTimesFragment;
import com.a2pt.whatsnext.fragments.MaintainTestFragment;
import com.a2pt.whatsnext.R;
import com.a2pt.whatsnext.fragments.SendEmailFragment;
import com.a2pt.whatsnext.fragments.TimetableFragment;
import com.a2pt.whatsnext.fragments.UpcomingEventsFragment;
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.ITSdbManager;
import com.a2pt.whatsnext.services.dbManager;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    //temporary variable for menu purposes
    /*
    The following variable is a temporary variable to help with Testing and Demo Purposes.
    The int usertype stores an integer according to what user is logged in.
    The Default user is 0 - Student and once Switch User is pressed in the toolbar Menu it switches between 1 and 0.
     */
    int userType = 0;
    User user;
    dbManager localDB;
    ITSdbManager ITSdb;

    //Make View Variables
    //==============================================================================================
    NavigationView navigationView;

    //TextViews
    TextView tvNavHeaderName;
    TextView tvNavHeaderUsername;
    TextView tvNavHeaderDegree;

    //==============================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Generated OnCreate Code From Nav Drawer
        //==========================================================================================
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Once the user has logged into the application then the system should remain logged in
        setLoginStatus();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //==========================================================================================
        //OnCreate Custom Code:
        //==========================================================================================
        //get reference to local database
        localDB = new dbManager(this);
        ITSdb = new ITSdbManager(this);

        //Get Bundle, Set Nav Menu and User Info
        //Bundle bundle = getIntent().getExtras();
        //user = (User)bundle.getSerializable("user");

        // Instead of logging in with bundle info, get the user from the Local Database
        user = localDB.getUser();


        //==========================================================================================

        //Handle Fragments and FragmentManager
        fragmentManager = getSupportFragmentManager(); //get the fragment manager, using supportFragment Manager to support earlier versions of Android
        //Get the Frame Layout that will hold the Fragments
       Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        //if no fragment in the Start of the app then set current fragment to HomeFragment
        if(fragment == null){
            fragment = new HomeFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        //==========================================================================================

    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavDrawer();
    }

    //Sets the sharedPreferences of the application.
    //This ensures that if a user was previously logged into the application they will not have
    //reenter their credentials and just move straight into the main menu
    private void setLoginStatus() {
        SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loggedIn", "true");
        editor.apply();
    }

    /**
     * Generated code for when Back button is Pressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Generated code for when Options item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_view_timetable ){
            TimetableFragment timetableFragment = new TimetableFragment(); //get reference to Timetable Fragment
            //Begin transaction with fragment manager, replace the fragment_container with TimeTable Fragment
            //We place the new Fragment on the backstack so that when the Back button is pressed it will go back to the Home Fragment

            if(fragmentManager.getBackStackEntryCount() == 0){
                //if Home Screen is the Only Fragment on the BackStack then simply add the new Fragment to the backstack
                fragmentManager.beginTransaction().replace(R.id.fragment_container, timetableFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                //if another Fragment is being shown on top of Home Screen, then Remove it first before adding new Fragment to Backstack
                fragmentManager.popBackStack(); //pop unwanted fragment form backstack
                fragmentManager.beginTransaction().replace(R.id.fragment_container, timetableFragment).addToBackStack(null).commit(); //show and add new Fragment
            }


        } else if (id == R.id.nav_view_upcoming_events) {
            //similarly replace the fragment_container with Upcoming Events Fragment
            UpcomingEventsFragment upcomingEventsFragment = new UpcomingEventsFragment();

            if(fragmentManager.getBackStackEntryCount() == 0){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, upcomingEventsFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, upcomingEventsFragment).addToBackStack(null).commit();
            }


        } else if (id == R.id.nav_maintain_timetable) {
            //TODO: Resynchronize Time Table
            //Make Popup Dialog "Do you want to resynchronise timetable?"
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes Button Clicked
                            //Run SQL Query to refresh time table
                            localDB.clearLocalAssignmentsTests();
                            ITSdb.refreshLocalDB(user, localDB, ITSdb);
                            Toast toast = Toast.makeText(MainActivity.this, "Refreshed Local DB", Toast.LENGTH_LONG);
                            toast.show();
                            //TODO: need to refresh Home Fragment
                            Fragment homeFrag = new HomeFragment(); //get new Home Fragment
                            fragmentManager.beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit(); //Removes all fragments from container
                            fragmentManager.beginTransaction().add(R.id.fragment_container, homeFrag).commit(); //add new Home Fragment to container

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No Button Clicked
                            //DO Nothing
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme); //This changes the YES/NO Button's colours so it is visible
            //Code below Shows the PopupDialog and calls the dialogClickedListener Events
            builder.setMessage("Are you sure you want to resynchronize databse?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();





        } else if (id == R.id.nav_maintain_lecture_times) {
            //similarly replace the fragment_container with Maintain Lecture Times Fragment
            MaintainLectureTimesFragment maintainLectureTimesFragment = new MaintainLectureTimesFragment();

            if(fragmentManager.getBackStackEntryCount() == 0){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainLectureTimesFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainLectureTimesFragment).addToBackStack(null).commit();
            }


        } else if (id == R.id.nav_maintain_schedule_event) {
            //Create an intent that will Open the Default Calendar app on the Phone
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();

            //Sets the opening of the calendar based on time not event
            builder.appendPath("time");
            //Sets the calendar on the right time
            ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
            //Create new intent and start activity
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
            startActivity(intent);


        }else if (id == R.id.nav_maintain_assignments) {
            //similarly replace the fragment_container with Maintain Assignments Fragment
            MaintainAssignmentFragment maintainAssignmentFragment = new MaintainAssignmentFragment();
            if(fragmentManager.getBackStackEntryCount() == 0){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainAssignmentFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainAssignmentFragment).addToBackStack(null).commit();
            }


        }else if (id == R.id.nav_maintain_tests) {
            //similarly replace the fragment_container with Maintain Tests Fragment
            MaintainTestFragment maintainTestFragment = new MaintainTestFragment();
            if(fragmentManager.getBackStackEntryCount() == 0){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainTestFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, maintainTestFragment).addToBackStack(null).commit();
            }


        }else if (id == R.id.nav_maintain_send_email) {
            //similarly replace the fragment_container with Send Email Fragment
            SendEmailFragment sendEmailFragment = new SendEmailFragment();
            if(fragmentManager.getBackStackEntryCount() == 0){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, sendEmailFragment).addToBackStack(null).commit();
            }else if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, sendEmailFragment).addToBackStack(null).commit();
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavDrawer(){
        //Clear the current Drawer Menu
        navigationView.getMenu().clear();

        if(user.getUserType().equalsIgnoreCase("student")) {
            System.out.println("LOGGED IN AS STUDENT");
            //inflate the new drawer menu (lecturer Drawer Menu)
            navigationView.inflateMenu(R.menu.activity_main_drawer_student);
            userType = 0;
        }else {
            System.out.println("LOGGED IN AS LECTURER");
            //inflate the new drawer menu (lecturer Drawer Menu)
            navigationView.inflateMenu(R.menu.activity_main_drawer_lecturer);
            userType = 1;
        }

        View header = navigationView.getHeaderView(0);

        tvNavHeaderDegree = (TextView)header.findViewById(R.id.tvNavHeaderDegree);
        tvNavHeaderName = (TextView)header.findViewById(R.id.tvNavHeaderName);
        tvNavHeaderUsername = (TextView)header.findViewById(R.id.tvNavHeaderUsername);

        tvNavHeaderName.setText(user.getUserName());
        tvNavHeaderUsername.setText(user.getUserEmail());
        tvNavHeaderDegree.setText(user.getCourseInfo());


    }

    public void SwitchUser(MenuItem item) {
        //TODO: This will be set Dynamically according to user log in

        if(userType == 0){
            //Clear the current Drawer Menu
            navigationView.getMenu().clear();
            //inflate the new drawer menu (lecturer Drawer Menu)
            navigationView.inflateMenu(R.menu.activity_main_drawer_lecturer);
            userType = 1;
            //remove all fragments from fragmentStack to go back to HomeFragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //set the NavHeader Textviews text (this will later be done when Logged in. This is only for testing purposes)
            tvNavHeaderDegree = (TextView)findViewById(R.id.tvNavHeaderDegree);
            tvNavHeaderName = (TextView)findViewById(R.id.tvNavHeaderName);
            tvNavHeaderUsername = (TextView)findViewById(R.id.tvNavHeaderUsername);

            tvNavHeaderName.setText("Dieter Vogts");
            tvNavHeaderUsername.setText("Dieter.Vogts@nmu.ac.za");
            tvNavHeaderDegree.setText("WRAP301, WRAP302, WRA301");


        }else {
            //Clear the current Drawer Menu
            navigationView.getMenu().clear();
            //inflate the new drawer menu (lecturer Drawer Menu)
            navigationView.inflateMenu(R.menu.activity_main_drawer_student);
            userType = 0;
            //remove all fragments from fragmentStack to go back to HomeFragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //set the NavHeader Textviews text (this will later be done when Logged in. This is only for testing purposes)
            tvNavHeaderDegree = (TextView)findViewById(R.id.tvNavHeaderDegree);
            tvNavHeaderName = (TextView)findViewById(R.id.tvNavHeaderName);
            tvNavHeaderUsername = (TextView)findViewById(R.id.tvNavHeaderUsername);

            tvNavHeaderName.setText("Carl Meyer");
            tvNavHeaderUsername.setText("s215006941@nmmu.ac.za");
            tvNavHeaderDegree.setText("BSc Computer Science");

        }

    }

    /**
     * This will return the currently logged in user. This is so that Fragments can get hold of List of Modules fot the spinners
     * @return
     */
    public User getCurUser(){
        return user;
    }

    public void LogOut(MenuItem item) {
        //get preferences file
        SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);
        //Set Login status to false
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("loggedIn");
        editor.commit();

        //Clear Databse
        localDB.clearLocalDB();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
