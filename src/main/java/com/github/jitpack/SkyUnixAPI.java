package com.github.jitpack;

import de.skyunix.api.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A JavaPlugin implementation for the SkyUnix API.
 */
public class SkyUnixAPI extends JavaPlugin {

    /**
     * A map to store instances of classes derived from FileHandle.
     */
    private static final Map<String, Object> instances = new ConcurrentHashMap<>();

    /**
     * The singleton instance of SkyUnixAPI.
     */
    private static SkyUnixAPI instance;

    /**
     * Retrieves the singleton instance of SkyUnixAPI.
     *
     * @return The singleton instance of SkyUnixAPI.
     */
    public static SkyUnixAPI getInstance() {
        return instance;
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        instance = this;
        init();
        getLogger().info("SkyUnix-API_BY:Balanceakt I.login...");
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.logout...");
    }

    /**
     * Initializes the SkyUnixAPI plugin.
     */
    public void init() {
        final Reflections reflections = new Reflections("de/skyunix/api");
        final Set<Class<? extends FileHandle>> classes = reflections.getSubTypesOf(FileHandle.class);

        for (final Class<?> clazz : classes) {
            try {
                final Object currentInstance = clazz.getDeclaredConstructor().newInstance();
                instances.put(clazz.getSimpleName(), currentInstance);
                registerClass(currentInstance);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the instance of SkyUnixHandleArgs.
     *
     * @return The instance of SkyUnixHandleArgs.
     */
    public Object argsInstance() {
        return instances.get(SkyUnixHandleArgs.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandleDelete.
     *
     * @return The instance of SkyUnixHandleDelete.
     */
    public Object deleteInstance() {
        return instances.get(SkyUnixHandleDelete.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandleLocation.
     *
     * @return The instance of SkyUnixHandleLocation.
     */
    public Object locationInstance() {
        return instances.get(SkyUnixHandleLocation.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandleNullCheck.
     *
     * @return The instance of SkyUnixHandleNullCheck.
     */
    public Object nullCheckInstance() {
        return instances.get(SkyUnixHandleNullCheck.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandlePlaceholder.
     *
     * @return The instance of SkyUnixHandlePlaceholder.
     */
    public Object placeholderInstance() {
        return instances.get(SkyUnixHandlePlaceholder.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandleUpdate.
     *
     * @return The instance of SkyUnixHandleUpdate.
     */
    public Object updateInstance() {
        return instances.get(SkyUnixHandleUpdate.class.getSimpleName());
    }

    /**
     * Retrieves the instance of SkyUnixHandleWorldBlock.
     *
     * @return The instance of SkyUnixHandleWorldBlock.
     */
    public Object wordBlockInstance() {
        return instances.get(SkyUnixHandleWorldBlock.class.getSimpleName());
    }

    /**
     * Registers a class instance.
     *
     * @param instance The instance of the class to register.
     */
    private void registerClass(Object instance) {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.load - " + instance.getClass().getName());
    }
}
