package utils;

import java.io.File;

/**
 * Utility class for handling file paths.
 */
public class FilePath {

    /**
     * The path to the folder where SkyUnix data is stored.
     */
    public static final String folderPath = System.getProperty("user.dir") + File.separator + ".." + File.separator + ".." + File.separator + "SkyUnixData";
}
