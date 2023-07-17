package com.maximde.fancyphysics.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class Config {
    private final File file = new File("plugins/FancyPhysics", "config.yml");
    private final YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);
    private final boolean realisticExplosion;
    private final boolean entityDeathParticles;
    private final boolean blockParticles;
    private final boolean trapdoorPhysics;
    private final boolean damageParticles;
    private final boolean particleRotation;
    private final boolean performanceMode;
    private final boolean realisticTrees;
    private final int maxParticleCount;
    private final boolean dropSaplings;
    private final boolean sprintDoorBreak;

    public Config() {
        String[] settingsPhysics = {
                "RealisticExplosion",
                "EntityDeathParticles",
                "3DBlockParticles",
                "DamageParticles",
                "ParticleRotation",
                "PerformanceMode",
                "RealisticTrees",
                "DropSaplings",
                "SpringDoorBreak"};

        for(String s : settingsPhysics) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }

        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 4000);
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
        performanceMode = cfg.getBoolean("Physics.PerformanceMode");
        sprintDoorBreak = cfg.getBoolean("Physics.SpringDoorBreak");
    }

    public void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean isSprintDoorBreak() {
        return sprintDoorBreak;
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return cfg;
    }

}
