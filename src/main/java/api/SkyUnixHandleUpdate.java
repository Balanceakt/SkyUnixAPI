package api;

import utils.FilePath;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SkyUnixHandleUpdate extends FileHandle {
    public void replaceArgValue(final String folder, final String table, final String key, final int indexToReplace, final String newValue) {
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
                if (indexToReplace >= 0 && indexToReplace < currentValues.size()) {
                    currentValues.set(indexToReplace, newValue);
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
            properties.store(output, "Updated by replaceArgValue method");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
