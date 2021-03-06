package com.example.damien.projetir.Presentations;

/**
 * Created by Damien on 06/12/2015..
 * Warning !
 */
public class PresentationShiftTests implements Presentations
{
    @Override
    public String getPresentation()
    {
        return "Vous allez faire des tests mouvements relatifs." + '\n'
                +"Laissez votre doigt enfoncé sur la partie droite ou gauche du clavier, puis tapez sur l'autre partie pour déplacer votre sélection"+ '\n'
                +"Le but dans tous les tests est de supprimer le caractère rouge du milieu";
    }

    @Override
    public String getType()
    {
        return "Shift";
    }

    @Override
    public Presentations getComplement()
    {
        return new PresentationFingerTest();
    }
}
