package com.example.damien.projetir;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.damien.projetir.Presentations.PresentationFingerTest;
import com.example.damien.projetir.Presentations.Presentations;
import com.example.damien.projetir.Presentations.PresentationShiftTests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseYourName extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_name);
        addItemToSpinnerNames();
    }


    private void addItemToSpinnerNames()
    {
        spinner = (Spinner) findViewById(R.id.spinnerNames);
        spinner.setAdapter(null);
        File dir = new File(Environment.getExternalStorageDirectory() + "/Tests/Users/");
        String[] files = dir.list();

        if (files != null) {
            List<String> list = new ArrayList<>();
            for (final String name : files) {

                String[] splittedStuff =  name.split("_");
                if ( splittedStuff.length > 1) {
                    String namePerson = name.split("_")[1];
                    list.add(namePerson);
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    public void buttonOnClick(View v) {

        Intent intent = new Intent(this, WriteName.class);
        startActivityForResult(intent,2);
    }

    public void onValidateClick(View v) {

        if(spinner.getSelectedItem() != null)
        {
            Intent intent = new Intent(this, PresentationActivity.class);
            intent.putExtra("Login", spinner.getSelectedItem().toString());

            intent.putExtra("NextActivity", "TestActivity");




            startActivity(intent);
        }
        else
            Toast.makeText(this, "Aucun nom choisis", Toast.LENGTH_SHORT).show();

    }

    //Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            this.addItemToSpinnerNames();
        }
    }
}
