package io.loot.lootsdk.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gabrielsamojlo on 10.01.2018.
 */

public class CommonUtils {

    public static String loadJsonFromAssets(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
