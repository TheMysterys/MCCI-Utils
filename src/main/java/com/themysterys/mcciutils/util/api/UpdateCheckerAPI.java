package com.themysterys.mcciutils.util.api;

import com.google.gson.JsonArray;
import com.themysterys.mcciutils.McciUtils;

import java.io.IOException;
import java.net.UnknownHostException;

import static com.themysterys.mcciutils.util.api.APIUtils.readJsonArrayFromUrl;


public class UpdateCheckerAPI {

    public boolean isUpdateAvailable;
    public String latestVersion;

    public UpdateCheckerAPI() {
        JsonArray json;
        try {
            json = readJsonArrayFromUrl("https://api.modrinth.com/v2/project/DJ1mNMjS/version");
        } catch (UnknownHostException e){
            McciUtils.LOGGER.warn("Unable to check for update");
            json = null;
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (json == null) {
            return;
        }
        isUpdateAvailable = !json.get(0).getAsJsonObject().get("version_number").getAsString().equals(McciUtils.modVersion);
        latestVersion = json.get(0).getAsJsonObject().get("version_number").getAsString();
    }
}
