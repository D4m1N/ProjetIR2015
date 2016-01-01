package com.example.damien.projetir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.CustomEdit.KeyLogger;
import com.example.damien.projetir.Tests.TestInterface.GoButtonListener;
import com.example.damien.projetir.Tests.TestManager;

public class TestActivity extends AppCompatActivity {

    private CustomEdit mainEdit;
    private Button goA,goB;

    private TestManager testManager;
    private String login;
    private boolean canGo;
//
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canGo = false;

        login = getIntent().getStringExtra("Login");

        this.mainEdit = (CustomEdit)findViewById(R.id.mainEdit);
        this.goA = (Button)findViewById(R.id.buttonGO);
        this.goB = (Button)findViewById(R.id.goBButton);


        this.testManager = new TestManager(this.mainEdit,login,getIntent().getStringExtra("FirstTest"));

        this.mainEdit.setOnKeyListener(new KeyLogger(getApplicationContext(),mainEdit.getKeyLogD(),testManager));
        this.goA.setOnTouchListener(new GoButtonListener(canGo));
        this.goB.setOnTouchListener(new GoButtonListener(canGo));

        testManager.generateContext();

    }


    public void onGOClick(View v)
    {
        if(canGo)
        {
            mainEdit.setStarted(true);
            mainEdit.startChrono();
            mainEdit.showKeyboard();
        }
        canGo = true;
    }


}
