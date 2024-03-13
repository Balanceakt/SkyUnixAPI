package utils;

import java.io.File;

/**
 * Utility class for handling folders.
 */
public class FolderHandle {

    /**
     * Checks if a folder exists at the given path and creates it if it doesn't exist.
     *
     * @param folderPath The path to the folder to check/create.
     */
    public static void folderCheck(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Folder created: " + folderPath);
            } else {
                System.err.println("Failed to create folder: " + folderPath);
            }
        }
    }
}
