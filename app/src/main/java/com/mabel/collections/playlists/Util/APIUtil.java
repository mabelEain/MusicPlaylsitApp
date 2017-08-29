package com.mabel.collections.playlists.Util;

import com.google.gson.JsonObject;

/**
 * Created by MabelEain on 8/27/2017.
 */
public class APIUtil {

    public static String getGsonAsString(JsonObject jsonObj, String key, String default_value) {
        String to_return = default_value;
        if (jsonObj.get(key) != null && !jsonObj.get(key).isJsonNull()) {
            to_return = jsonObj.get(key).getAsString();
        }
        return to_return;
    }

    public static int getGsonAsInt(JsonObject jsonObj, String key, int default_value) {
        int to_return = default_value;
        if (jsonObj.get(key) != null && !jsonObj.get(key).isJsonNull()) {
            to_return = jsonObj.get(key).getAsInt();
        }
        return to_return;
    }

    public static float getGsonAsFloat(JsonObject jsonObj, String key, float default_value) {
        float to_return = default_value;
        if (jsonObj.get(key) != null && !jsonObj.get(key).isJsonNull()) {
            to_return = jsonObj.get(key).getAsFloat();
        }
        return to_return;
    }

    public static double getGsonAsDouble(JsonObject jsonObj, String key, double default_value) {
        double to_return = default_value;
        if (jsonObj.get(key) != null && !jsonObj.get(key).isJsonNull()) {
            to_return = jsonObj.get(key).getAsDouble();
        }
        return to_return;
    }

}
