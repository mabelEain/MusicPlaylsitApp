package com.mabel.collections.playlists.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mabel.collections.playlists.Model.Track;
import com.mabel.collections.playlists.Model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MabelEain on 8/29/2017.
 */

public class AppPreference {
    public static final String PREF_NAME = "MabelCollection";
    public static final String TOKEN_KEY = "TOKEN_KEY";
    public static final String USER_OBJ = "USER_OBJ";
    public static final String TRACK_OBJ = "TRACK_OBJ";

    public AppPreference() {
        super();
    }

    public static String getTokenKey(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        return pref.getString(TOKEN_KEY, token);
    }

    public static void setTokenKey(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static void setUserObject(Context context, User user) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String jsonObjects = gson.toJson(user);
        editor.putString(USER_OBJ, jsonObjects);
        editor.commit();
    }

    public static User getUserObject(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        Gson gson = new Gson();
        User user = gson.fromJson(pref.getString(USER_OBJ, ""),
                User.class);
        return user;
    }

    public static void setTrackObj(Context context,List<Track> tracks) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String jsonObjects = gson.toJson(tracks);
        editor.putString(TRACK_OBJ, jsonObjects);
        editor.commit();
    }

    public static List<Track> getTrackList(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        Gson gson = new Gson();
        Track[] tracks = gson.fromJson(pref.getString(TRACK_OBJ, ""),
                Track[].class);
        return Arrays.asList(tracks);
    }
}
