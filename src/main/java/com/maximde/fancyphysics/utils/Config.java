package com.maximde.fancyphysics.utils;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Config {
    private final File file = new File("plugins/FancyPhysics", "config.yml");
    private YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);
    private boolean realisticExplosion;
    private boolean entityDeathParticles;
    private boolean blockParticles;
    private boolean trapdoorPhysics;
    private boolean damageParticles;
    private boolean particleRotation;
    private boolean performanceMode;
    private boolean realisticTrees;
    private boolean dropSaplings;
    private boolean sprintDoorBreak;
    private boolean sprintGlassBreak;
    private boolean visualCrafting;
    private boolean naturalDropsOnExplode;
    private boolean fallingBlockPhysics;
    private boolean flyUpParticles;
    private boolean blockCrackOnFall;
    private boolean explosionRegeneration;
    private boolean treeRegeneration;
    private int maxParticleCount;
    private List<String> blockParticleBlackList;
    private List<String> disabledWorldsList;

    public Config() {
        String[] settingsPhysicsEnabled = {
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

        String[] settingsPhysicsDisabled = {
                "TrapdoorPhysics",
                "SprintDoorBreak",
                "SprintGlassBreak",
                "FlyUpParticles",
                "TreeRegeneration",
                "ExplosionRegeneration",
        };

        for(String s : settingsPhysicsEnabled) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }

        for(String s : settingsPhysicsDisabled) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, false);
        }

        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 4000);
        if(!cfg.isSet("Physics.BlockParticleBlackList")) cfg.set("Physics.BlockParticleBlackList", getDefaultBlackList());
        if(!cfg.isSet("Physics.DisabledWorldsList")) cfg.set("Physics.DisabledWorldsList", new ArrayList<>(Arrays.asList("DisabledWorld", "AnotherDisabledWorld")));
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
        sprintGlassBreak = cfg.getBoolean("Physics.SprintGlassBreak");
        visualCrafting = cfg.getBoolean("Physics.VisualCrafting");
        naturalDropsOnExplode = cfg.getBoolean("Physics.NaturalDropsOnExplode");
        flyUpParticles = cfg.getBoolean("Physics.FlyUpParticles");
        fallingBlockPhysics = cfg.getBoolean("Physics.FallingBlockPhysics");
        blockCrackOnFall = cfg.getBoolean("Physics.BlockCrackOnFall");
        treeRegeneration = cfg.getBoolean("Physics.TreeRegeneration");
        explosionRegeneration = cfg.getBoolean("Physics.ExplosionRegeneration");
        blockParticleBlackList = cfg.getStringList("Physics.BlockParticleBlackList");
        disabledWorldsList = cfg.getStringList("Physics.DisabledWorldsList");
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
