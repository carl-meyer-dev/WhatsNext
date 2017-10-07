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

import com.a2pt.whatsnext.services.ITSdbManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    ITSdbManager ITSdb;



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

        user = new User("s215144988", "Gerrit Naude", "ss215144988@nmmu.ac.za", "def456", "student", "BSc Information Systems","WRAP302, EBM302, WRUI301, WRB302, WRR301");
        ITSdb.insertData(user);

        user = new User("Vogts.Dieter", "Dieter Vogts", "Vogts.Dieter@nmmu.ac.za", "wrap2017", "lecturer", "Computer Science", "WRAP301,WRAP302,WRA301");
        ITSdb.insertData(user);

    }




}

