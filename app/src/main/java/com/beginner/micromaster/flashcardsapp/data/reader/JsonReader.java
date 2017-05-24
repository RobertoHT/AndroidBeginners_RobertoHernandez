package com.beginner.micromaster.flashcardsapp.data.reader;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by praxis on 12/04/17.
 */

public class JsonReader {
    public static String loadJsonFromAsset(Context context, String fileName){
        String json;

        try{
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return json;
    }
}
