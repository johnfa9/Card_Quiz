package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResultsActivity extends Fragment {
    private TextView mDisplayMsg;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_results);


    // }
    CallBackInterface callBackInterface;

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_results, container, false);


        //to have fragment handle button event
        Button button = (Button) view.findViewById(R.id.button_results);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getView();


                //createSnackbar();
                if (callBackInterface != null) {
                    callBackInterface.callBackMethod("restart");
                }
            }
        });
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        TextView mDisplayMsg;
        mDisplayMsg = view.findViewById(R.id.showMsg);
        try {
            mDisplayMsg.setText(readTextFromUri(Uri.fromFile(new File(getActivity().getFilesDir(), "cardFile.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        //create inputStream
        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

        //create bufferedreader object
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));

        //create stringbuilder
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (( line = reader.readLine()) !=null) {
            stringBuilder.append(line + "\n");

        }
        inputStream.close();
        reader.close();

        return stringBuilder.toString();
    }

    public void RestartGame(View view) {

    }
}