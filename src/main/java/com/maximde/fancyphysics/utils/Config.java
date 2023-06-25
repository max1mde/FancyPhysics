package com.maximde.fancyphysics.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Config {
    private File file = new File("plugins/FancyPhysics", "config.yml");
    private YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);
    private boolean realisticExplosion;
    private boolean entityDeathParticles;
    private boolean blockParticles;
    private boolean trapdoorPhysics;
    private boolean damageParticles;
    private boolean particleRotation;
    private boolean performanceMode;
    private boolean realisticTrees;
    private int maxParticleCount;
    private boolean dropSaplings;

    private final String[] settingsPhysics = {
            "RealisticExplosion",
            "EntityDeathParticles",
            "3DBlockParticles",
            "DamageParticles",
            "ParticleRotation",
            "PerformanceMode",
            "RealisticTrees",
            "DropSaplings"};


    public Config() {
        for(String s : settingsPhysics) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }
        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 5000);
        if(!cfg.isSet("Physics.TrapdoorPhysics")) cfg.set("Physics.TrapdoorPhysics", false);
        saveConfig();
        realisticExplosion = cfg.getBoolean("Physics.RealisticExplosion");
        entityDeathParticles = cfg.getBoolean("Physics.EntityDeathParticles");
        blockParticles = cfg.getBoolean("Physics.3DBlockParticles");
        trapdoorPhysics = cfg.getBoolean("Physics.TrapdoorPhysics");
        damageParticles = cfg.getBoolean("Physics.DamageParticles");
        particleRotation = cfg.getBoolean("Physics.ParticleRotation");
        maxParticleCount = cfg.getInt("Physics.MaxParticleCount");
        realisticTrees = cfg.getBoolean("Physics.RealisticTrees");
        dropSaplings = cfg.getBoolean("Physics.DropSaplings");
    }

    public void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
    public YamlConfiguration getConfig() {
        return cfg;
    }
    public boolean isRealisticExplosion() {
        return realisticExplosion;
    }
    public boolean isEntityDeathParticles() {
        return entityDeathParticles;
    }
    public boolean isBlockParticles() {
        return blockParticles;
    }
    public boolean isTrapdoorPhysics() {
        return trapdoorPhysics;
    }
    public boolean isDamageParticles() {
        return damageParticles;
    }
    public boolean isParticleRotation() {
        return particleRotation;
    }
    public int getMaxParticleCount() {
        return maxParticleCount;
    }
    public boolean isPerformanceMode() {
        return performanceMode;
    }
    public boolean isRealisticTrees() {
        return realisticTrees;
    }
    public boolean isDropSaplings() {
        return dropSaplings;
    }
}
