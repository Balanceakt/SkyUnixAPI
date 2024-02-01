package api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import utils.FilePath;
import utils.FolderHandle;

import java.io.*;
import java.util.Properties;

public class SkyUnixHandleLocation {
    public SkyUnixHandleLocation() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }

    public void saveLocation(String folder, String table, String key, Location location) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        try {
            if (!folderFile.exists() && !folderFile.mkdirs()) {
                System.err.println("Failed to create folder: " + folderFile.getAbsolutePath());
                return;
            }
            if (!settingFile.exists() && !settingFile.createNewFile()) {
                System.err.println("Failed to create file: " + settingFile.getPath());
                return;
            }
            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String locationKey = key;
            properties.setProperty(locationKey + ".world", location.getWorld().getName());
            properties.setProperty(locationKey + ".x", String.valueOf(location.getX()));
            properties.setProperty(locationKey + ".y", String.valueOf(location.getY()));
            properties.setProperty(locationKey + ".z", String.valueOf(location.getZ()));
            properties.setProperty(locationKey + ".yaw", String.valueOf(location.getYaw()));
            properties.setProperty(locationKey + ".pitch", String.valueOf(location.getPitch()));
            try (OutputStream output = new FileOutputStream(settingFile)) {
                properties.store(output, "Updated by saveLocation method");
                System.out.println("Location saved successfully: " + settingFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save location: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public Location loadLocation(String folder, String table, String key) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return null;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load location: " + e.getMessage());
            return null;
        }
        System.out.println("Loaded from file: " + settingFile.getAbsolutePath());
        System.out.println("Existing properties:");
        properties.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
        String locationKey = key;
        double x = Double.parseDouble(properties.getProperty(locationKey + ".x"));
        double y = Double.parseDouble(properties.getProperty(locationKey + ".y"));
        double z = Double.parseDouble(properties.getProperty(locationKey + ".z"));
        float yaw = Float.parseFloat(properties.getProperty(locationKey + ".yaw"));
        float pitch = Float.parseFloat(properties.getProperty(locationKey + ".pitch"));
        String worldName = properties.getProperty(locationKey + ".world");
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return new Location(world, x, y, z, yaw, pitch);
        } else {
            System.err.println("World not found: " + worldName);
            return null;
        }
    }

    public World getWorldForLocation(String folder, String table, String key) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return null;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load location: " + e.getMessage());
            return null;
        }
        String locationKey = key;
        String worldName = properties.getProperty(locationKey + ".world");
        return Bukkit.getWorld(worldName);
    }

    public static double[] getCoordinates(String folder, String table, String key) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();
        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return null;
        }
        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load coordinates: " + e.getMessage());
            return null;
        }

        String locationKey = key;
        String xStr = properties.getProperty(locationKey + ".x");
        String yStr = properties.getProperty(locationKey + ".y");
        String zStr = properties.getProperty(locationKey + ".z");

        if (xStr != null && yStr != null && zStr != null) {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double z = Double.parseDouble(zStr);

            return new double[]{x, y, z};
        } else {
            System.err.println("Coordinates not found for key: " + key);
            return null;
        }
    }
}
