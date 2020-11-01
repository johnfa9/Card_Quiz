package com.ga.cardquiz;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedStorage {
    //creats shared storage for user registration
    public static final String SP_Name = "userDetails"; //name of storage file
    SharedPreferences sharedPref;

    //constructor
    public UserSharedStorage(Context context) {
        sharedPref = context.getSharedPreferences(SP_Name, 0);
    }

    public void saveUserData(User user){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstname", user.getFirstName());
        editor.putString("lastname", user.getLastName());
        editor.putString("email", user.getEmail());
        editor.putString("birthdate", user.getBirthDate());
        editor.putString("password", user.getPassword());

        editor.commit(); //save changes to SharedPreferences
    }

    public User getUserData(){
           String firstname = sharedPref.getString("firstname", "");
           String lastname = sharedPref.getString("lastname", "");
           String email = sharedPref.getString("email", "");
           String birthdate = sharedPref.getString("birthdate", "");
           String password = sharedPref.getString("password", "");

           User savedUser = new User(firstname, lastname,email,birthdate,password);

           return savedUser;
    }

    public void deleteUserData(){
        //delete shared services data when user logs out
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public void setLoggedIn(boolean userLoggedIn){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("LoggedIn", userLoggedIn);
        editor.commit();
    }

    public boolean getLoggedIn(){
        if (sharedPref.getBoolean("userLoggedIn", false) == true){
            return true;
        }
        else {
            return false;
        }
    }
}
