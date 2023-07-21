package com.maximde.fancyphysics.api;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.utils.Config;
import com.maximde.fancyphysics.utils.ParticleGenerator;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;

import java.util.List;

public class API {

    private final FancyPhysics fancyPhysics;

    public API(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    public Config getConfig() {
        return fancyPhysics.getPluginConfig();
    }

    /**
     * @return the particle generator for generating block or blood particles
     */
    public ParticleGenerator getParticleGenerator() {
        return fancyPhysics.getParticleGenerator();
    }

    /**
     * @return all active display entitys spawned by the plugin
     */
    public List<Display> getBlockDisplayList() {
        return fancyPhysics.displayList;
    }

}
