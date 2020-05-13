package com.example.skeleton.services;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String code = json.getAsString();
            return code.toLowerCase().equals("true") || code.toLowerCase().equals("1");
        } catch (UnsupportedOperationException ex) {
            int code = json.getAsInt();

            return code == 1;
        }
    }
}