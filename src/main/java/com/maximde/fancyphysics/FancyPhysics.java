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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.*;
import java.util.List;

public final class FancyPhysics extends JavaPlugin {

    private Config config;
    private ParticleGenerator particleGenerator;
    /**
     * Contains all display entity's spawned by this plugin
     */
    public List<Display> displayList = new ArrayList<>();
    public HashMap<UUID, Block> craftingTableMap = new HashMap<>();
    private static API api;
    public final String primaryColor = ChatColor.of(new Color(238,103,87)).toString();
    public final String secondaryColor = ChatColor.of(new Color(250, 150, 138)).toString();
    public final String green = ChatColor.of(new Color(83, 246, 159)).toString();
    public final String red = ChatColor.of(new Color(246, 94, 129)).toString();
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
        this.config = new Config(getDataFolder());
        this.particleGenerator = new ParticleGenerator(this);
        new Metrics(this, 18833);
        registerListeners();
        getCommand("fancyphysics").setExecutor(new FPCommand(this));
        getCommand("fancyphysics").setTabCompleter(new FPTabCompleter(this));
        getServer().getConsoleSender().sendMessage(primaryColor + enableMessage);
    }

    @Override
    public void onDisable() {
        displayList.forEach(Display::remove);
        this.displayList.clear();
        this.craftingTableMap.clear();
        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().stream().filter(entity -> entity.getScoreboardTags().contains("fancyphysics")).forEach(Entity::remove);
        });
    }

    private void registerListeners() {
        Arrays.asList(
                new BlockBreakListener(this),
                new DeathListener(this),
                new ExplodeListener(this),
                new HitGroundListener(this),
                new InteractListener(this),
                new BlockPlaceListener(this),
                new DamageListener(this),
                new MoveListener(this),
                new InventoryClickListener(this),
                new PlayerQuitListener(this)).forEach(listener -> {
            getServer().getPluginManager().registerEvents(listener, this);
        });
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
