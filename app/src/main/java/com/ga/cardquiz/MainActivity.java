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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements CallBackInterface {
    UserSharedStorage userSharedStorage;  //to store login
    FragmentManager manager;

    final int intQuestionCount = Questions.questionList.length;
    public int intQuestionPosition = 0;
    Fragment[] frags = new Fragment[intQuestionCount]; //an array of Fragments to hold questions

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

        manager = getSupportFragmentManager();  //create fragment manager
        //addQuestionFragment();  //send question
        createFragments();
        displayFragment(0);

        //Not used


    }

    private void createFragments() {
        //Populate Fragment array with questions from questionList array

        //QuestionFormat1 newFrag;
        QuestionFormat1 newFrag1;
        QuestionFormat2 newFrag2;


        for (int i = 0; i < intQuestionCount; i++) {
            Bundle bundle = new Bundle();  //bundle will hold the question information
            Question result = Questions.questionList[i];

            if (Questions.questionList[i].getType() == "radio") {

                frags[i] = new QuestionFormat1();  //create a single choice question
                //newFrag = frags[i]; //get a reference to the created fragment
                  //retrieve a question
                newFrag1 = (QuestionFormat1) frags[i]; //get a reference to the created fragment
                newFrag1.setCallBackInterface(this);
                newFrag1.setArguments(bundle);
            }

            else {
                frags[i] = new QuestionFormat2();  //create a single choice question
                //newFrag = frags[i]; //get a reference to the created fragment
                //result = Questions.questionList[i];  //retrieve a question
                newFrag2 = (QuestionFormat2) frags[i]; //get a reference to the created fragment
                newFrag2.setCallBackInterface(this);
                newFrag2.setArguments(bundle);
            }

            result = Questions.questionList[i];

            //copy the question to a bundle
            bundle.putString("Question", result.getQuestionText());
            bundle.putString("ChoiceA", result.getChoiceA());
            bundle.putString("ChoiceB", result.getChoiceB());
            bundle.putString("ChoiceC", result.getChoiceC());
            bundle.putString("ChoiceD", result.getChoiceD());

            //pass bundle to fragment
            //newFrag.setCallBackInterface(this);
            //newFrag.setArguments(bundle);
        }


    }

    private void displayFragment(int fragNum) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_layout, frags[fragNum], "questionType" + fragNum);   //id created for main activity layout
        transaction.addToBackStack("Question " + fragNum);
        transaction.commit();


        Fragment f = manager.findFragmentById(R.id.fragformat1); //id value in fragment
        //RadioGroup radioText=f.getView().findViewById(R.id.radioGroup);
      // RadioButton T=  (RadioButton)f.getView().findViewById(R.id.choiceA);
        //T.setChecked(true);

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
        bundle.putString("ChoiceD", result.getChoiceD());

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
        //displayFragment(intQuestionPosition);
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

        //Record user's answer to the question
        Questions.questionList[intQuestionPosition].setUserAnswer(strAnswer);

        if (intQuestionPosition < (intQuestionCount-1)) {
           intQuestionPosition += 1;
            displayFragment(intQuestionPosition);  //display next fragment

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

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        //intQuestionPosition = intQuestionPosition -1;
        Toast.makeText(this, "Sorry you can update a confirmed answer", Toast.LENGTH_SHORT).show();

    }
}