package de.skyunix.api;

import de.skyunix.api.abstraction.FileHandle;
import de.skyunix.utils.FilePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SkyUnixHandlePlaceholder extends FileHandle {

    /**
     * Reads a message with placeholders from a specified table and key within a folder.
     *
     * @param folder       The name of the folder containing the table.
     * @param table        The name of the table.
     * @param key          The key corresponding to the message in the table.
     * @param argIndex     The index of the message in the comma-separated values.
     * @param placeholders Optional placeholders to replace in the message.
     * @return The message with replaced placeholders, or null if the folder, table, key, or message is not found.
     */
    public String readMessageWithPlaceholders(String folder, String table, String key, int argIndex, String... placeholders) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        try {
            if (!folderFile.exists() || !settingFile.exists()) {
                System.out.println("Folder or table does not exist: " + folderFile.getAbsolutePath());
                return null;
            }
            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
                String value = properties.getProperty(key);
                if (value != null) {
                    String[] valuesArray = value.split(",");
                    if (argIndex >= 0 && argIndex < valuesArray.length) {
                        String messageWithPlaceholders = valuesArray[argIndex];
                        return replacePlaceholders(messageWithPlaceholders, placeholders);
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
     * Replaces placeholders in a message with specified replacements.
     *
     * @param messageWithPlaceholders The message containing placeholders.
     * @param replacements            The replacements for the placeholders.
     * @return The message with replaced placeholders.
     */
    private String replacePlaceholders(String messageWithPlaceholders, String... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                String placeholder = replacements[i];
                String replacement = replacements[i + 1];
                messageWithPlaceholders = messageWithPlaceholders.replace(placeholder, replacement);
            }
        }
        return messageWithPlaceholders;
    }
}