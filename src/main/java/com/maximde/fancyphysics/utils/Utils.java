package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.components.ParticleDisplay;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Utils {

    private FancyPhysics fancyPhysics;

    public Utils(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    public void simulateBloodParticles(Location location, Material material) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 42, 1.3F);
                }
            }
        }
    }

    public void simulateSplashBloodParticles(Location location, Material material) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 1.0F / 5F, 2F);
                }
            }
        }
    }

    public void simulateBlockParticles(Block block) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(block, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics);
                }
            }
        }
    }
}
