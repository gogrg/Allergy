package com.example.allergi.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JSONConverter {
    private static Gson gson = new Gson();


    public static String toString (Object object) {
        return gson.toJson(object);
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static <T> T toObject(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
