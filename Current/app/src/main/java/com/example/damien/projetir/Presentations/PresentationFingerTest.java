package com.example.damien.projetir.Presentations;

/**
 * Created by Damien on 06/12/2015..
 * Warning !//
 */
public class PresentationFingerTest implements Presentations
{
    @Override
    public String getPresentation()
    {
        return "Vous allez faire des tests en mouvements absolus." + '\n'
                +"Utilisez votre doigt pour positionner votre curseur." + '\n'
                +"Le but dans tous les tests est de supprimer le caractère rouge du milieu";
    }

    @Override
    public String getType()
    {
        return "Finger";
    }

    @Override
    public Presentations getComplement()
    {
        return new PresentationShiftTests();
    }
}
