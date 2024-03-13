package com.github.jitpack;

import api.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MainSkyUnix extends JavaPlugin {

    private static final Map<String, Object> instances = new ConcurrentHashMap<>();
    private static MainSkyUnix instance;

    public static MainSkyUnix getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        init();
        getLogger().info("SkyUnix-API_BY:Balanceakt I.login...");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.logout...");
    }

    public void init() {
        final Reflections reflections = new Reflections("api");
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

    public Object argsInstance() {
        return instances.get(SkyUnixHandleArgs.class.getSimpleName());
    }

    public Object deleteInstance() {
        return instances.get(SkyUnixHandleDelete.class.getSimpleName());
    }

    public Object locationInstance() {
        return instances.get(SkyUnixHandleLocation.class.getSimpleName());
    }

    public Object nullCheckInstance() {
        return instances.get(SkyUnixHandleNullCheck.class.getSimpleName());
    }

    public Object placeholderInstance() {
        return instances.get(SkyUnixHandlePlaceholder.class.getSimpleName());
    }

    public Object updateInstance() {
        return instances.get(SkyUnixHandleUpdate.class.getSimpleName());
    }

    public Object wordBlockInstance() {
        return instances.get(SkyUnixHandleWorldBlock.class.getSimpleName());
    }

    private void registerClass(Object instance) {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.load - " + instance.getClass().getName());
    }
}
