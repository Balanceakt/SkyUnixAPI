package api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import utils.FilePath;
import utils.FolderHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SkyUnixHandleWorldBlock extends FileHandle {

    /**
     * Saves block properties to a table in a file.
     *
     * @param folder The name of the folder containing the table.
     * @param table  The name of the table.
     * @param key    The key within the table.
     * @param block  The block to save.
     */
    public void saveBlock(String folder, String table, String key, Block block) {
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

            String blockKey = key;
            int count = 0;
            while (properties.containsKey(blockKey + "." + count + ".world")) {
                count++;
            }

            Location location = block.getLocation();
            properties.setProperty(blockKey + "." + count + ".world", location.getWorld().getName());
            properties.setProperty(blockKey + "." + count + ".x", String.valueOf(location.getX()));
            properties.setProperty(blockKey + "." + count + ".y", String.valueOf(location.getY()));
            properties.setProperty(blockKey + "." + count + ".z", String.valueOf(location.getZ()));
            properties.setProperty(blockKey + "." + count + ".type", block.getType().toString());
            properties.setProperty(blockKey + "." + count + ".data", block.getBlockData().getAsString());
            properties.setProperty(blockKey + "." + count + ".direction", block.getFace(block.getRelative(BlockFace.DOWN)).name());

            try (OutputStream output = new FileOutputStream(settingFile)) {
                properties.store(output, "Updated by saveBlock method");
                System.out.println("Block saved successfully: " + settingFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save block: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Loads blocks from a table in a file.
     *
     * @param folder The name of the folder containing the table.
     * @param table  The name of the table.
     * @param key    The key within the table.
     * @return A list of loaded blocks.
     */
    public List<Block> loadBlocks(String folder, String table, String key) {
        List<Block> blocks = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return blocks;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block: " + e.getMessage());
            return blocks;
        }

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".world")) {
            String worldName = properties.getProperty(blockKey + "." + count + ".world");
            double x = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".x"));
            double y = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".y"));
            double z = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".z"));

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                Block block = world.getBlockAt((int) x, (int) y, (int) z);
                if (block != null) {
                    blocks.add(block);
                } else {
                    System.err.println("Failed to load block at location: " + x + ", " + y + ", " + z);
                }
            } else {
                System.err.println("World not found: " + worldName);
            }
            count++;
        }
        return blocks;
    }

    /**
     * Retrieves a list of block types associated with the specified key from the given folder and table.
     *
     * @param folder The folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key associated with the block types to retrieve.
     * @return A list containing the block types.
     */
    public List<String> getBlockTypes(String folder, String table, String key) {
        List<String> types = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return types;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block properties: " + e.getMessage());
            return types;
        }

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".type")) {
            types.add(properties.getProperty(blockKey + "." + count + ".type"));
            count++;
        }
        return types;
    }

    /**
     * Retrieves a list of block data associated with the specified key from the given folder and table.
     *
     * @param folder The folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key associated with the block data to retrieve.
     * @return A list containing the block data.
     */
    public List<String> getBlockDataList(String folder, String table, String key) {
        List<String> dataList = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return dataList;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block properties: " + e.getMessage());
            return dataList;
        }

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".data")) {
            dataList.add(properties.getProperty(blockKey + "." + count + ".data"));
            count++;
        }
        return dataList;
    }

    public List<String> getBlockDirections(String folder, String table, String key) {
        List<String> directions = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return directions;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block properties: " + e.getMessage());
            return directions;
        }

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".direction")) {
            directions.add(properties.getProperty(blockKey + "." + count + ".direction"));
            count++;
        }
        return directions;
    }

    /**
     * Retrieves a list of block locations associated with the specified key from the given folder and table.
     *
     * @param folder The folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key associated with the block locations to retrieve.
     * @return A list containing the block locations.
     */
    public List<Location> getBlockLocations(String folder, String table, String key) {
        List<Location> locations = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return locations;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block properties: " + e.getMessage());
            return locations;
        }

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".world")) {
            String worldName = properties.getProperty(blockKey + "." + count + ".world");
            double x = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".x"));
            double y = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".y"));
            double z = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".z"));

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                locations.add(new Location(world, x, y, z));
            } else {
                System.err.println("World not found: " + worldName);
            }
            count++;
        }
        return locations;
    }

    /**
     * Retrieves a list of all block locations from the given folder and table.
     *
     * @param folder The folder containing the properties file.
     * @param table  The name of the properties file.
     * @return A list containing all block locations.
     */
    public List<Location> getAllBlockLocations(String folder, String table) {
        List<Location> locations = new ArrayList<>();
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table);
        Properties properties = new Properties();

        if (!settingFile.exists()) {
            System.err.println("File not found: " + settingFile.getPath());
            return locations;
        }

        try (InputStream input = new FileInputStream(settingFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load block properties: " + e.getMessage());
            return locations;
        }

        for (String key : properties.stringPropertyNames()) {
            if (key.endsWith(".world")) {
                String worldName = properties.getProperty(key);
                double x = Double.parseDouble(properties.getProperty(key.replace(".world", ".x")));
                double y = Double.parseDouble(properties.getProperty(key.replace(".world", ".y")));
                double z = Double.parseDouble(properties.getProperty(key.replace(".world", ".z")));

                World world = Bukkit.getWorld(worldName);
                if (world != null) {
                    locations.add(new Location(world, x, y, z));
                } else {
                    System.err.println("World not found: " + worldName);
                }
            }
        }
        return locations;
    }

    /**
     * Finds the block key associated with the given location in the specified folder and table.
     *
     * @param folder   The folder containing the properties file.
     * @param table    The name of the properties file.
     * @param location The location of the block.
     * @return The block key if found, or null if not found.
     */
    public String findBlockKeyByLocation(String folder, String table, Location location) {
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
            System.err.println("Failed to load block properties: " + e.getMessage());
            return null;
        }

        for (String key : properties.stringPropertyNames()) {
            if (key.endsWith(".world")) {
                String worldName = properties.getProperty(key);
                double x = Double.parseDouble(properties.getProperty(key.replace(".world", ".x")));
                double y = Double.parseDouble(properties.getProperty(key.replace(".world", ".y")));
                double z = Double.parseDouble(properties.getProperty(key.replace(".world", ".z")));

                World world = Bukkit.getWorld(worldName);
                if (world != null && location.getWorld().equals(world) && location.getX() == x && location.getY() == y && location.getZ() == z) {
                    // Wenn die Position übereinstimmt, geben Sie den Teil vor ".world" des Schlüssels zurück
                    int lastDotIndex = key.lastIndexOf(".");
                    return key.substring(0, lastDotIndex);
                }
            }
        }

        // Wenn keine Übereinstimmung gefunden wurde
        return null;
    }

    /**
     * Sets the block at the given location based on the block key found in the specified folder and table.
     *
     * @param folder   The folder containing the properties file.
     * @param table    The name of the properties file.
     * @param location The location where the block should be set.
     */
    public void setBlockByLocation(String folder, String table, Location location) {
        String key = findBlockKeyByLocation(folder, table, location);

        if (key != null) {
            File folderFile = new File(FilePath.folderPath, folder);
            File settingFile = new File(folderFile, table);
            Properties properties = new Properties();

            try (InputStream input = new FileInputStream(settingFile)) {
                properties.load(input);
            } catch (IOException e) {
                System.err.println("Failed to load block properties: " + e.getMessage());
                return;
            }

            String worldName = properties.getProperty(key + ".world");
            double x = Double.parseDouble(properties.getProperty(key + ".x"));
            double y = Double.parseDouble(properties.getProperty(key + ".y"));
            double z = Double.parseDouble(properties.getProperty(key + ".z"));
            String type = properties.getProperty(key + ".type");
            String data = properties.getProperty(key + ".data");
            String direction = properties.getProperty(key + ".direction");

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                Block block = world.getBlockAt((int) x, (int) y, (int) z);

                try {
                    block.setType(Material.matchMaterial(type));

                    // Blockdaten verarbeiten (falls vorhanden)
                    if (data != null && !data.isEmpty()) {
                        BlockData blockData = Bukkit.createBlockData(data);
                        block.setBlockData(blockData);
                    }

                    // Blockrichtung verarbeiten (falls vorhanden)
                    if (direction != null && !direction.isEmpty()) {
                        BlockFace blockFace = BlockFace.valueOf(direction);
                        block = block.getRelative(blockFace);
                    }
                } catch (Exception e) {
                    System.err.println("Fehler beim Setzen des Blocks: " + e.getMessage());
                }
            } else {
                System.err.println("World not found: " + worldName);
            }
        } else {
            System.err.println("Block not found at location: " + location);
        }
    }
}