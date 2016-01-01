package com.example.damien.projetir.CustomEdit;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.damien.projetir.Tests.Difficulty.Difficulty;
import com.example.damien.projetir.Tests.Distance.Distance;
import com.example.damien.projetir.Tests.Orientation.Orientation;

/**
 * Created by Damien on 01/12/2015.
 * No Warning
 */
public class CustomEdit extends EditText
{

    private boolean fingerState;
    private int editPosition;
    private int previousPosition;

    public KeyLogData getKeyLogD()
    {
        return keyLogD;
    }

    public void setKeyLogD(KeyLogData keyLogD)
    {
        this.keyLogD = keyLogD;
    }

    private KeyLogData keyLogD;

    private Difficulty _difficulty;
    private int _textSize;
    private Orientation _orientation;
    private Distance _distance;
    private int _nbErrors;
    public boolean _selectionChangeable;

    private boolean started;
    private float chronometer;



    private String dataLogs;                                                                        //Variable contenant les logs, complété à chaque action,

    public String getDataLogs()
    {
        return dataLogs;
    }

    public void setDataLogs(String dataLogs)
    {
        this.dataLogs = dataLogs;
    }


    public int get_nbErrors()
    {
        return _nbErrors;
    }

    public void set_nbErrors(int _nbErrors)
    {
        this._nbErrors = _nbErrors;
    }
    public void addError()
    {
        this._nbErrors++;
    }

    public Distance get_Distance()
    {
        return _distance;
    }

    public void set_Distance(Distance _distance)
    {
        this._distance = _distance;
    }

    public int get_TextSize()
    {
        return _textSize;
    }

    public void set_TextSize(int textSize)
    {
        this._textSize = textSize;
    }

    public Difficulty get_Difficulty()
    {
        return _difficulty;
    }

    public void set_Difficulty(Difficulty difficulty)
    {
        this._difficulty = difficulty;
    }

    public Orientation get_Orientation()
    {
        return _orientation;
    }

    public void set_Orientation(Orientation orientation)
    {
        this._orientation = orientation;
    }

    public boolean isTestCompleted()
    {
        //Test de validité du test, ici on test si l'état du delete est 1
        //-1 = InitState, 1 le delete a été fait; 0 le delete etait faux
        return keyLogD.getDeleteDone() == 1;// && getTextSize() == get_TextSize()-1;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public float getChronometer() {
        return chronometer;
    }

    public void setChronometer(float chronometer) {
        this.chronometer = chronometer;
    }

    public float getCurrentTime()
    {
        return System.nanoTime() - getChronometer();
    }

    public void startChrono()
    {
        this.setChronometer(System.nanoTime());
    }

    public void stopChrono()
    {
        float chronoValue = System.nanoTime() - getChronometer(); // nanoTime pour être précis, sinon l'update n'est pas assez rapide et on a des temps de 0.0
        this.setChronometer(chronoValue);

    }


    private TextWatcher textController = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(count < get_TextSize()) {//Si l'event n'est pas généré via generateText()


                if(isStarted()) {
                    if (keyLogD.getDeleteDone() == 0)
                        reloadDueToError();

                    if (!(s.subSequence(start, start + count).length() == 0 || s.toString().length() == 0)) {
                        reloadDueToError();
                    }
                }
                else
                {
                    if (s.charAt(start) == ' ') {
                        setStarted(true);
                        reloadTest();
                        startChrono();
                    }
                    else
                        reloadTest();
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s)
        {
        }
    };

    public CustomEdit(Context context) {
        super(context);
        initialisation();
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialisation();
    }

    public void initialisation()
    {

        addTextChangedListener(textController);
        keyLogD = new KeyLogData(false,-1,false,false,false);
        setFingerState(true);
        set_nbErrors(0);
        setStarted(false);
    }


    public void setFingerState(boolean b)
    {
        fingerState = b;
        keyLogD.setPadsDisabled(b);
        _selectionChangeable = !b;//Variable utilisée pour un eventuel cancel du Keyboardshift
    }

    public boolean getFingerState()
    {
        return fingerState;
    }

    public void showKeyboard()//Affiche le clavier sans avoir besoin de tapper le text edit
    {
        requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public void generateText()
    {
        SpannableString string;
        string = new SpannableString("Lorem ipsum dol" + get_Difficulty().getLetters() +"sit amet, elit.");
        set_TextSize(string.length());


        editPosition = string.length()/2;

        string.setSpan(new ForegroundColorSpan(Color.RED), editPosition - 1, editPosition + 2, 0);

        this.setText(string);

        //Cette fonction deviendrait :
        //this.setText(TestType.generateText());

    }
                                                                                                    //Pour les tracktests : faire un objet TestType;
                                                                                                    //TestType = new ShiftTest(); par exemple
                                                                                                    //avec fonction generateText, et setPosition, qui feront les bons textes et bons placement de position
    public void setCursor()
    {
        int position;

        if(get_Orientation().getOrientation().equalsIgnoreCase("Left"))
        {
            position = (editPosition - get_Distance().getCursorPosition());
        }
        else
        {
            position = (editPosition + get_Distance().getCursorPosition());
        }

        setSelection(position);
    }

    public void resetForNewTest()
    {
        reloadTest();
        set_nbErrors(0);
    }

    public void reloadDueToError()
    {
        addError();
        reloadTest();
    }

    public void reloadTest()
    {
        if(get_Distance() == null || get_Difficulty() == null)
            Toast.makeText(getContext(), "No context left", Toast.LENGTH_SHORT).show();
        else
        {
            keyLogD.setEnabled(false);
            keyLogD.setDeleteDone(-1);
            keyLogD.setReturnPressed(false);
            generateText();
            setCursor();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (!isStarted())
            return false;

        //Si on est pas en mode finger, on renvoie faux, sinon on laisse OnTouchEvent mère gérer.
        return fingerState && super.onTouchEvent(event);

    }



    @Override
    protected void onSelectionChanged(int selStart, int selEnd)
    {
        super.onSelectionChanged(selStart, selEnd);


        if (!(keyLogD == null))
        {
            if (selStart == this.editPosition + 1)
                keyLogD.setEnabled(true);
            else
                keyLogD.setEnabled(false);
        }
    }
}
