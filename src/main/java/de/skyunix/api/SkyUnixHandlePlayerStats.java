package de.skyunix.api;

import com.github.jitpack.SkyUnixAPI;
import de.skyunix.api.abstraction.FileHandle;
import de.skyunix.api.abstraction.InventoryHandle;
import org.bukkit.entity.Player;

/**
 * The SkyUnixHandlePlayerStats class provides methods for handling player statistics data using the SkyUnix Data API.
 * It extends the FileHandle class.
 */
public class SkyUnixHandlePlayerStats extends FileHandle {

    /**
     * Saves player statistics to the SkyUnix Data API.
     *
     * @param player The player whose statistics will be saved.
     */
    public void saveStats(final Player player) {
        // Save player stats using SkyUnix Data API

        // Save inventory
        SkyUnixAPI.getInstance().inventoryInstance().saveInventories("stats", "player_" + player.getUniqueId(), player);

        // Save game mode
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "gamemode", player.getGameMode().toString());

        // Save level and experience
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "level", String.valueOf(player.getLevel()));
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "exp", String.valueOf(player.getExp()));

        // Save health
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "health", String.valueOf(player.getHealth()));

        // Save food level
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "food", String.valueOf(player.getFoodLevel()));

        // Save speed
        SkyUnixAPI.getInstance().argsInstance().setSimpleArgValue("stats", "player_" + player.getUniqueId(), "speed", String.valueOf(player.getWalkSpeed()));

        // Save location
        SkyUnixAPI.getInstance().locationInstance().saveLocation("stats", "player_" + player.getUniqueId(), "location", player.getLocation());
    }

    /**
     * Loads player statistics from the SkyUnix Data API.
     *
     * @param player The player whose statistics will be loaded.
     */
    public void loadStats(final Player player) {
        // Load inventory
        final InventoryHandle inventory = SkyUnixAPI.getInstance().inventoryInstance().loadInventory("stats", "player_" + player.getUniqueId());
        player.getInventory().setContents(inventory.inventory());
        player.getInventory().setArmorContents(inventory.armor());
        player.getEnderChest().setContents(inventory.enderChest());

        // Load game mode
        String gameModeStr = SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "gamemode", 0);
        player.setGameMode(org.bukkit.GameMode.valueOf(gameModeStr));

        // Load level and experience
        int level = Integer.parseInt(SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "level", 0));
        float exp = Float.parseFloat(SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "exp", 0));
        player.setLevel(level);
        player.setExp(exp);

        // Load health
        double health = Double.parseDouble(SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "health", 0));
        player.setHealth(health);

        // Load food level
        int foodLevel = Integer.parseInt(SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "food", 0));
        player.setFoodLevel(foodLevel);

        // Load speed
        float speed = Float.parseFloat(SkyUnixAPI.getInstance().argsInstance().readSimpleArgs("stats", "player_" + player.getUniqueId(), "speed", 0));
        player.setWalkSpeed(speed);

        // Load location
        org.bukkit.Location location = SkyUnixAPI.getInstance().locationInstance().loadLocation("stats", "player_" + player.getUniqueId(), "location");
        player.teleport(location);
    }

}
