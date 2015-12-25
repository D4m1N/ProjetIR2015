package com.example.damien.projetir;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class WriteName extends AppCompatActivity {

    public static final String  PATH = Environment.getExternalStorageDirectory() + "/Tests/Users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_name);
    }

    public void buttonOnClick(View v) {

        EditText mEdit   = (EditText)findViewById(R.id.editText_name);

        String name=mEdit.getText().toString();
        name = name.replace('_', ' ');
        name = name.replace('/', ' ');
        name = name.replace('\\', ' ');

        File dir = new File(PATH);
        String[] files = dir.list();
        int lenght;
        if (files != null) {
            lenght = files.length;
        }
        else
            lenght = 0;

        if (this.isPresent(name) == -1) {

            name = PATH + (lenght + 1) + "_" + name;

            boolean success = new File(PATH).mkdirs();

            success = new File(name).mkdirs();

            Intent intent=new Intent();
            intent.putExtra("MESSAGE","finished");
            setResult(2,intent);
            finish();//finishing activity
        }
        else
        {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Erreur : Ce nom existe déjà", Toast.LENGTH_LONG);
            toast.show();
        }

    }


    public int isPresent(String User)
    {
        File dir = new File(PATH);
        String[] files = dir.list();


        if (files != null) {
            for (int i = 0; i <  files.length ; i++) {
                String[] splittedStuff =  files[i].split("_");
                if ( splittedStuff.length > 1) {
                    if (files[i].split("_")[1].equals(User)) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }
}
