package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
//user login
        String emailText = tvEmail.getText().toString();
        String passwordText = tvPassword.getText().toString();

        userSharedStorage = new UserSharedStorage(this);
        User user = userSharedStorage.getUserData();


        //Compare data entered by user on login to registration
        if (user.getEmail().equals(emailText) && user.getPassword().equals(passwordText)) {
            userSharedStorage.setLoggedIn(true);
            Intent intentLogin = new Intent(this, Instructions.class);
            startActivity(intentLogin);
            Toast toast = Toast.makeText(this, "Login Success !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();


        } else {
            tvResult.setTextColor(Color.RED);
            tvResult.setText("Login Failed...\nPlease enter a valid email and password");
        }

    }
}