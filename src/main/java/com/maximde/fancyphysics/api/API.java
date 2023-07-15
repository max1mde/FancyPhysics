package com.maximde.fancyphysics.api;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.utils.ParticleGenerator;
import org.bukkit.entity.BlockDisplay;

import java.util.List;

public class API {

    private final FancyPhysics fancyPhysics;

    public API(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    public Config getConfig() {
        return fancyPhysics.config;
    }

    /**
     * @return the particle generator for generating block or blood particles
     */
    public ParticleGenerator getParticleGenerator() {
        return fancyPhysics.particleGenerator;
    }

    /**
     * @return all active blockdisplay entitys spawned by the plugin
     */
    public List<BlockDisplay> getBlockDisplayList() {
        return fancyPhysics.blockDisplayList;
    }

}
