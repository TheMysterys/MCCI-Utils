package com.themysterys.mcciutils.util.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ModConfig {

    private static final File folder = new File("config/MCCI-Utils");
    private static final File legacyFolder = new File("config");
    private static File configFile;
    private static final Gson config = new GsonBuilder().setPrettyPrinting().create();
    public static ConfigInstance INSTANCE;

    public static void init() {
        loadDefaults();
        generateFoldersAndFiles();
        readJson();
        if (INSTANCE.customDetails == null) {
            INSTANCE.customDetails = ConfigInstance.RPCustomDetails.IP;
        }
        writeJson(); //Write to file new config options
    }

    public static void loadDefaults() {
        INSTANCE = new ConfigInstance();
    }

    private static void generateFoldersAndFiles() {
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (folder.isDirectory()) {
            configFile = new File(folder, "mcciutils.json");

            File legacyConfigFile = new File(legacyFolder, "mcciutils.json");
            if (legacyConfigFile.exists() && legacyConfigFile.isFile() && ! configFile.exists()) {
                if (!legacyConfigFile.renameTo(configFile)) {
                    System.err.format("[MCCI Utils] Could not rename legacy config file %s to %s\n", legacyConfigFile.getAbsolutePath(), configFile.getAbsolutePath());
                } else {
                    System.out.println("[MCCI Utils] Successfully converted legacy config file");
                }
            }


            if (!configFile.exists()) {
                System.out.println("[MCCI Utils] Creating new config file");
                try {
                    configFile.createNewFile();
                    loadDefaults();
                    String json = config.toJson(INSTANCE);
                    FileWriter writer = new FileWriter(configFile);
                    writer.write(json);
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalStateException("[MCCI Utils] Can't create config file", e);
                }
            } else if (configFile.isDirectory()) {
                throw new IllegalStateException("[MCCI Utils] 'mcciutils.json' must be a file!");
            }
        } else {
            throw new IllegalStateException("[MCCI Utils] 'config/MCCI-Utils' must be a folder!");
        }
    }

    public static void readJson() {
        try {
            INSTANCE = config.fromJson(new FileReader(configFile), ConfigInstance.class);
            if (INSTANCE == null) {
                throw new IllegalStateException("[MCCI Utils] Null configuration");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[MCCI Utils] Invalid configuration!");
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // No op
        }
    }

    public static void writeJson() {
        try {
            String json = config.toJson(INSTANCE);
            FileWriter writer = new FileWriter(configFile, false);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException("[MCCI Utils] Can't update config file", e);
        }
    }

}