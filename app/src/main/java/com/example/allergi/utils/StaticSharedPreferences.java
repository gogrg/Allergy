package com.example.allergi.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

public class StaticSharedPreferences {
    private static SharedPreferences preferences;


    public static <T> void putObject (String fileSetting, String key, T object, Context context) {
        String json = JSONConverter.toString(object);

        preferences = context.getSharedPreferences(fileSetting, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, json);
        editor.apply();
    }

    public static <T> T getObject (String fileSetting, String key, Class<T> tClass, Context context) {
        preferences = context.getSharedPreferences(fileSetting, MODE_PRIVATE);

        String json = preferences.getString(key, null);

        if (json != null) {
            return JSONConverter.toObject(json, tClass);
        }

        throw new IllegalArgumentException("Object with key " + key + " not found");
    }

    public static <T> T getObject (String fileSetting, String key, Type type, Context context) {
        preferences = context.getSharedPreferences(fileSetting, MODE_PRIVATE);

        String json = preferences.getString(key, null);

        if (json != null) {
            return JSONConverter.toObject(json, type);
        }

        throw new IllegalArgumentException("Object with key " + key + " not found");
    }

    public static void removeObject(String fileSetting, String key, Context context) {
        preferences = context.getSharedPreferences(fileSetting, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(key);
        editor.apply();
    }
}
