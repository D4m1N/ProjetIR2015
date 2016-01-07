package com.example.damien.projetir;

import android.os.Environment;

import com.example.damien.projetir.Tests.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

/**
 * Created by Pascal on 04/01/2016. for IR
 */
public class InitialisationFile {

    //private FileManager fileM;
    private String _name;

    public InitialisationFile(String name){
        this._name = name;
    }

    private List<String> GetFileContent()
    {
        String filename = Environment.getExternalStorageDirectory() + "/Tests/testFiles.txt";
        FileInputStream inputStream;
        List<String> fileContent = new Vector<>();
        File file;
        try{
            file = new File(filename);


            inputStream  = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    private int NameIsPresent(List<String> fileContent){
        for(int i=0; i<fileContent.size(); i++) {
            String oneLine = fileContent.get(i);
            if (oneLine.split(":").length > 0) {
                if (oneLine.split(":")[0].equals(_name)) {
                    return i;
                }
            }
        }
        return -1;
    }


    public boolean addName()
    {
        List<String> fileContent = GetFileContent();

        if (NameIsPresent(fileContent) == -1){
            if (fileContent.size() == 0)
                fileContent.add(_name+":000000000000000000000111111111111111111111");
            else {
                String oneLine = fileContent.get(0);
                if (oneLine.split(":").length > 1) {
                    if (oneLine.split(":")[1].charAt(1) == '0') {
                        fileContent.add(_name+":000000000000000000000111111111111111111111");
                    }else{
                        fileContent.add(_name+":111111111111111111111000000000000000000000");
                    }
                }
            }
            writeFile(fileContent);
            return true;
        }

        return false;

    }

    public char getCurrentTest()
    {
        List<String> fileContent = GetFileContent();

        int namePosition = NameIsPresent(fileContent);

        if (namePosition != -1) {
            FileManager fileM = new FileManager(_name);
            return fileContent.get(namePosition).split(":")[1].charAt(fileM.getTestNumber(_name));
        }

        return ' ';
    }

    public boolean writeFile(List<String> fileContent)
    {
        String filename = Environment.getExternalStorageDirectory() + "/Tests/testFiles.txt";
        FileOutputStream outputStream = null;
        File file;
        try{
            file = new File(filename);
            outputStream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile(); //
            }
            else
            {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }

            String newFileContent = "";
            for(int i=0; i<fileContent.size(); i++) {
                newFileContent += fileContent.get(i) + "\n";
            }

            byte[] contentInBytes = newFileContent.getBytes();

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
}
