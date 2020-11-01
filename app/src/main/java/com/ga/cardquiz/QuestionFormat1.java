package com.ga.cardquiz;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class QuestionFormat1 extends Fragment {

    CallBackInterface callBackInterface;

    public void setCallBackInterface(CallBackInterface callBackInterface){
        this.callBackInterface = callBackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_format1, container, false);

        //to have fragment handle button event
        Button button = (Button) view.findViewById(R.id.button_apply);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View view = getView();
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                //check if user didn't make a selection
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Please make a selection", Toast.LENGTH_SHORT).show();
                }
                else {

                    String txtRadioButton = (String) radioButton.getText();
                    if (callBackInterface != null) {
                        callBackInterface.callBackMethod(txtRadioButton);

                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        // Set the view’s values in the fragment’s onStart() method
        super.onStart();

        View view = getView();
        if (view != null) {
            //get view references
            final String question = this.getArguments().getString("Question");
            final String choiceA = this.getArguments().getString("ChoiceA");
            final String choiceB = this.getArguments().getString("ChoiceB");
            final String choiceC = this.getArguments().getString("ChoiceC");
            final String choiceD = this.getArguments().getString("ChoiceD");


            //Populate question
            TextView vwQuestion =(TextView) view.findViewById(R.id.text_view_selected);
            vwQuestion.setText(question);

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            radioGroup.clearCheck(); //make sure radio buttons are cleared
            ((RadioButton) radioGroup.getChildAt(0)).setText(String.valueOf(choiceA));
            ((RadioButton) radioGroup.getChildAt(1)).setText(String.valueOf(choiceB));
            ((RadioButton) radioGroup.getChildAt(2)).setText(String.valueOf(choiceC));
            ((RadioButton) radioGroup.getChildAt(3)).setText(String.valueOf(choiceD));
        }

    }
}