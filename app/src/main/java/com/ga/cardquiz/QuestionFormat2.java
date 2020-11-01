package com.ga.cardquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class QuestionFormat2 extends Fragment {
    CallBackInterface callBackInterface;
    CheckBox mChoiceA, mChoiceB, mChoiceC, mChoiceD;

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_format2, container, false);

        //to have fragment handle button event
        Button button = (Button) view.findViewById(R.id.button_apply);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtCheckedItems = "";

                if (mChoiceA.isChecked()) {
                    txtCheckedItems += "choiceA";
                }
                if (mChoiceB.isChecked()) {
                    txtCheckedItems += "choiceB";
                }

                if (mChoiceC.isChecked()) {
                    txtCheckedItems += "choiceC";
                }

                if (mChoiceD.isChecked()) {
                    txtCheckedItems += "choiceD";
                }
                if (!mChoiceA.isChecked() && !mChoiceB.isChecked() && !mChoiceC.isChecked()
                        && !mChoiceD.isChecked()) {
                    Toast.makeText(getActivity(), "Please make a selection",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (callBackInterface != null) {
                        callBackInterface.callBackMethod(txtCheckedItems);
                    }
                }
            }

        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {

        String txtCheckedItems = "";
        // Set the view’s values in the fragment’s onStart() method
        super.onStart();

        View view = getView();
        if (view != null) {
            //get bundle values
            final String question = this.getArguments().getString("Question");
            final String choiceA = this.getArguments().getString("ChoiceA");
            final String choiceB = this.getArguments().getString("ChoiceB");
            final String choiceC = this.getArguments().getString("ChoiceC");
            final String choiceD = this.getArguments().getString("ChoiceD");

            //Get view references
            mChoiceA = view.findViewById(R.id.choice1);
            mChoiceB = view.findViewById(R.id.choice2);
            mChoiceC = view.findViewById(R.id.choice3);
            mChoiceD = view.findViewById(R.id.choice4);

            //clear check boxes
            mChoiceA.setChecked(false);
            mChoiceB.setChecked(false);
            mChoiceC.setChecked(false);
            mChoiceD.setChecked(false);

            //Populate views
            TextView vwQuestion = (TextView) view.findViewById(R.id.text2_view_selected);
            vwQuestion.setText(question);
            mChoiceA.setText(choiceA);
            mChoiceB.setText(choiceB);
            mChoiceC.setText(choiceC);
            mChoiceD.setText(choiceD);
        }
    }

}
