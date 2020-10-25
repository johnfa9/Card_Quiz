package com.ga.cardquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements CallBackInterface {
    UserSharedStorage userSharedStorage;
    FragmentManager manager;

    final int intQuestionCount = Questions.questionList.length;
    public int intQuestionPosition = 0;
    Fragment[] frags = new Fragment[intQuestionCount]; //to hold question objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSharedStorage = new UserSharedStorage(this);

        //Question firstQuestion = Questions.getBrands().get(0);
        //Question result = Questions.questionList[1];
        //Toast toast = Toast.makeText(this, result.getQuestionText(), Toast.LENGTH_SHORT);
        //toast.show();

        //savedInstanceState.putString("Question",result.getQuestionText());

        //add fragment dynamically

        manager = getSupportFragmentManager();
        //addQuestionFragment();  //send question
        createFragments();
        displayFragment(0);


        Fragment f = manager.findFragmentById(R.id.fragformat1); //id value in fragment

    }

    private void createFragments() {

        QuestionFormat1 newFrag;
        for (int i = 0; i < intQuestionCount; i++) {
            Bundle bundle = new Bundle();
            frags[i] = new QuestionFormat1();
            newFrag = (QuestionFormat1) frags[i];
            Question result = Questions.questionList[i];


            bundle.putString("Question", result.getQuestionText());
            bundle.putString("ChoiceA", result.getChoiceA());
            bundle.putString("ChoiceB", result.getChoiceB());
            bundle.putString("ChoiceC", result.getChoiceC());
            newFrag.setCallBackInterface(this);
            newFrag.setArguments(bundle);
        }


    }

    private void displayFragment(int fragNum) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_layout, frags[fragNum], "questionType" + fragNum);   //id created for main activity layout
        transaction.commit();
    }

    private void addQuestionFragment() {
        //sends question to fragment
        Bundle bundle = new Bundle();


        //create fragment
        QuestionFormat1 frag = new QuestionFormat1();  //name of fragment's Java file
        Question result = Questions.questionList[0];
        bundle.putString("Question", result.getQuestionText());
        bundle.putString("ChoiceA", result.getChoiceA());
        bundle.putString("ChoiceB", result.getChoiceB());
        bundle.putString("ChoiceC", result.getChoiceC());

        frag.setCallBackInterface(this);  //send a reference of Activity to fragment
        frag.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_layout, frag, "questionType1");   //id created for main activity layout
        transaction.commit();
    }

    @Override
    protected void onStart() {
        //check if user logged in
        super.onStart();
        //if (userSharedStorage.getLoggedIn()==true){

    }


    public void Logout(View view) {
        //    userSharedStorage.clearUserData();
        //    userSharedStorage.setUserLoggedIn(false);
    }

    public void itemClicked(long id) {

    }

//    public void  replaceAWithB(View v)
//        FragmentB f2 = new FragamentB();
//        FragmentTransaction transaction =manager.beginTransaction();
//        transaction.replace(R.id.group,f2,"B");


    @Override
    public void callBackMethod(final String strPassed) {
        boolean answer = false;
        Toast.makeText(this, strPassed, Toast.LENGTH_SHORT).show();
        confirmAnswer(strPassed);
        displayFragment(intQuestionPosition);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //method required when adding configuration portrait/landscape to manifest file
        super.onConfigurationChanged(newConfig);
    }

    public void confirmAnswer(final String strAnswer) {
        //Ask the user to confirm the answer that was submitted.
        AlertDialog dialog;
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(("Do you really want to submit your answer ?"));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this, "Your answer was recorded...congrats", Toast.LENGTH_SHORT).show();
                saveAnswer(strAnswer);

            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();

    }

    public void saveAnswer(String strAnswer) {


        if (strAnswer.equals(Questions.questionList[intQuestionPosition].getAnswer())) {
            Questions.questionList[intQuestionPosition].setCorrectAnswer(true);
        } else {
            Questions.questionList[intQuestionPosition].setCorrectAnswer(false);
        }

        if (intQuestionPosition < (intQuestionCount-1)) {
           intQuestionPosition += 1;
            displayFragment(intQuestionPosition);

        } else {
            score();
        }
    }

    public void score() {
        int intNumberCorrect = 0;
        for (int i = 0; i < intQuestionCount; i++) {
            if (Questions.questionList[i].getCorrectAnswer() == true) {
                intNumberCorrect += 1;
            }
        }
        Toast.makeText(this, "You got " + intNumberCorrect + " questions correct", Toast.LENGTH_SHORT).show();
    }
}