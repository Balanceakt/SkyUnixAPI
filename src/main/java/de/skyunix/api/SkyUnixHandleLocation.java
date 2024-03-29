package de.skyunix.api;

import de.skyunix.api.abstraction.FileHandle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import de.skyunix.utils.FilePath;

import java.io.*;
import java.util.Properties;

public class SkyUnixHandleLocation extends FileHandle {

    /**
     * Retrieves coordinates (x, y, z) from a properties file located in a specified folder and table
     * based on the provided key.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key corresponding to the coordinates to be retrieved.
     * @return An array containing the x, y, and z coordinates, or null if the folder, table, or key is not found,
     * or if an error occurs while reading the properties file.
     */
    public double[] getCoordinates(String folder, String table, String key) {
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

    /**
     * Saves a location to a properties file located in a specified folder and table, associated with a given key.
     *
     * @param folder   The name of the folder containing the properties file.
     * @param table    The name of the properties file.
     * @param key      The key corresponding to the location to be saved.
     * @param location The location to be saved.
     */
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

    /**
     * Loads a location from a properties file located in a specified folder and table, associated with a given key.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key corresponding to the location to be loaded.
     * @return The loaded location, or null if the folder, table, or key is not found,
     * or if an error occurs while reading the properties file.
     */
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

    /**
     * Retrieves the world associated with a location stored in a properties file located in a specified folder and table,
     * based on the provided key.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key corresponding to the location for which the world is to be retrieved.
     * @return The world associated with the location, or null if the folder, table, or key is not found,
     * or if an error occurs while reading the properties file.
     */
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
}
