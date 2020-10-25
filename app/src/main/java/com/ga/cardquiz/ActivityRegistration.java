package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ActivityRegistration extends AppCompatActivity {

    UserSharedStorage userSharedStorage;  //References shared storage
    User user;

    boolean dateValid= false;  //date is entered by user
    EditText tvFirstName;
    EditText tvtLastName;
    EditText tvDt;
    EditText tvEmail;
    EditText tvPassword;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tvFirstName = (EditText) findViewById(R.id.editTextFirstName);
        tvtLastName = (EditText) findViewById(R.id.editTextLastName);
        tvDt = (EditText) findViewById(R.id.editTextDate);
        tvEmail = (EditText) findViewById(R.id.editTextEmailAddress);
        tvPassword = (EditText) findViewById(R.id.editTextPassword);

    }
    public void SaveRegistration(View view) {
        //get values of views
        String firstname = tvFirstName.getText().toString();
        String lastname = tvtLastName.getText().toString();
        String dt = tvDt.getText().toString();
        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

        //Check if all views have been populated by user

        if (firstname.trim().equalsIgnoreCase("")) {
            tvFirstName.setError("This field can not be blank");
            return;
        }

        if (lastname.trim().equalsIgnoreCase("")) {
            tvtLastName.setError("This field can not be blank");
            return;
        }

        if (dt.toString().trim().equalsIgnoreCase("")) {
            tvDt.setError("This field can not be blank");
            return;
        }

        if (email.trim().equalsIgnoreCase("")) {
            tvtLastName.setError("This field can not be blank");
            return;
        }

        if (password.toString().trim().equalsIgnoreCase("")) {
            tvPassword.setError("This field can not be blank");
            return;
        }
        //Check length of first name
        if (firstname.trim().length() < 3) {
            tvFirstName.setError("First name must be at least 3 characters");
            return;
        }

        //Check Length of last name
        if (lastname.trim().length() < 3) {
            tvtLastName.setError("Last name must be at least 3 characters");
            return;
        }
        //Check length of password
        if (password.toString().length()<8) {
            tvPassword.setError("Password must be at least 8 characters");
            return;
        }

        //Check if user entered a valid date
        dateValid= checkDate(dt);
        if (!dateValid ) {
            tvDt.setError(("Please enter a correct date (mm/dd/yyyy"));
            return;
        }

        //Check if user entered correct email address
        if ((!email.trim().matches(emailPattern)) ) {
            tvEmail.setError(("Please enter a correct email address"));
            return;
        }



       //Create new user object and send to Shared Storage
        User user = new User(firstname,lastname,email,dt,password);
        userSharedStorage = new UserSharedStorage(this);
        userSharedStorage.saveUserData(user);
        userSharedStorage.setLoggedIn(true);

        //User successfully created, return to MainActivity
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    public boolean checkDate (String dateVal) {
            //check if a valid date was entered in the registration

        String errorReturn="";
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        Date testDate = null;
        try
        {
            testDate = sdf.parse(dateVal);
        }
        catch (ParseException | java.text.ParseException e)
        {
            errorReturn = "invalid date" +
                    " format.";
            return false;
        }
        if (!sdf.format(testDate).equals(dateVal))
        {
            errorReturn = "Invalid date.";
            return false;
        }
        return true;
    }
}