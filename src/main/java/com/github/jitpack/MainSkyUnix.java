package com.github.jitpack;
import api.*;
import org.bukkit.plugin.java.JavaPlugin;
public class MainSkyUnix extends JavaPlugin {
    @Override
    public void onEnable() {
        init();
        getLogger().info("SkyUnix-API_BY:Balanceakt I.login...");
    }
    @Override
    public void onDisable() {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.logout...");
    }
    public void init() {
        SkyUnixHandleDelete simpleDelete = new SkyUnixHandleDelete();
        SkyUnixHandleLocation simpleLocation = new SkyUnixHandleLocation();
        SkyUnixHandleArgs simpleReadArgs = new SkyUnixHandleArgs();
        SkyUnixHandleUpdate simplUpdateArgs = new SkyUnixHandleUpdate();
        SkyUnixHandleNullCheck simpleNullCheck = new SkyUnixHandleNullCheck();
        SkyUnixHandlePlaceholder simplePlaceHolder = new SkyUnixHandlePlaceholder();
        SkyUnixHandleWorldBlock simpleWorldBlock = new SkyUnixHandleWorldBlock();
        registerClass(simpleDelete);
        registerClass(simpleLocation);
        registerClass(simpleReadArgs);
        registerClass(simplUpdateArgs);
        registerClass(simpleNullCheck);
        registerClass(simplePlaceHolder);
       registerClass(simpleWorldBlock);
    }

    public void registerClass(Object instance) {
        getLogger().info("SkyUnix-API_BY:Balanceakt I.load - " + instance.getClass().getName());
    }
}
