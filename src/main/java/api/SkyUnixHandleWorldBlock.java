package api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import utils.FilePath;
import utils.FolderHandle;

import java.io.*;
import java.util.Properties;

public class SkyUnixHandleWorldBlock {

    public SkyUnixHandleWorldBlock() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }

    public class BlockStorageHandler {
        public BlockStorageHandler() {
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
                Location location = block.getLocation();
                properties.setProperty(blockKey + ".world", location.getWorld().getName());
                properties.setProperty(blockKey + ".x", String.valueOf(location.getX()));
                properties.setProperty(blockKey + ".y", String.valueOf(location.getY()));
                properties.setProperty(blockKey + ".z", String.valueOf(location.getZ()));
                properties.setProperty(blockKey + ".type", block.getType().toString());
                properties.setProperty(blockKey + ".data", String.valueOf(block.getBlockData().getAsString()));
                properties.setProperty(blockKey + ".direction", block.getFace(block.getRelative(BlockFace.DOWN)).name());

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


        public Block loadBlock(String folder, String table, String key) {
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
                System.err.println("Failed to load block: " + e.getMessage());
                return null;
            }
            System.out.println("Loaded from file: " + settingFile.getAbsolutePath());
            System.out.println("Existing properties:");
            properties.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
            String blockKey = key;
            double x = Double.parseDouble(properties.getProperty(blockKey + ".x"));
            double y = Double.parseDouble(properties.getProperty(blockKey + ".y"));
            double z = Double.parseDouble(properties.getProperty(blockKey + ".z"));
            String worldName = properties.getProperty(blockKey + ".world");
            Material type = Material.getMaterial(properties.getProperty(blockKey + ".type"));
            String dataString = properties.getProperty(blockKey + ".data");
            String directionString = properties.getProperty(blockKey + ".direction");

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                Block block = world.getBlockAt((int) x, (int) y, (int) z);
                if (block != null) {
                    block.setType(type);
                    if (dataString != null) {
                        block.setBlockData(Bukkit.createBlockData(dataString));
                    }
                    if (directionString != null) {
                        BlockFace direction = BlockFace.valueOf(directionString);
                    }
                    return block;
                } else {
                    System.err.println("Failed to load block at location: " + x + ", " + y + ", " + z);
                }
            } else {
                System.err.println("World not found: " + worldName);
            }
            return null;
        }
    }
}
