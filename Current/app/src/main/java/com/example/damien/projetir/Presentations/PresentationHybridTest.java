package com.example.damien.projetir.Presentations;

/**
 * Created by Damien on 06/12/2015..
 * Warning !//
 */
public class PresentationHybridTest implements Presentations
{
    @Override
    public String getPresentation()
    {
        return "Vous allez faire des tests hybrides." + '\n'
                +"Utilisez votre doigt pour positionner votre curseur UNE FOIS." + '\n'
                +"Puis utilisez les PADS du clavier pour terminer votre positionnement";
    }

    @Override
    public String getType()
    {
        return "Hybrid";
    }

    @Override
    public Presentations getComplement()
    {
        return null;
    }
}
