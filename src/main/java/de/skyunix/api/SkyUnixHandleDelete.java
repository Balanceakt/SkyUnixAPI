package de.skyunix.api;

import de.skyunix.api.abstraction.FileHandle;
import de.skyunix.utils.FilePath;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SkyUnixHandleDelete extends FileHandle {

    /**
     * Recursively deletes all contents of a given folder.
     *
     * @param folder The folder whose contents are to be deleted.
     * @return True if all contents were successfully deleted, false otherwise.
     */
    private static boolean deleteContents(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (!deleteContents(file)) {
                        return false;
                    }
                } else {
                    if (!file.delete()) {
                        return false;
                    }
                }
            }
        }
        return folder.delete();
    }

    /**
     * Deletes a value at a specified index for a given key in a properties file located in a specified folder and table.
     *
     * @param folder        The name of the folder containing the properties file.
     * @param table         The name of the properties file.
     * @param key           The key for which the value is to be removed.
     * @param indexToRemove The index of the value to remove if it's a comma-separated list.
     */
    public void deleteArgValue(final String folder, final String table, final String key, final int indexToRemove) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!folderFile.exists() && !folderFile.mkdirs()) {
            System.err.println("Failed to create folder: " + folderFile.getAbsolutePath());
            return;
        }
        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getAbsolutePath());
            return;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
            String existingValues = properties.getProperty(key);
            if (existingValues != null) {
                List<String> currentValues = new ArrayList<>(Arrays.asList(existingValues.split(",")));
                if (indexToRemove >= 0 && indexToRemove < currentValues.size()) {
                    currentValues.remove(indexToRemove);
                    properties.setProperty(key, String.join(",", currentValues));
                } else {
                    System.err.println("Index out of range for key: " + key);
                    return;
                }
            } else {
                System.err.println("Key not found: " + key);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (OutputStream output = new FileOutputStream(settingFile)) {
            properties.store(output, "Updated by removeSimpleArgValueByIndex method");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a key and its associated value from a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key to be deleted.
     */
    public void deleteKey(final String folder, final String table, final String key) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.remove(key);
        try (OutputStream output = new FileOutputStream(settingFile)) {
            properties.store(output, "Updated by deleteEntry method");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a properties file located in a specified folder.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file to be deleted.
     */
    public void deleteTable(final String folder, final String table) {
        File folderFile = new File(FilePath.folderPath, folder);
        File fileToDelete = new File(folderFile, table);
        try {
            if (fileToDelete.exists() && fileToDelete.delete()) {
                System.out.println("File deleted: " + fileToDelete.getAbsolutePath());
            } else {
                System.err.println("Failed to delete file: " + fileToDelete.getAbsolutePath());
            }
        } catch (SecurityException e) {
            System.err.println("SecurityException: " + e.getMessage());
        }
    }

    /**
     * Deletes a folder and its contents located in a specified path.
     *
     * @param folder The name of the folder to be deleted.
     */
    public void deleteFolder(final String folder) {
        File folderToDelete = new File(FilePath.folderPath, folder);
        try {
            if (folderToDelete.exists() && folderToDelete.isDirectory()) {
                if (deleteContents(folderToDelete)) {
                    System.out.println("Folder and its contents deleted: " + folderToDelete.getAbsolutePath());
                } else {
                    System.err.println("Failed to delete folder and its contents: " + folderToDelete.getAbsolutePath());
                }
            } else {
                System.err.println("Folder not found: " + folderToDelete.getAbsolutePath());
            }
        } catch (SecurityException e) {
            System.err.println("SecurityException: " + e.getMessage());
        }
    }
}
