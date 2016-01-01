package com.example.damien.projetir.Tests.TestInterface;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Damien on 01/01/2016.
 */
public class GoButtonListener implements View.OnTouchListener
{
    private boolean canGo;

    public GoButtonListener(boolean canGo)
    {
        this.canGo = canGo;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE)
            this.canGo = false;

        return false;
    }
}
