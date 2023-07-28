package com.maximde.fancyphysics;

import com.maximde.fancyphysics.api.API;
import com.maximde.fancyphysics.command.FPCommand;
import com.maximde.fancyphysics.command.FPTabCompleter;
import com.maximde.fancyphysics.listeners.entity.DamageListener;
import com.maximde.fancyphysics.listeners.entity.DeathListener;
import com.maximde.fancyphysics.listeners.entity.ExplodeListener;
import com.maximde.fancyphysics.listeners.entity.HitGroundListener;
import com.maximde.fancyphysics.listeners.player.*;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.bstats.Metrics;
import com.maximde.fancyphysics.utils.ParticleGenerator;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class FancyPhysics extends JavaPlugin {

    private Config config;
    private ParticleGenerator particleGenerator;
    /**
     * Contains all display entity's spawned by this plugin
     */
    public List<Display> displayList = new ArrayList<>();
    public HashMap<UUID, Block> craftingTableMap = new HashMap<>();
    private static API api;

    @Override
    public void onEnable() {
        api = new API(this);
        this.config = new Config();
        this.particleGenerator = new ParticleGenerator(this);
        new Metrics(this, 18833);
        registerListeners();
        getCommand("fancyphysics").setExecutor(new FPCommand(this));
        getCommand("fancyphysics").setTabCompleter(new FPTabCompleter(this));
    }

    @Override
    public void onDisable() {
        for(Display blockDisplay : displayList) {
            blockDisplay.remove();
        }
        this.displayList.clear();
        this.craftingTableMap.clear();
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
        registerListener(new InventoryClickListener(this));
        registerListener(new PlayerQuitListener(this));
    }

    private void registerListener(Object listener) {
        getServer().getPluginManager().registerEvents((Listener) listener, this);
    }

    public static API getAPI() {
        return api;
    }

    public Config getPluginConfig() {
        return config;
    }

    public ParticleGenerator getParticleGenerator() {
        return particleGenerator;
    }

}
