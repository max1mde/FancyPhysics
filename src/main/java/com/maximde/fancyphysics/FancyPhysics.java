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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class FancyPhysics extends JavaPlugin {

    private Config config;
    private ParticleGenerator particleGenerator;
    /**
     * Contains all display entity's spawned by this plugin
     */
    public List<Display> displayList = new ArrayList<>();
    public HashMap<UUID, Block> craftingTableMap = new HashMap<>();
    private static API api;
    private final String enableMessage =
            "\n    ______                          ____  __               _          \n" +
                    "   / ____/___ _____  _______  __   / __ \\/ /_  __  _______(_)_________\n" +
                    "  / /_  / __ `/ __ \\/ ___/ / / /  / /_/ / __ \\/ / / / ___/ / ___/ ___/\n" +
                    " / __/ / /_/ / / / / /__/ /_/ /  / ____/ / / / /_/ (__  ) / /__(__  ) \n" +
                    "/_/    \\__,_/_/ /_/\\___/\\__, /  /_/   /_/ /_/\\__, /____/_/\\___/____/  \n" +
                    "                       /____/               /____/                   v" + getDescription().getVersion();

    @Override
    public void onEnable() {
        api = new API(this);
        this.config = new Config();
        this.particleGenerator = new ParticleGenerator(this);
        new Metrics(this, 18833);
        registerListeners();
        getCommand("fancyphysics").setExecutor(new FPCommand(this));
        getCommand("fancyphysics").setTabCompleter(new FPTabCompleter(this));
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + enableMessage);
    }

    @Override
    public void onDisable() {
        for(Display blockDisplay : displayList) {
            blockDisplay.remove();
        }
        this.displayList.clear();
        this.craftingTableMap.clear();

        for(World world : Bukkit.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(!(entity instanceof ItemDisplay)) continue;
                if(!entity.getScoreboardTags().contains("fancyphysics_crafting")) continue;
                entity.remove();
            }
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
