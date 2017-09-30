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
import com.a2pt.whatsnext.models.User;
import com.a2pt.whatsnext.services.DatabaseHelper;
import com.a2pt.whatsnext.services.ITSdbManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    ITSdbManager ITSdb;

    DatabaseHelper databaseHelper;

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
        insertData();


        //=========================================================================================

        SharedPreferences getState = getSharedPreferences("State", MODE_PRIVATE);

        if (getState.getString("loggedIn", "").toString().equals("loggedIn"))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
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

    public void insertData(){
        //Gonna use Dummy Variables here but we will have to set up a fake acounts from Textfile or List or something

        User user = new User("s215006941", "Carl Meyer", "s215006941@nmmu.ac.za", "abc123", "student", "BSc Computer Science");
        ITSdb.insertData(user);

        user = new User("s215144988", "Gerrit Naude", "ss215144988@nmmu.ac.za", "def456", "student", "BSc Information Systems");
        ITSdb.insertData(user);

        user = new User("Vogts.Dieter", "Dieter Vogts", "Vogts.Dieter@nmmu.ac.za", "wrap2017", "lecturer", "wrap301, wrap302, wrl301");
        ITSdb.insertData(user);

    }






        /* Just Commenting out to Test out my code without conflicting


        initialise();

        SharedPreferences getState = getSharedPreferences("State", MODE_PRIVATE);

        if (getState.getString("loggedIn", "").toString().equals("loggedIn"))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyDetails();
            }
        });


    }

    private void verifyDetails() {

        String password = txtPassword.getText().toString();
        String loginName = txtUserName.getText().toString();

        //Checks to see if the user has entered a username
        if(loginName == null)
        {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_LONG).show();
            return;
        }

        //Checks to see if the user has entered a password
        if (password == null)
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password == null && loginName == null)
        {
            Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_LONG).show();
            return;
        }

        //Checks the database if the user exists
        if(databaseHelper.checkUserCredentials(loginName, password) && loginName != null & password != null)
        {
            //if the user exists then they are taken to the main page
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("type", databaseHelper.getUserType(loginName));
            startActivity(intent);
        }
        else
        {
            //if they do not exit or typed an incorrect password/username then they must enter details
            Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
            txtPassword.setText(null);
            txtUserName.setText(null);
        }

    }

    private void initialise() {
        //Initialise TextFields


        //Initialise Button


        //Initialise helper class
        databaseHelper = new DatabaseHelper(LoginActivity.this);
        //databaseHelper.insertData();
    }

    */



}

