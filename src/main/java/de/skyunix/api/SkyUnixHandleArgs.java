package de.skyunix.api;

import de.skyunix.utils.FilePath;

import java.io.*;
import java.util.*;

public class SkyUnixHandleArgs extends FileHandle {

    /**
     * Reads a value from a properties file located in a specified folder and table based on a provided key.
     *
     * @param folder   The name of the folder containing the properties file.
     * @param table    The name of the properties file.
     * @param key      The key whose associated value is to be retrieved.
     * @param argIndex The index of the value to retrieve if it's a comma-separated list.
     * @return The value associated with the provided key, or null if the folder, table, or key is not found,
     * or if an error occurs while reading the properties file.
     */
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

    /**
     * Sets a value for a specified key in a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key for which the value is to be set.
     * @param value  The value to set for the specified key.
     */
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

    /**
     * Sets multiple values for a specified key in a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key for which the values are to be set.
     * @param values The list of values to set for the specified key.
     */
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

    /**
     * Reads a color code value from a properties file located in a specified folder and table based on a provided key
     * and returns the color code at the specified index after converting color codes if necessary.
     *
     * @param folder   The name of the folder containing the properties file.
     * @param table    The name of the properties file.
     * @param key      The key whose associated value is to be retrieved.
     * @param argIndex The index of the value to retrieve if it's a comma-separated list.
     * @return The color code value at the specified index, or null if the folder, table, key is not found,
     * or if an error occurs while reading the properties file.
     */
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

    /**
     * Reads all values at the specified index from a properties file located in a specified folder and table
     * and returns them as a list of strings.
     *
     * @param folder   The name of the folder containing the properties file.
     * @param table    The name of the properties file.
     * @param argIndex The index of the values to retrieve if they're comma-separated lists.
     * @return A list containing values at the specified index from all keys, or null if the folder or table is not found,
     * or if an error occurs while reading the properties file.
     */
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

    /**
     * Reads the number of keys (entries) in a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @return The number of keys in the properties file, or 0 if the folder or table is not found,
     * or if an error occurs while reading the properties file.
     */
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

    /**
     * Reads all keys (entries) from a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @return A set containing all keys from the properties file, or null if the folder or table is not found,
     * or if an error occurs while reading the properties file.
     */
    public Set<String> readTableKeys(final String folder, final String table) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        Set<String> keys = null;

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
                keys = properties.stringPropertyNames();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading properties: " + e.getMessage());
        }

        return keys;
    }

    /**
     * Converts color codes in a string by replacing '&' with '§'.
     *
     * @param input The string containing color codes to be converted.
     * @return The input string with color codes converted.
     */
    private String convertColorCodes(String input) {
        return input.replace("&", "§");
    }
}