package com.example.damien.projetir.Tests.TestInterface;

import android.view.MotionEvent;
import android.view.View;

import com.example.damien.projetir.CustomEdit.CustomEdit;

/**
 * Created by Damien on 01/01/2016.
 * Warning ?
 */
public class GoButtonListener implements View.OnTouchListener
{
    private boolean[] canGo;
    private CustomEdit mainEdit;

    public GoButtonListener(boolean [] canGo,CustomEdit mainEdit)
    {
        this.canGo = canGo;
        this.mainEdit = mainEdit;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            this.canGo[0] = false;
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(canGo[0])
            {
                this.mainEdit.setStarted(true);
                this.mainEdit.startChrono();
                this.mainEdit.showKeyboard();
            }
            this.canGo[0] = true;
        }

        return false;
    }
}
