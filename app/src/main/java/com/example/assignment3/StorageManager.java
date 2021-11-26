package com.example.assignment3;

import android.app.Activity;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageManager {
    String filename = "history.txt";

    public void resetHistory(Activity activity){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            //fileOutputStream.write("0+0".getBytes());

        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void saveHistory(Activity activity, int history, int correctHistory){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write((correctHistory+"+"+history).getBytes());

        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public String getHistory(Activity activity)  {
        FileInputStream fileInputStream = null;
        int read;
        String history = "";
        StringBuilder buffer = new StringBuilder();
        try {
            fileInputStream = activity.openFileInput(filename);
            while(( read = fileInputStream.read() )!= -1 ){
                buffer.append((char)read);
            }
            history  =  buffer.toString();
        }catch (IOException ex){ex.printStackTrace();
        }
        finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            }catch (IOException ex){ex.printStackTrace();}
        }
        return history;
    }
}
