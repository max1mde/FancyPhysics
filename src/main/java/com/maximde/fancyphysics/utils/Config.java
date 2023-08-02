package com.maximde.fancyphysics.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
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
                "NaturalDropsOnExplode"};

        for(String s : settingsPhysics) {
            if(cfg.isSet("Physics."+s)) continue;
            cfg.set("Physics." + s, true);
        }

        var blackList = new ArrayList<>();
        blackList.add(Material.END_ROD.name());

        if(!cfg.isSet("Physics.MaxParticleCount")) cfg.set("Physics.MaxParticleCount", 4000);
        if(!cfg.isSet("Physics.TrapdoorPhysics")) cfg.set("Physics.TrapdoorPhysics", false);
        if(!cfg.isSet("Physics.SprintDoorBreak")) cfg.set("Physics.SprintDoorBreak", false);
        if(!cfg.isSet("Physics.BlockParticleBlackList")) cfg.set("Physics.BlockParticleBlackList", blackList);
        saveConfig();
        initValues();
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
