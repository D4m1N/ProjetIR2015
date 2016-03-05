package com.example.damien.projetir;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.CustomEdit.KeyLogger;
import com.example.damien.projetir.Presentations.PresentationFingerTest;
import com.example.damien.projetir.Presentations.PresentationHybridTest;
import com.example.damien.projetir.Presentations.PresentationShiftTests;
import com.example.damien.projetir.Presentations.Presentations;
import com.example.damien.projetir.Tests.FileManager;
import com.example.damien.projetir.Tests.TestManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PresentationActivity extends AppCompatActivity {

    private TextView textView;
    private Presentations _presentation;
    private String _settings_FirstTest;
    private InitialisationFile IF;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentations);

        this.textView = (TextView)findViewById(R.id.textView);
        this.IF = new InitialisationFile(getIntent().getCharSequenceExtra("Login").toString());

        _presentation = getPresentation(IF.getCurrentTest());

        textView.setText(_presentation.getPresentation());

    }

    public Presentations getPresentation(char in)
    {

        Log.w("debug",Character.toString(in));
        if(in == '0')
        {
            Toast.makeText(this, "Shifts", Toast.LENGTH_SHORT).show();
            _settings_FirstTest = "Shifts";
            return new PresentationShiftTests();
        }

        if(in == '1')
        {
            Toast.makeText(this, "Finger", Toast.LENGTH_SHORT).show();
            _settings_FirstTest = "Finger";
            return new PresentationFingerTest();
        }

        if(in == '2')
        {
            Toast.makeText(this, "Hybrid", Toast.LENGTH_SHORT).show();
            _settings_FirstTest = "Hybrid";
            return new PresentationHybridTest();
        }

        return null;
    }


    public void onOKClick(View v)
    {

        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("FirstTest", _settings_FirstTest);
        intent.putExtra("Login", getIntent().getCharSequenceExtra("Login"));
        startActivity(intent);
    }

}
