package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResultsActivity extends AppCompatActivity {
    private TextView mDisplayMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView mDisplayMsg;
        mDisplayMsg= (TextView) findViewById(R.id.showMsg);
        try {
           mDisplayMsg.setText(readTextFromUri(Uri.fromFile(new File(this.getFilesDir(), "cardFile.txt"))));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        //create inputStream
        InputStream inputStream = getContentResolver().openInputStream(uri);

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