package com.themysterys.mcciutils.util.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIUtils {

    public static JsonArray readJsonArrayFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            JsonElement root = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return root.getAsJsonArray();
        }
    }

    public static JsonObject readJsonObjectFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            JsonElement root = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return root.getAsJsonObject();
        }
    }
}
