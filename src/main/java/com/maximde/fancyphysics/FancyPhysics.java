package com.maximde.fancyphysics;

import com.maximde.fancyphysics.listeners.entity.DamageListener;
import com.maximde.fancyphysics.listeners.entity.DeathListener;
import com.maximde.fancyphysics.listeners.entity.ExplodeListener;
import com.maximde.fancyphysics.listeners.entity.HitGroundListener;
import com.maximde.fancyphysics.listeners.player.BlockBreakListener;
import com.maximde.fancyphysics.listeners.player.BlockPlaceListener;
import com.maximde.fancyphysics.listeners.player.InteractListener;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.utils.Metrics;
import com.maximde.fancyphysics.utils.ParticleGenerator;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class FancyPhysics extends JavaPlugin {

    public Config config;
    public ParticleGenerator particleGenerator;
    public List<BlockDisplay> blockDisplayList = new ArrayList<>();

    @Override
    public void onEnable() {
        this.config = new Config();
        this.particleGenerator = new ParticleGenerator(this);
        new Metrics(this, 18833);
        registerListeners();
    }

    @Override
    public void onDisable() {
        for(BlockDisplay blockDisplay : blockDisplayList) {
            blockDisplay.remove();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplodeListener(this), this);
        getServer().getPluginManager().registerEvents(new HitGroundListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
    }
}
