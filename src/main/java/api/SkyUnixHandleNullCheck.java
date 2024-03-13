package api;

import utils.FilePath;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SkyUnixHandleNullCheck extends FileHandle {

    /**
     * Checks if a specified folder exists.
     *
     * @param folder The name of the folder to check.
     * @return True if the folder exists and is a directory, otherwise false.
     */
    public boolean isFolderExists(String folder) {
        File folderFile = new File(FilePath.folderPath, folder);
        return folderFile.exists() && folderFile.isDirectory();
    }

    /**
     * Checks if a specified table exists within a folder.
     *
     * @param folder The name of the folder containing the table.
     * @param table  The name of the table to check.
     * @return True if both the folder and table exist, otherwise false.
     */
    public boolean isTableExists(String folder, String table) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        return folderFile.exists() && settingFile.exists();
    }

    /**
     * Checks if a specified key exists within a table.
     *
     * @param folder The name of the folder containing the table.
     * @param table  The name of the table.
     * @param key    The key to check for existence.
     * @return True if the key exists in the table, otherwise false.
     */
    public boolean isKeyExists(String folder, String table, String key) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!folderFile.exists() || !settingFile.exists()) {
            return false;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
            String value = properties.getProperty(key);
            return value != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a specified value exists at the given index within a table.
     *
     * @param folder       The name of the folder containing the table.
     * @param table        The name of the table.
     * @param key          The key within the table.
     * @param indexToCheck The index of the value to check.
     * @return True if the value exists at the specified index in the table, otherwise false.
     */
    public boolean isValueExists(String folder, String table, String key, int indexToCheck) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!folderFile.exists() || !settingFile.exists()) {
            return false;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
            String existingValues = properties.getProperty(key);
            if (existingValues != null) {
                List<String> currentValues = new ArrayList<>(Arrays.asList(existingValues.split(",")));
                return indexToCheck >= 0 && indexToCheck < currentValues.size();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a specified value exists within any key-value pair in a table.
     *
     * @param folder       The name of the folder containing the table.
     * @param table        The name of the table.
     * @param valueToCheck The value to check for existence.
     * @return True if the value exists in any key-value pair in the table, otherwise false.
     */
    public boolean isValueExistsInTable(String folder, String table, String valueToCheck) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!folderFile.exists() || !settingFile.exists()) {
            return false;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);

            for (String key : properties.stringPropertyNames()) {
                String values = properties.getProperty(key);

                if (values != null) {
                    if (values.contains(valueToCheck)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
