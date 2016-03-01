package com.example.damien.projetir.Tests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.damien.projetir.ChooseYourName;
import com.example.damien.projetir.CustomEdit.CustomEdit;
import com.example.damien.projetir.CustomEdit.Logger;
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
 * WR
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
        if(in.matches("Shifts"))//Tests mouvements relatifs
        {
            edit.setFingerState(false);
            //edit.setHybrid(false);
            currentTest = new PresentationShiftTests();
        }

        if(in.matches("Finger"))//Tests mouvements absolus
        {
            edit.setFingerState(true);
            //edit.setHybrid(false);
            currentTest = new PresentationFingerTest();
        }

        /*
        if(in.matches("Hybrid"))//Tests mouvements finger une fois puis only pads
        {
            edit.setFingerState(true);
            edit.setHybrid(true);
            currentTest = new PresentationHybridTest();
        }

         */
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
                bundle.putCharSequence("Results Tests n°" + fileManager.getTestNumber(_login), edit.getLogger().getDataLogs());
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
                edit.resetLoger();

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

            edit.getLogger().writeSingleDataLogs("<"+ Logger.LogAction.LOG_ACTION_INIT.name()+ ">" + '\n');
            edit.getLogger().writeSingleDataLogs("<TestNumber>" + currentTestNumber + "</TestNumber>" + '\n');
            edit.getLogger().writeSingleDataLogs("<Difficulty>" + edit.get_Difficulty().getLabel() + "</Difficulty>" + '\n');
            edit.getLogger().writeSingleDataLogs("<Distance>" + edit.get_Distance().getLabel() + "</Distance>" + '\n');
            edit.getLogger().writeSingleDataLogs("<Orientation>" + edit.get_Orientation().getLabel() + "</Orientation>" + '\n');
            edit.getLogger().writeSingleDataLogs("<FingerAllowed>" + edit.getFingerState()+ "</FingerAllowed>" + '\n');
            edit.getLogger().setPreviousLogAction(Logger.LogAction.LOG_ACTION_INIT);

            edit.resetForNewTest();
        }
    }

    public void validateTest()
    {
        if (edit.isTestCompleted())
        {
            edit.getLogger().writeDataLogs(Logger.LogAction.LOG_ACTION_RETURN, "Return pressed, test complete",edit.getCurrentTime());

            edit.stopChrono();
            edit.setStarted(false);
            edit.hideKeyboard();

            edit.getLogger().writeSingleDataLogs("<Errors>" + edit.get_nbErrors() + "</Errors>" + '\n');
            edit.getLogger().writeSingleDataLogs("<Time>" + edit.getChronometer() / 1000000000 + "</Time>" + '\n');

            edit.getLogger().writeSingleDataLogs("---------------------------------" + '\n');


            generateContext();
        }
        else
        {
            edit.getLogger().writeDataLogs(Logger.LogAction.LOG_ACTION_RETURN, "ERROR : Return pressed, test not complete", edit.getCurrentTime());
            edit.addError();
            Toast.makeText(edit.getContext(), "Vous n'avez pas rempli le test", Toast.LENGTH_SHORT).show();
        }
    }
}
