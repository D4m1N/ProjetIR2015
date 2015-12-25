package com.example.damien.projetir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.CustomEdit.KeyLogger;
import com.example.damien.projetir.Tests.TestManager;

public class TestActivity extends AppCompatActivity {
//Commentaire 2
    private CustomEdit mainEdit;
    private TestManager testManager;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = getIntent().getStringExtra("Login");

        this.mainEdit = (CustomEdit)findViewById(R.id.mainEdit);
        this.testManager = new TestManager(this.mainEdit,login,getIntent().getStringExtra("FirstTest"));

        this.mainEdit.setOnKeyListener(new KeyLogger(getApplicationContext(),mainEdit.getKeyLogD(),testManager));

        testManager.generateContext();

    }


    public void onGOClick(View v)
    {
        mainEdit.setStarted(true);
        mainEdit.startChrono();
        mainEdit.showKeyboard();
    }


}
