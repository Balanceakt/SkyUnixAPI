package de.skyunix.api;

import de.skyunix.api.abstraction.FileHandle;
import de.skyunix.api.abstraction.InventoryHandle;
import de.skyunix.utils.FilePath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;

/**
 * The SkyUnixHandleInventory class provides methods for loading and saving player inventories to YAML files.
 * It extends the FileHandle class and implements InventoryHandle.
 */
public class SkyUnixHandleInventory extends FileHandle {

    /**
     * Loads player inventory, armor, and ender chest contents from a YAML file.
     *
     * @param folder The folder where the YAML file is located.
     * @param table  The name of the YAML file (excluding the .yml extension).
     * @return An InventoryHandle object containing the loaded inventory, armor, and ender chest contents.
     */
    public InventoryHandle loadInventory(String folder, String table) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table + ".yml");

        final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(settingFile);

        return new InventoryHandle((ItemStack[]) yamlConfiguration.getList("inventory").toArray(), (ItemStack[]) yamlConfiguration.getList("armor").toArray(), (ItemStack[]) yamlConfiguration.getList("enderchest").toArray());
    }

    /**
     * Saves player inventory, armor, and ender chest contents to a YAML file.
     *
     * @param folder The folder where the YAML file will be saved.
     * @param table  The name of the YAML file to be saved (excluding the .yml extension).
     * @param player The player whose inventory, armor, and ender chest contents will be saved.
     */
    public void saveInventories(String folder, String table, Player player) {
        File folderFile = new File(FilePath.folderPath, folder);
        File settingFile = new File(folderFile, table + ".yml");

        final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(settingFile);

        yamlConfiguration.set("inventory", player.getInventory().getContents());
        yamlConfiguration.set("armor", player.getInventory().getArmorContents());
        yamlConfiguration.set("enderchest", player.getEnderChest().getContents());

        try {
            yamlConfiguration.save(settingFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
