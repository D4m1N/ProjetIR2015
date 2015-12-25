package com.example.damien.projetir.CustomEdit;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

/**
 * Created by Damien on 01/12/2015.
 */
public class TextController implements TextWatcher
{

    private Context context;

    public TextController(Context context)
    {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        /*Toast.makeText(context,
                "Char S : " + s + " start : " + start + " before : " + before,
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }
}
