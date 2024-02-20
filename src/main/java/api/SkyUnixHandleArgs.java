package api;

import utils.FilePath;
import utils.FolderHandle;

import java.io.*;
import java.util.*;

public class SkyUnixHandleArgs {
    public SkyUnixHandleArgs() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }
    public String readSimpleArgs(final String folder, final String table, final String key, int argIndex) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try {
            if (!folderFile.exists()) {
                System.out.println("Folder does not exist: " + folderFile.getAbsolutePath());
                return null;
            }
            if (!settingFile.exists()) {
                System.out.println("Table does not exist: " + settingFile.getAbsolutePath());
                return null;
            }

            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
                String value = properties.getProperty(key);
                if (value != null) {
                    String[] valuesArray = value.split(",");
                    if (argIndex >= 0 && argIndex < valuesArray.length) {
                        return valuesArray[argIndex];
                    } else {
                        System.out.println("Index out of range for key: " + key);
                        return null;
                    }
                } else {
                    System.out.println("Key not found: " + key);
                    return null;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading properties: " + e.getMessage());
            return null;
        }
    }

    public void setSimpleArgValue(final String folder, final String table, final String key, final String value) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try {
            if (!folderFile.exists() && !folderFile.mkdirs()) {
                throw new IOException("Failed to create folder: " + folderFile.getAbsolutePath());
            }
            if (!settingFile.exists() && !settingFile.createNewFile()) {
                throw new IOException("Failed to create file: " + settingFile.getAbsolutePath());
            }
            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
            }
            properties.setProperty(key, value);
            try (OutputStream output = new FileOutputStream(settingFile)) {
                properties.store(output, "Updated by setSetting method");
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void setSimpleArgsValues(final String folder, final String table, final String key, final List<String> values) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!folderFile.exists() && !folderFile.mkdirs()) {
            System.err.println("Failed to create folder: " + folderFile.getAbsolutePath());
            return;
        }
        if (!settingFile.exists()) {
            try {
                if (settingFile.createNewFile()) {
                    System.out.println("File created: " + settingFile.getAbsolutePath());
                } else {
                    System.err.println("Failed to create file: " + settingFile.getAbsolutePath());
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
            properties.setProperty(key, String.join(",", values));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (OutputStream output = new FileOutputStream(settingFile)) {
            properties.store(output, "Updated by setSimpleArgsValues method");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readColorCodes(final String folder, final String table, final String key, int argIndex) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
            String value = properties.getProperty(key);
            if (value != null) {
                String[] valuesArray = value.split(",");
                if (argIndex >= 0 && argIndex < valuesArray.length) {
                    return convertColorCodes(valuesArray[argIndex]);
                } else {
                    System.out.println("Index out of range for key: " + key);
                    return null;
                }
            } else {
                System.out.println("Key not found: " + key);
                return null;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading properties: " + e.getMessage());
            return null;
        }
    }

    public List<String> readAllArgsAtIndex(final String folder, final String table, int argIndex) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try {
            if (!folderFile.exists()) {
                System.out.println("Folder does not exist: " + folderFile.getAbsolutePath());
                return null;
            }
            if (!settingFile.exists()) {
                System.out.println("Table does not exist: " + settingFile.getAbsolutePath());
                return null;
            }
            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
                List<String> valuesAtIndex = new ArrayList<>();
                properties.forEach((key, value) -> {
                    String[] valuesArray = value.toString().split(",");
                    if (argIndex >= 0 && argIndex < valuesArray.length) {
                        valuesAtIndex.add(valuesArray[argIndex]);
                    } else {
                        System.out.println("Index out of range for key: " + key);
                    }
                });
                return valuesAtIndex;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading properties: " + e.getMessage());
            return null;
        }
    }

    public int readTableCountKeys(final String folder, final String table) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try {
            if (!folderFile.exists()) {
                System.out.println("Folder does not exist: " + folderFile.getAbsolutePath());
                return 0;
            }
            if (!settingFile.exists()) {
                System.out.println("Table does not exist: " + settingFile.getAbsolutePath());
                return 0;
            }

            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
                return properties.size();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading properties: " + e.getMessage());
            return 0;
        }
    }

    private String convertColorCodes(String input) {
        return input.replace("&", "§");
    }
}