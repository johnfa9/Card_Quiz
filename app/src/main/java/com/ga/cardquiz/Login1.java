package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Login1 extends AppCompatActivity {

    EditText tvEmail;
    EditText tvPassword;
    TextView tvResult;
    UserSharedStorage userSharedStorage;  //References shared storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        //set view references
        tvEmail = (EditText) findViewById(R.id.editTextEmailAddressLogin);
        tvPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        tvResult = (TextView) findViewById(R.id.textViewResult);

    }

    public void LoginUser(View view) {

        String emailText= tvEmail.getText().toString();
        String passwordText= tvPassword.getText().toString();

        //Boolean userValidate =ValidateUser();
        userSharedStorage = new UserSharedStorage(this);
        User user = userSharedStorage.getUserData();

        String userNameExists;
        String passwordExists;

        //check i user exists in storage



        //Compare data entered by user on login to registration
        if (user.getEmail().equals(emailText) && user.getPassword().equals(passwordText))
        {
            userSharedStorage.setLoggedIn(true);
            Intent intentLogin = new Intent(this, MainActivity.class);
            startActivity(intentLogin);
            //Add a toast here
            //tvResult.setTextColor(Color.BLUE);
            //tvResult.setText("Login Success...Welcome to Card Qui !");

        }
        else {
            tvResult.setTextColor(Color.RED);
            tvResult.setText("Login Failed...Please enter a valid email and password");
        }

    }

}