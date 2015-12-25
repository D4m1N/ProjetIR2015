package com.example.damien.projetir.CustomEdit;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.damien.projetir.Tests.TestManager;

/**
 * Created by Damien on 01/12/2015.
 */
public class KeyLogger implements EditText.OnKeyListener {

    private Context context;
    private KeyLogData keyLogData;
    private TestManager testManager;

    public KeyLogger(Context context, KeyLogData kld, TestManager testM)
    {
        this.context = context;
        this.keyLogData = kld;
        this.testManager = testM;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if ((event.getAction() == KeyEvent.ACTION_DOWN))
        {

            switch(keyCode)
            {
                case KeyEvent.KEYCODE_ENTER :
                        keyLogData.setReturnPressed(true);
                        testManager.validateTest();
                            return true;
                case KeyEvent.KEYCODE_DEL :
                    /*if(keyLogData.getDeleteDone() == 1)
                        return true;*/
                    if (!keyLogData.isEnabled() || keyLogData.getDeleteDone() == 1)
                        keyLogData.setDeleteDone(0);
                    else
                        keyLogData.setDeleteDone(1);

                    break;

                case KeyEvent.KEYCODE_BACK :
                    return true; // impossible de faire un back, sinon le clavier disparait et c'est la merde

                case KeyEvent.KEYCODE_DPAD_LEFT :
                    return keyLogData.isPadsDisabled();

                case KeyEvent.KEYCODE_DPAD_RIGHT :
                    return keyLogData.isPadsDisabled();
            }
        }
        return false;
    }
}
