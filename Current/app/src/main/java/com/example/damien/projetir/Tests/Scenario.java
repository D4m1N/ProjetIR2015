package com.example.damien.projetir.Tests;

import com.example.damien.projetir.Tests.Difficulty.Difficulty;
import com.example.damien.projetir.Tests.Distance.Distance;
import com.example.damien.projetir.Tests.Orientation.Orientation;

/**
 * Created by Damien on 01/12/2015.
 */
public class Scenario
{


    private Difficulty difficulty;
    private Distance distance;
    private Orientation orientation;

    public Scenario(Difficulty diff, Distance dist, Orientation orientation)
    {
        this.difficulty = diff;
        this.distance = dist;
        this.orientation = orientation;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation distance)
    {
        this.orientation = orientation;
    }


    public Distance getDistance()
    {
        return distance;
    }

    public void setDistance(Distance distance)
    {
        this.distance = distance;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }


}
