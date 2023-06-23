package com.maximde.fancyphysics;

import com.maximde.fancyphysics.listeners.*;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.utils.Metrics;
import com.maximde.fancyphysics.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;


public final class FancyPhysics extends JavaPlugin {

    public Config config;
    public Utils utils;

    @Override
    public void onEnable() {
        this.config = new Config();
        this.utils = new Utils(this);
        new Metrics(this, 18833);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityHitGroundListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
    }
}
