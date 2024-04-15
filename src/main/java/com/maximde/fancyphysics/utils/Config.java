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
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
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
    private int treeMaxStemSize;
    private int treeMaxLeavesSize;
    private int treeMaxInvalidScans;
    private int treeMaxInvalidBlockDistance;
    private boolean advancedStemScan;
    private int maxParticleCount;
    private List<String> blockParticleBlackList;
    private List<String> disabledWorldsList;
    private boolean sounds;
    private boolean treeChopDelay;
    private int treeRegenerationDelay;
    private int explosionRegenerationDelay;
    private boolean affectedBlocksInPlayerStats;
    private int particleAnimationSpeed;
    private boolean gravityInAir;
    private double particleEndSizeMultiplier;
    public Config() {

        /**
         * True values
         */
        for(String s : new String[]{
                "Sounds",
                "Explosion.Physics",
                "EntityDeathParticles",
                "Particle.Enabled",
                "DamageParticles",
                "Particle.Animation.Rotation",
                "PerformanceMode",
                "Tree.Physics",
                "Tree.DropSaplings",
                "VisualCrafting",
                "FallingBlockPhysics",
                "Explosion.NaturalDrops",
                "BlockCrackOnFall",
                "Tree.ChopDelay",
                "Tree.GravityIfInAir",
                "Tree.AffectedBlocksInPlayerBreakBlocksStatistic"}) setIfNot(s, true);

        /**
         * False values
         */
        for(String s : new String[]{
                "TrapdoorPhysics",
                "SprintBreak.Door",
                "SprintBreak.Glass",
                "Particle.Animation.FlyUp",
                "Tree.AdvancedStemScan"
        }) setIfNot(s, false);

        setIfNot("Regeneration.TreeRegeneration.Enabled", false);
        setIfNot("Regeneration.ExplosionRegeneration.Enabled", false);

        setIfNot("Regeneration.TreeRegeneration.Delay", 10);
        setIfNot("Regeneration.ExplosionRegeneration.Delay", 10);

        setIfNot("Particle.Animation.SpeedInTicks", 40);
        setIfNot("Particle.Animation.EndSizeMultiplier", 0.5D);

        setIfNot("Tree.ScanMaxStemSize", 200);
        setIfNot("Tree.ScanMaxLeavesSize", 260);
        setIfNot("Tree.MaxInvalidScans", 2700);
        setIfNot("Tree.MaxInvalidBlockDistance", 2);
        setIfNot("Particle.MaxAmount", 4000);
        setIfNot("BlockParticleBlackList", getDefaultBlackList());
        setIfNot("DisabledWorldsList", new ArrayList<>(Arrays.asList("DisabledWorld", "AnotherDisabledWorld")));
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
        realisticExplosion = cfg.getBoolean("Explosion.Physics");
        entityDeathParticles = cfg.getBoolean("EntityDeathParticles");
        blockParticles = cfg.getBoolean("Particle.Enabled");
        trapdoorPhysics = cfg.getBoolean("TrapdoorPhysics");
        damageParticles = cfg.getBoolean("DamageParticles");
        particleRotation = cfg.getBoolean("Particle.Animation.Rotation");
        maxParticleCount = cfg.getInt("Particle.MaxAmount");
        realisticTrees = cfg.getBoolean("Tree.Physics");
        dropSaplings = cfg.getBoolean("Tree.DropSaplings");
        performanceMode = cfg.getBoolean("PerformanceMode");
        sprintDoorBreak = cfg.getBoolean("SprintBreak.Door");
        sprintGlassBreak = cfg.getBoolean("SprintBreak.Glass");
        visualCrafting = cfg.getBoolean("VisualCrafting");
        naturalDropsOnExplode = cfg.getBoolean("Explosion.NaturalDrops");
        flyUpParticles = cfg.getBoolean("Particle.Animation.FlyUp");
        fallingBlockPhysics = cfg.getBoolean("FallingBlockPhysics");
        blockCrackOnFall = cfg.getBoolean("BlockCrackOnFall");
        treeRegeneration = cfg.getBoolean("Regeneration.TreeRegeneration.Enabled");
        explosionRegeneration = cfg.getBoolean("Regeneration.ExplosionRegeneration.Enabled");
        blockParticleBlackList = cfg.getStringList("BlockParticleBlackList");
        disabledWorldsList = cfg.getStringList("DisabledWorldsList");
        advancedStemScan = cfg.getBoolean("Tree.AdvancedStemScan");
        treeMaxLeavesSize = cfg.getInt("Tree.ScanMaxLeavesSize");
        treeMaxStemSize = cfg.getInt("Tree.ScanMaxStemSize");
        treeMaxInvalidScans = cfg.getInt("Tree.MaxInvalidScans");
        treeMaxInvalidBlockDistance = cfg.getInt("Tree.MaxInvalidBlockDistance");
        sounds = cfg.getBoolean("Sounds");
        treeChopDelay = cfg.getBoolean("Tree.ChopDelay");
        treeRegenerationDelay = cfg.getInt("Regeneration.TreeRegeneration.Delay");
        explosionRegenerationDelay = cfg.getInt("Regeneration.ExplosionRegeneration.Delay");
        affectedBlocksInPlayerStats = cfg.getBoolean("Tree.AffectedBlocksInPlayerBreakBlocksStatistic");
        particleAnimationSpeed = cfg.getInt("Particle.Animation.SpeedInTicks");
        gravityInAir = cfg.getBoolean("Tree.GravityIfInAir");
        particleEndSizeMultiplier = cfg.getDouble("Particle.Animation.EndSizeMultiplier");
    }

    public void reload() {
        this.cfg = YamlConfiguration.loadConfiguration(file);
        initValues();
    }

    public void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setIfNot(String path, Object value) {
        if(!cfg.isSet(path)) setValue(path, value);
    }

    public void setValue(String path, Object value) {
        this.cfg.set(path, value);
    }

    public Object getValue(String path) {
        return this.cfg.get(path);
    }

}
