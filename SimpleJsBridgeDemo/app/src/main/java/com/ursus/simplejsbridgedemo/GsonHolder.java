package com.ursus.simplejsbridgedemo;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;

public class GsonHolder {

    private Gson gson;
    private static GsonHolder instance = new GsonHolder();
    private GsonHolder() {
        gson = createGson();
    }

    public static GsonHolder getInstance() {
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement arg0, Type arg1,
                                    JsonDeserializationContext arg2) throws JsonParseException {
                return null;
            }
        });
        return builder.addDeserializationExclusionStrategy(exclusionStrategy)
                .addSerializationExclusionStrategy(exclusionStrategy)
                .create();
    }

    ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz == Field.class || clazz == Method.class;
        }
    };
}
