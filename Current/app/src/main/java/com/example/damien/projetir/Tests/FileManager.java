package com.example.damien.projetir.Tests;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pascal on 04/12/2015.
 */
public class FileManager
{

    private String _login;

    public static final String  PATH = Environment.getExternalStorageDirectory() + "/Tests/Users/";

    public FileManager()
    {
        _login = "";
    }

    public FileManager(String login)
    {
        _login = login;
    }

    private String search_folder_from_login()
    {
        File dir = new File(PATH);
        String[] files = dir.list();

        String name = "";

        if (files != null) {
            for (int i = 0; i <  files.length ; i++) {
                String[] splittedStuff = files[i].split("_");
                if (splittedStuff.length > 1)
                {
                    name = splittedStuff[1];
                    Log.i("Info", name);
                    if (name.equals(_login))
                        return files[i];
                }
            }
        }

        return "";
    }

    public void set_login(String login)
    {
        _login = login;
    }

    public boolean writeFile(Bundle bundle)
    {
        String filename = PATH + search_folder_from_login() + "/" + getTestNumber(_login) + ".txt";
        FileOutputStream outputStream = null;
        File file;
        try{
            file = new File(filename);
            outputStream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = bundle.toString().getBytes();

            outputStream.write(contentInBytes);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public String[] getUserNames()
    {
        File dir = new File(PATH);
        String[] files = dir.list();


        if (files != null) {
            for (int i = 0; i <  files.length ; i++) {
                files[i] = files[i].split("_")[1];
            }
        }

        return files;
    }

    public int getTestNumber(String User)
    {
        File dir = new File(PATH);
        String[] files = dir.list();


        if (files != null) {
            for (int i = 0; i <  files.length ; i++) {
                String[] splittedStuff =  files[i].split("_");
                if ( splittedStuff.length > 1) {
                    if (files[i].split("_")[1].equals(User)) {
                        File dirUser = new File(PATH + files[i]);
                        String[] filesUser = dirUser.list();
                        if (filesUser != null) {
                            return filesUser.length;
                        } else
                            return 0;
                    }
                }
            }
        }

        return -1;
    }
}
