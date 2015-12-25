package com.example.damien.projetir.Tests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.damien.projetir.ChooseYourName;
import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.Presentations.PresentationFingerTest;
import com.example.damien.projetir.Presentations.PresentationShiftTests;
import com.example.damien.projetir.Presentations.Presentations;
import com.example.damien.projetir.Tests.Difficulty.*;
import com.example.damien.projetir.Tests.Difficulty.Medium;
import com.example.damien.projetir.Tests.Distance.*;
import com.example.damien.projetir.Tests.Distance.Short;
import com.example.damien.projetir.Tests.Orientation.Left;
import com.example.damien.projetir.Tests.Orientation.Right;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Damien on 01/12/2015.
 */
public class TestManager
{


    private Vector<Scenario> scenarios;
    private CustomEdit edit;
    private FileManager fileManager;
    AlertDialog.Builder ad;

    private boolean done;
    private Bundle bundle;
    private int currentTestNumber;
    private int totalTestNumber;

    private Presentations currentTest;

    private String _login;

    public TestManager(CustomEdit edit, String login, String firstTest)
    {
        scenarios = new Vector<>();
        this.edit = edit;
        this.bundle = new Bundle();
        this.currentTestNumber = 0;
        this._login = login;
        this.fileManager = new FileManager(_login);

        ad = new AlertDialog.Builder(edit.getContext());
        ad.setCancelable(true);

        handleFirstTest(firstTest);

        initManager();

    }

    public boolean noContextLeft()
    {
        return done;
    }

    public void initManager()
    {
        scenarios.addElement(new Scenario(new Easy(), new Short(),new Right()));
        scenarios.addElement(new Scenario(new Medium() , new Short(), new Right()));
        scenarios.addElement(new Scenario(new Hard(), new Short(), new Right()));
        scenarios.addElement(new Scenario(new Easy(), new DMedium(), new Right()));
        scenarios.addElement(new Scenario(new Medium(), new DMedium(), new Right()));
        scenarios.addElement(new Scenario(new Hard(), new DMedium(), new Right()));
        scenarios.addElement(new Scenario(new Easy(), new Far(), new Right()));
        scenarios.addElement(new Scenario(new Medium(), new Far(), new Right()));
        scenarios.addElement(new Scenario(new Hard(), new Far(), new Right()));
        scenarios.addElement(new Scenario(new Easy(), new Short(),new Left()));
        scenarios.addElement(new Scenario(new Medium() , new Short(), new Left()));
        scenarios.addElement(new Scenario(new Hard(), new Short(), new Left()));
        scenarios.addElement(new Scenario(new Easy(), new DMedium(), new Left()));
        scenarios.addElement(new Scenario(new Medium(), new DMedium(), new Left()));
        scenarios.addElement(new Scenario(new Hard(), new DMedium(), new Left()));
        scenarios.addElement(new Scenario(new Easy(), new Far(), new Left()));
        scenarios.addElement(new Scenario(new Medium(), new Far(), new Left()));
        scenarios.addElement(new Scenario(new Hard(), new Far(), new Left()));

        totalTestNumber = scenarios.size() * 2;
        done = false;
    }

    public void showAlert(String message)
    {
        ad
                .setTitle("Fin de tests")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        ad.show();
    }

    public void handleFirstTest(String in)
    {
        if(in.matches("Shifts"))
        {
            edit.setFingerState(false);
            currentTest = new PresentationShiftTests();
        }

        if(in.matches("Finger"))
        {
            edit.setFingerState(true);
            currentTest = new PresentationFingerTest();
        }
    }

    public void generateContext()
    {

        int randomNumber;

        Random rng = new Random(System.currentTimeMillis());

        if(scenarios.isEmpty())
        {
            if(totalTestNumber == currentTestNumber)//Les tests sont terminés
            {
                done = true;
                edit.set_Difficulty(null);
                edit.set_Distance(null);
                fileManager.writeFile(bundle);
                ad
                        .setTitle("Fin des tests")
                        .setMessage("Vous avez terminer les tests." + '\n' + "Merci d'avoir participé !")
                        .setPositiveButton("Terminer", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                                Intent intent = new Intent(edit.getContext(), ChooseYourName.class);
                                edit.getContext().startActivity(intent);
                            }
                        });
                ad.show();

            }
            else//Une des 2 parties seulement est terminée
            {
                showAlert("Vous avez terminer les tests "+ currentTest.getType()+", vous allez maintenant faire les tests "+ currentTest.getComplement().getType());

                initManager();
                edit.setFingerState(!edit.getFingerState());
                currentTest = currentTest.getComplement();
                generateContext();

            }
        }
        else
        {
            if(scenarios.size() > 1)
                randomNumber = rng.nextInt(scenarios.size()-1);
            else
                randomNumber = 0;

            edit.set_Difficulty(scenarios.elementAt(randomNumber).getDifficulty());
            edit.set_Distance(scenarios.elementAt(randomNumber).getDistance());
            edit.set_Orientation(scenarios.elementAt(randomNumber).getOrientation());
            scenarios.remove(randomNumber);

            this.currentTestNumber++;
            edit.resetForNewTest();
        }
    }

    public void validateTest()
    {
        if(edit.isTestCompleted())
        {
            edit.stopChrono();
            edit.setStarted(false);
            edit.hideKeyboard();

            String testDatas = new String();

            testDatas = testDatas.concat("Test NB : " + currentTestNumber + '\n');
            testDatas = testDatas.concat("Chrono(in Sec) : " + edit.getChronometer() / 1000000000 + '\n');
            testDatas = testDatas.concat("Errors : " + edit.get_nbErrors() + '\n');
            testDatas = testDatas.concat("Difficulty : " + edit.get_Difficulty().getLabel() + '\n');
            testDatas = testDatas.concat("Distance : " + edit.get_Distance().getLabel() + '\n');
            testDatas = testDatas.concat("Orientation : " + edit.get_Orientation().getLabel() + '\n');
            testDatas = testDatas.concat("FingerAllowed : " + edit.getFingerState() + '\n');

            testDatas = testDatas.concat("---------------------------------" + '\n');

            //Toast.makeText(edit.getContext(), testDatas, Toast.LENGTH_SHORT).show();
            bundle.putCharSequence("Test " + currentTestNumber, testDatas);

            generateContext();
        }
        else
        {
            edit.addError();
            Toast.makeText(edit.getContext(), "Vous n'avez pas rempli le test", Toast.LENGTH_SHORT).show();
        }
    }
}
