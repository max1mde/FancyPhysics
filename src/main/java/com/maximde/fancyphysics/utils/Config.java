package com.maximde.fancyphysics.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static File file = new File("plugins/FancyPhysics", "config.yml");

    private static YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);

    private static boolean realisticExplosion;
    private static boolean entityDeathParticles;
    private static boolean blockParticles;

    public static void setupConfig() {
        if(!cfg.isSet("Physics")) {
            cfg.set("Physics.RealisticExplosion", true);
            cfg.set("Physics.EntityDeathParticles", true);
            cfg.set("Physics.3DBlockParticles", true);
            saveConfig();
        }
        realisticExplosion = cfg.getBoolean("Physics.RealisticExplosion");
        entityDeathParticles = cfg.getBoolean("Physics.EntityDeathParticles");
        blockParticles = cfg.getBoolean("Physics.3DBlockParticles");
    }

    public static void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File getFile() {
        return file;
    }


    public static YamlConfiguration getConfig() {
        return cfg;
    }

    public static boolean isRealisticExplosion() {
        return realisticExplosion;
    }

    public static boolean isEntityDeathParticles() {
        return entityDeathParticles;
    }

    public static boolean isBlockParticles() {
        return blockParticles;
    }
}
