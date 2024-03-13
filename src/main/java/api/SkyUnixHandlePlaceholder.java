package api;

import utils.FilePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SkyUnixHandlePlaceholder extends FileHandle {
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