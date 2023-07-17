package com.maximde.fancyphysics;

import com.maximde.fancyphysics.api.API;
import com.maximde.fancyphysics.listeners.entity.DamageListener;
import com.maximde.fancyphysics.listeners.entity.DeathListener;
import com.maximde.fancyphysics.listeners.entity.ExplodeListener;
import com.maximde.fancyphysics.listeners.entity.HitGroundListener;
import com.maximde.fancyphysics.listeners.player.BlockBreakListener;
import com.maximde.fancyphysics.listeners.player.BlockPlaceListener;
import com.maximde.fancyphysics.listeners.player.InteractListener;
import com.maximde.fancyphysics.listeners.player.MoveListener;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.bstats.Metrics;
import com.maximde.fancyphysics.utils.ParticleGenerator;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class FancyPhysics extends JavaPlugin {

    public Config config;
    public ParticleGenerator particleGenerator;
    /**
     * Contains all block display entity's spawned by this plugin
     */
    public List<BlockDisplay> blockDisplayList = new ArrayList<>();
    public static API api;

    @Override
    public void onEnable() {
        api = new API(this);
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
        registerListener(new BlockBreakListener(this));
        registerListener(new DeathListener(this));
        registerListener(new ExplodeListener(this));
        registerListener(new HitGroundListener(this));
        registerListener(new InteractListener(this));
        registerListener(new BlockPlaceListener(this));
        registerListener(new DamageListener(this));
        registerListener(new MoveListener(this));
    }

    private void registerListener(Object listener) {
        getServer().getPluginManager().registerEvents((Listener) listener, this);
    }

    public static API getAPI() {
        return api;
    }

}
