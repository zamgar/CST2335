package com.mycompany.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Toolbar setup by Android Studio */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Instantiate SharedPreferences and Editor
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //References the email text field. Set to final because it is accessed in inner clas (login button's on click listener)
        final EditText editText = (EditText)findViewById(R.id.login_emailTextField);

        editor.putString("Default Email", "email@domain.com"); //The initial default email. Overwritten when login button is pressed
        editor.commit();

        Button loginButton = (Button)findViewById(R.id.login_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("Default Email", editText.getText().toString()); //New default email is saved to sharedPrefs
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);//Start_Activity is called
                startActivity(intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult()");
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

        EditText editText = (EditText)findViewById(R.id.login_emailTextField);
        editText.setText(sharedPref.getString("Default Email", "email@domain.com"));

    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
