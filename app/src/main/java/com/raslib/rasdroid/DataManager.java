package com.raslib.rasdroid;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Data Manager class to get/save data in file
 */
public class DataManager {
    /**
     * Save data in file from filename and Application context
     * @param filename - File to save data
     * @param object - Data to save
     * @param context - Application context
     */
    public static void saveData(String filename, Object object, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream outputStream;

            try {
                outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.writeObject(object);
                outputStream.flush();
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get data in file from filename and Application context
     * @param filename - File to get data
     * @param context - Application context
     * @return object - retrieved data - returns null on error
     */
    public static Object getData(String filename, Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream inputStream;

            try {
                inputStream = new ObjectInputStream(fileInputStream);
                try {
                    Object object = inputStream.readObject();
                    inputStream.close();
                    return object;
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
