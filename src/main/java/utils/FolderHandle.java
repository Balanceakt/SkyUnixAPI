package utils;

import java.io.File;

public class FolderHandle {
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
