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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class QuestionFormat1 extends Fragment {

//    public interface Communicator {
//        public void response(long id);
//    };
//    private Communicator comm;
//
//    public void setCommunicator (Communicator c)
//    {
//        Communicator comm = c;
//    }

//    public void onClick(View v)
//    {
//        comm.response("button was clicked"); //comm is MainActivity, calls respond method
//    }

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
                //createSnackbar();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    // @Override
//   public void onResume(){
//        super.onResume();
//        View view =getView();
//       RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
//       radioGroup.clearCheck();
//   }

    //@Override
//   public void onDetach(){
//        super.onDetach();
//       View view =getView();
//       RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
//       radioGroup.clearCheck();
//   }


//   @Override
//   public void onDestroyView(){
//        super.onDestroyView();
//       View view =getView();
//       RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
//      radioGroup.clearCheck();
//
//
//    }

//    @Override
//    public void onStop(){
//        super.onStop();
//        View view =getView();
//        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
//        radioGroup.clearCheck();
//        int selectedId = radioGroup.getCheckedRadioButtonId();
//
//    }

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
            //RadioButton vwChoiceA =(RadioButton) view.findViewById(R.id.choiceA);
            //RadioButton vwChoiceB =(RadioButton) view.findViewById(R.id.choiceB);
            //RadioButton vwChoiceC =(RadioButton) view.findViewById(R.id.choiceC);


            vwQuestion.setText(question);

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            radioGroup.clearCheck(); //make sure radio buttons are cleared
            ((RadioButton) radioGroup.getChildAt(0)).setText(String.valueOf(choiceA));
            ((RadioButton) radioGroup.getChildAt(1)).setText(String.valueOf(choiceB));
            ((RadioButton) radioGroup.getChildAt(2)).setText(String.valueOf(choiceC));
            ((RadioButton) radioGroup.getChildAt(3)).setText(String.valueOf(choiceD));

//            ((RadioButton) radioGroup.getChildAt(0)).setChecked(false);
//            ((RadioButton) radioGroup.getChildAt(1)).setChecked(false);
//            ((RadioButton) radioGroup.getChildAt(2)).setChecked(false);
//            ((RadioButton) radioGroup.getChildAt(3)).setChecked(false);


            //need to pass data from questions

        }

    }

    @Override
    public void onAttach(Context context) { //called when fragment gets attached to the activity
        super.onAttach(context);
//        this.listener = (Listener)context;


    }

//    public void createSnackbar() {
//        //View view =getView();
//        View v =getActivity().findViewById(android.R.id.content);
//        Snackbar.make(v,"Submit Answer ?", Snackbar.LENGTH_LONG)
//                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
//                .setAction("Penis") {}
//                .show()
//
//        }
        //add snackbar library to build.gradle

        //Button vwBtn =(Button) view.findViewById(R.id.button_apply);









}