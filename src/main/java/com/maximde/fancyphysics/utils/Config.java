package com.maximde.fancyphysics.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
                "NaturalDropsOnExplode"};

        for(String s : settingsPhysics) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }


        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 4000);
        if(!cfg.isSet("Physics.TrapdoorPhysics")) cfg.set("Physics.TrapdoorPhysics", false);
        if(!cfg.isSet("Physics.SprintDoorBreak")) cfg.set("Physics.SprintDoorBreak", false);
        if(!cfg.isSet("Physics.BlockParticleBlackList")) cfg.set("Physics.BlockParticleBlackList", getDefaultBlackList());
        saveConfig();
        initValues();
    }

    private ArrayList<String> getDefaultBlackList() {
        ArrayList<String> blackList = new ArrayList<>();
        blackList.add("END_ROD");
        blackList.add("POINTED_DRIPSTONE");
        blackList.add("CANDLE");
        blackList.add("BLACK_CANDLE");
        blackList.add("CYAN_CANDLE");
        blackList.add("GRAY_CANDLE");
        blackList.add("LIGHT_GRAY_CANDLE");
        blackList.add("BAMBOO");
        blackList.add("PURPLE_CANDLE");
        blackList.add("BLUE_CANDLE");
        blackList.add("LIME_CANDLE");
        blackList.add("RED_CANDLE");
        blackList.add("WHITE_CANDLE");
        blackList.add("PINK_CANDLE");
        blackList.add("MAGENTA_CANDLE");
        blackList.add("BROWN_CANDLE");
        blackList.add("GRAY_CANDLE");
        blackList.add("GREEN_CANDLE");
        blackList.add("YELLOW_CANDLE");
        blackList.add("ORANGE_CANDLE");
        blackList.add("RED_CANDLE");
        blackList.add("LIGHT_BLUE_CANDLE");
        blackList.add("CHAIN");
        blackList.add("GRASS");
        blackList.add("DANDELION");
        blackList.add("CRIMSON_FUNGUS");
        blackList.add("WARPED_FUNGUS");
        blackList.add("OXEYE_DAISY");
        blackList.add("WITHER_ROSE");
        blackList.add("ORANGE_TULIP");
        blackList.add("RED_TULIP");
        blackList.add("RED_MUSHROOM");
        blackList.add("BROWN_MUSHROOM");
        blackList.add("TALL_GRASS");
        blackList.add("SCULK_VEIN");
        blackList.add("CHERRY_SAPLING");
        blackList.add("LARGE_FERN");
        blackList.add("SUNFLOWER");
        blackList.add("CORNFLOWER");
        blackList.add("PINK_PETALS");
        blackList.add("TORCHFLOWER");
        blackList.add("MANGROVE_PROPAGULE");
        blackList.add("TWISTING_VINES");
        blackList.add("POPPY");
        blackList.add("ALLIUM");
        blackList.add("PINK_TULIP");
        blackList.add("LILY_OF_THE_VALLEY");
        blackList.add("BLUE_ORCHID");
        blackList.add("WARPED_ROOTS");
        blackList.add("AZURE_BLUET");
        blackList.add("BAMBOO");
        return blackList;
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
        fallingBlockPhysics = cfg.getBoolean("Physics.FallingBlockPhysics");
        blockParticleBlackList = cfg.getStringList("Physics.BlockParticleBlackList");
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
