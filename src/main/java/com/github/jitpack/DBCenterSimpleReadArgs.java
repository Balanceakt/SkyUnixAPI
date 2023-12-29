package com.github.jitpack;

import utils.FilePath;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class DBCenterSimpleReadArgs {
    public DBCenterSimpleReadArgs() {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        File folder = new File(FilePath.folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Folder created: " + FilePath.folderPath);
            } else {
                System.err.println("Failed to create folder: " + FilePath.folderPath);
            }
        }
    }
    public String readSimpleArgs(String table, String key, int argIndex) {
        File settingFile = new File(FilePath.folderPath, table);
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);

            String value = properties.getProperty(key);

            String[] valuesArray = value.split(",");

            if (argIndex >= 0 && argIndex < valuesArray.length) {
                return valuesArray[argIndex];
            } else {
                System.out.println("Index out of range for key: " + key);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}