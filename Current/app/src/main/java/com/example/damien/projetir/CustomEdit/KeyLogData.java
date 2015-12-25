package com.example.damien.projetir.CustomEdit;

/**
 * Created by lehmann15u on 04/12/2015.
 */
public class KeyLogData
{

    //Enabled est vrai si la selection est au bon endroit
    //Il sert au key logger pour savoir si le delete detecté est bon ou mauvais
    private boolean enabled;

    private int deleteDone;//-1 = InitState, 1 le delete a été fait; 0 le delete etait faux

    private boolean returnPressed;
    private boolean testStarted;
    private boolean padsDisabled;

    public boolean isTestStarted()
    {
        return testStarted;
    }

    public void setTestStarted(boolean testStarted)
    {
        this.testStarted = testStarted;
    }

    public KeyLogData(boolean enabled, int deleteDone, boolean returnPressed, boolean tio, boolean pads) {
        this.enabled = enabled;
        this.deleteDone = deleteDone;
        this.returnPressed = returnPressed;
        this.testStarted = tio;
        this.padsDisabled = pads;

    }

    public boolean isReturnPressed() {
        return returnPressed;
    }

    public void setReturnPressed(boolean returnPressed) {
        this.returnPressed = returnPressed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDeleteDone() {
        return deleteDone;
    }

    public void setDeleteDone(int deleteDone) {
        this.deleteDone = deleteDone;
    }


    public boolean isPadsDisabled()
    {
        return padsDisabled;
    }

    public void setPadsDisabled(boolean padsDisabled)
    {
        this.padsDisabled = padsDisabled;
    }

}
