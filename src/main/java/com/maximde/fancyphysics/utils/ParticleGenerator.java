package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ParticleGenerator {

    private FancyPhysics fancyPhysics;

    public ParticleGenerator(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }


    public void simulateBloodParticles(Location location, Material material) {
        simulateBloodParticles(location, material, -1);
    }

    public void simulateBloodParticles(Location location, Material material, int lightLevel) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 42, 1.3F, lightLevel);
                }
            }
        }
    }

    public void simulateSplashBloodParticles(Location location, Material material) {
        simulateSplashBloodParticles(location, material, -1);
    }
    public void simulateSplashBloodParticles(Location location, Material material, int lightLevel) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 1.0F / 5F, 2F, lightLevel);
                }
            }
        }
    }

    public void simulateBlockParticles(Block block) {
        if(!fancyPhysics.config.isBlockParticles()) return;
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(block, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics);
                }
            }
        }
    }

    public void simulateBlockParticles(Location location, Material material) {
        if(!fancyPhysics.config.isBlockParticles()) return;
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 0);
                }
            }
        }
    }
    public void simulateBlockParticles(Location location, Material material, float startSize, float speed) {
        if(!fancyPhysics.config.isBlockParticles()) return;
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, startSize, speed);
                }
            }
        }
    }

    public Material getParticleMaterial(Material type) {
        return switch (type) {
            case GRASS_BLOCK -> Material.DIRT;
            case VINE, ACACIA_LEAVES, OAK_LEAVES, DARK_OAK_LEAVES, MANGROVE_LEAVES, FIRE -> Material.AIR;
            default -> type;
        };
    }
}
