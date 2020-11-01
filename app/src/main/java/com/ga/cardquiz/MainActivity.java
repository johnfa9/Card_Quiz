package com.ga.cardquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements CallBackInterface {

    FragmentManager manager;
    private static final long START_TIME_IN_MILLIS = 120000; //set the initial timer 2 min 120000
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    AlertDialog dialogCancel;


    final int intQuestionCount = Questions.questionList.length;
    public int intQuestionPosition = 0;
    Fragment[] frags = new Fragment[intQuestionCount]; //an array of Fragments to hold questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCountDown = findViewById(R.id.view_countdown); //countdown view
        manager = getSupportFragmentManager();  //create fragment manager
        createFragments();
        startTimer();  //start the clock countdown
        updateCountDownText();
        displayFragment(0);
    }

    private void createFragments() {
        //Populate Fragment array with questions from questionList array

        QuestionFormat1 newFrag1; //question with radio button format
        QuestionFormat2 newFrag2; //multi answer question

        for (int i = 0; i < intQuestionCount; i++) {
            Bundle bundle = new Bundle();  //bundle will hold the question information
            Question result = Questions.questionList[i];

            if (Questions.questionList[i].getType() == "radio") {

                frags[i] = new QuestionFormat1();  //create a single choice question
                newFrag1 = (QuestionFormat1) frags[i]; //get a reference to the created fragment
                newFrag1.setCallBackInterface(this);
                newFrag1.setArguments(bundle);
            } else {
                frags[i] = new QuestionFormat2();  //create a single choice question
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
        }
    }

    private void displayFragment(int fragNum) {
        //updates the fragment to display in MainActivity
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_layout, frags[fragNum], "questionType" + fragNum);   //id created for main activity layout
        transaction.commit();
        //Fragment f = manager.findFragmentById(R.id.fragformat1); //id value in fragment

    }

    @Override
    public void callBackMethod(final String strPassed) {
        //method is called from fragment
        if (strPassed == "restart") {
            RestartGame();

        } else {
            boolean answer = false;
            confirmAnswer(strPassed);
        }
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
                Toast toast = Toast.makeText(MainActivity.this, "Your answer " +
                                "was recorded...congrats",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
                toast.show();
                saveAnswer(strAnswer);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this, "Please select an answer",
                        Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialogCancel = builder.show();  //save a reference so dialog can be closed if time elapses
    }

    public void saveAnswer(String strAnswer) {
        //saves answer entered by user

        if (strAnswer.equals(Questions.questionList[intQuestionPosition].getAnswer())) {
            Questions.questionList[intQuestionPosition].setCorrectAnswer(true);
        } else {
            Questions.questionList[intQuestionPosition].setCorrectAnswer(false);
        }

        //Record user's answer to the question
        Questions.questionList[intQuestionPosition].setUserAnswer(strAnswer);

        if (intQuestionPosition < (intQuestionCount - 1)) {
            intQuestionPosition += 1;
            displayFragment(intQuestionPosition);  //display next fragment

        } else {
            score();
        }
    }

    public void score() {
        //Calculates number of correct answers
        mCountDownTimer.cancel(); //stop timer
        if (dialogCancel != null) {
            dialogCancel.cancel();  //if time elapses while dialog is open, close it
        }
        int intNumberCorrect = 0;
        for (int i = 0; i < intQuestionCount; i++) {
            if (Questions.questionList[i].getCorrectAnswer() == true) {
                intNumberCorrect += 1;
            }
        }

        //write quiz results to file
        submitScores("Score: " + intNumberCorrect + "/5 correct");

        //load result fragment
        DisplayResult();
    }

    @Override
    public void onBackPressed() {
        //Prevent operation of back button
        Toast.makeText(this, "Sorry you can't go back now", Toast.LENGTH_SHORT).show();
    }

    private void submitScores(String score) {
        //save score to a file
        String filename = "cardFile.txt";
        FileOutputStream outputStream;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {

            //file is private to app
            outputStream = openFileOutput(filename, this.MODE_PRIVATE | this.MODE_APPEND);
            outputStream.write(dtf.format(now).getBytes());  //content initialized above
            outputStream.write("\t\t".getBytes());  //content initialized above
            outputStream.write(score.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void RestartGame() {
        //clear answer of previous game
        //reset game parameters
        for (int i = 0; i < intQuestionCount; i++) {
            Questions.questionList[i].setUserAnswer(null);
            Questions.questionList[i].setCorrectAnswer(false);
        }
        //display first question
        intQuestionPosition = 0;
        displayFragment(0);
        resetTimer();
        startTimer();
    }


    public void DisplayResult() {
        //display of the history of game play
        ResultsActivity resultView = new ResultsActivity();
        resultView.setCallBackInterface(this);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_layout, resultView, "questionType" + "results");
        transaction.commit();

    }

    private void startTimer() {
        //starts timer and update
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                //no time remaining to continue game
                score();
            }
        }.start();
    }

    private void resetTimer() {
        //reset time to constant
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {
        //update timer with updated time
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",
                minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}