package com.example.damien.projetir;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.CustomEdit.KeyLogger;
import com.example.damien.projetir.Presentations.PresentationFingerTest;
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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentations);

        this.textView = (TextView)findViewById(R.id.textView);


        //On lis le fichier _settings pour recupérer la config de premier test
        File settings = new File(Environment.getExternalStorageDirectory() + "/Tests/_settings.txt");
        StringBuilder text = new StringBuilder();
        try
        {

            BufferedReader br = new BufferedReader(new FileReader(settings));
            String line;

            while ((line = br.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e)
        {
            Toast.makeText(this, "Aucune Configuration n'a été trouvée", Toast.LENGTH_SHORT).show();
        }

        _settings_FirstTest = text.toString().split(":")[1].trim();

        _presentation = getPresentation(_settings_FirstTest);

        textView.setText(_presentation.getPresentation());

    }

    public Presentations getPresentation(String in)
    {


        if(in.matches("Shifts"))
        {
            Toast.makeText(this, "Shifts", Toast.LENGTH_SHORT).show();
            return new PresentationShiftTests();
        }

        if(in.matches("Finger"))
        {
            Toast.makeText(this, "Finger", Toast.LENGTH_SHORT).show();
            return new PresentationFingerTest();
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
