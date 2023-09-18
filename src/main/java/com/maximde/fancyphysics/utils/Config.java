package com.maximde.fancyphysics.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Config {
    private @Getter final File file = new File("plugins/FancyPhysics", "config.yml");
    private @Getter YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);
    private @Getter boolean realisticExplosion;
    private @Getter boolean entityDeathParticles;
    private @Getter boolean blockParticles;
    private @Getter boolean trapdoorPhysics;
    private @Getter boolean damageParticles;
    private @Getter boolean particleRotation;
    private @Getter boolean performanceMode;
    private @Getter boolean realisticTrees;
    private @Getter boolean dropSaplings;
    private @Getter boolean sprintDoorBreak;
    private @Getter boolean visualCrafting;
    private @Getter boolean naturalDropsOnExplode;
    private @Getter boolean fallingBlockPhysics;
    private @Getter boolean flyUpParticles;
    private @Getter boolean blockCrackOnFall;
    private @Getter int maxParticleCount;
    private @Getter List<String> blockParticleBlackList;

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
                "VisualCrafting",
                "FallingBlockPhysics",
                "NaturalDropsOnExplode",
                "BlockCrackOnFall"};

        for(String s : settingsPhysics) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }

        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 4000);
        if(!cfg.isSet("Physics.TrapdoorPhysics")) cfg.set("Physics.TrapdoorPhysics", false);
        if(!cfg.isSet("Physics.SprintDoorBreak")) cfg.set("Physics.SprintDoorBreak", false);
        if(!cfg.isSet("Physics.FlyUpParticles")) cfg.set("Physics.FlyUpParticles", false);
        if(!cfg.isSet("Physics.BlockParticleBlackList")) cfg.set("Physics.BlockParticleBlackList", getDefaultBlackList());
        saveConfig();
        initValues();
    }

    private List<String> getDefaultBlackList() {
        return new ArrayList<>(Arrays.asList(
                "END_ROD", "POINTED_DRIPSTONE", "CANDLE", "BLACK_CANDLE",
                "CYAN_CANDLE", "GRAY_CANDLE", "LIGHT_GRAY_CANDLE", "BAMBOO",
                "PURPLE_CANDLE", "BLUE_CANDLE", "LIME_CANDLE", "RED_CANDLE",
                "WHITE_CANDLE", "PINK_CANDLE", "MAGENTA_CANDLE", "BROWN_CANDLE",
                "GRAY_CANDLE", "GREEN_CANDLE", "YELLOW_CANDLE", "ORANGE_CANDLE",
                "RED_CANDLE", "LIGHT_BLUE_CANDLE", "CHAIN", "GRASS",
                "DANDELION", "CRIMSON_FUNGUS", "WARPED_FUNGUS", "OXEYE_DAISY",
                "WITHER_ROSE", "ORANGE_TULIP", "RED_TULIP", "RED_MUSHROOM",
                "BROWN_MUSHROOM", "TALL_GRASS", "SCULK_VEIN", "CHERRY_SAPLING",
                "LARGE_FERN", "SUNFLOWER", "CORNFLOWER", "PINK_PETALS",
                "TORCHFLOWER", "MANGROVE_PROPAGULE", "TWISTING_VINES", "POPPY",
                "ALLIUM", "PINK_TULIP", "LILY_OF_THE_VALLEY", "BLUE_ORCHID",
                "WARPED_ROOTS", "AZURE_BLUET", "BAMBOO"
        ));
    }


    private void initValues() {
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
        sprintDoorBreak = cfg.getBoolean("Physics.SprintDoorBreak");
        visualCrafting = cfg.getBoolean("Physics.VisualCrafting");
        naturalDropsOnExplode = cfg.getBoolean("Physics.NaturalDropsOnExplode");
        flyUpParticles = cfg.getBoolean("Physics.FlyUpParticles");
        fallingBlockPhysics = cfg.getBoolean("Physics.FallingBlockPhysics");
        blockParticleBlackList = cfg.getStringList("Physics.BlockParticleBlackList");
        blockCrackOnFall = cfg.getBoolean("Physics.BlockCrackOnFall");
    }

    public void reload() {
        this.cfg = new YamlConfiguration().loadConfiguration(file);
        initValues();
    }

    public void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValue(String path, Object value) {
        this.cfg.set(path, value);
    }

    public Object getValue(String path) {
        return this.cfg.get(path);
    }

}
