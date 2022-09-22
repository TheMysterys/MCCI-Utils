package com.themysterys.mcciutils.util.updates;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.themysterys.mcciutils.McciUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class UpdateChecker {

    public boolean isUpdateAvailable;
    public String latestVersion;

    public UpdateChecker() {
        JsonArray json;
        try {
            json = readJsonFromUrl("https://api.modrinth.com/v2/project/DJ1mNMjS/version");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        isUpdateAvailable = !json.get(0).getAsJsonObject().get("version_number").getAsString().equals(McciUtils.modVersion);
        latestVersion = json.get(0).getAsJsonObject().get("version_number").getAsString();
    }

    public static JsonArray readJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            JsonElement root = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return root.getAsJsonArray();
        }
    }
}
