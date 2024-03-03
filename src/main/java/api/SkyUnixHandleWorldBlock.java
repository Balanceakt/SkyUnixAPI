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

public class SkyUnixHandleWorldBlock {

    public SkyUnixHandleWorldBlock() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }

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
            properties.setProperty(blockKey + "." + count + ".data", String.valueOf(block.getBlockData().getAsString()));
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

    public Block getBlockAtCoordinate(String folder, String table, String key, Location coordinate) {
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

        String blockKey = key;
        int count = 0;
        while (properties.containsKey(blockKey + "." + count + ".world")) {
            String worldName = properties.getProperty(blockKey + "." + count + ".world");
            double x = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".x"));
            double y = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".y"));
            double z = Double.parseDouble(properties.getProperty(blockKey + "." + count + ".z"));

            if (worldName.equals(coordinate.getWorld().getName()) && x == coordinate.getX() && y == coordinate.getY() && z == coordinate.getZ()) {
                World world = Bukkit.getWorld(worldName);
                if (world != null) {
                    return world.getBlockAt((int) x, (int) y, (int) z);
                } else {
                    System.err.println("World not found: " + worldName);
                    return null;
                }
            }

            count++;
        }

        System.err.println("Block not found at coordinate: " + coordinate);
        return null;
    }
}
