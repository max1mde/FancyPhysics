package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.api.events.ParticleEffectEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import java.util.ArrayList;
import java.util.List;

public class ParticleGenerator {
    private final FancyPhysics fancyPhysics;
    public ParticleGenerator(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    /**
     * Simulates blood particles at the given location with the specified material.
     *
     * @param location  The location at which to simulate the blood particles.
     * @param material  The material of the blood particles.
     */
    public void simulateBloodParticles(Location location, Material material) {
        simulateBloodParticles(location, material, -1);
    }

    /**
     * Simulates blood particles at the given location with the specified material and light level.
     *
     * @param location    The location at which to simulate the blood particles.
     * @param material    The material of the blood particles.
     * @param lightLevel  The light level of the blood particles. (For example the damage and death particles of the blaze are using the maximum light level (15) )
     */
    public void simulateBloodParticles(Location location, Material material, int lightLevel) {
        List<BlockDisplay> displayList = new ArrayList<>();
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    var display = new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 42, 1.3F, lightLevel);
                    displayList.add(display.getBlockDisplay());
                }
            }
        }
        manageParticleEffectEvent(location, displayList);
    }

    /**
     * Simulates splash blood particles at the given location with the specified material.
     * They are similar to the normal blood particles, but they move faster
     *
     * @param location  The location at which to simulate the splash blood particles.
     * @param material  The material of the splash blood particles.
     */
    public void simulateSplashBloodParticles(Location location, Material material) {
        simulateSplashBloodParticles(location, material, -1);
    }

    /**
     * Simulates splash blood particles at the given location with the specified material and light level.
     *They are similar to the normal blood particles, but they move faster
     *
     * @param location    The location at which to simulate the splash blood particles.
     * @param material    The material of the splash blood particles.
     * @param lightLevel  The light level of the splash blood particles.
     */
    public void simulateSplashBloodParticles(Location location, Material material, int lightLevel) {
        List<BlockDisplay> displayList = new ArrayList<>();
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    var display = new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 1.0F / 5F, 2F, lightLevel);
                    displayList.add(display.getBlockDisplay());
                }
            }
        }
        manageParticleEffectEvent(location, displayList);
    }

    /**
     * Simulates block particles at the location of the given block.
     *
     * @param block  The block to simulate the block particles.
     */
    public void simulateBlockParticles(Block block) {
        simulateBlockParticles(block.getLocation(), block.getType());
    }

    /**
     * Simulates block particles at the given location with the specified material.
     *
     * @param location  The location at which to simulate the block particles.
     * @param material  The material of the block particles.
     */
    public void simulateBlockParticles(Location location, Material material) {
        simulateBlockParticles(location, material, 0, 1);
    }

    /**
     * Simulates block particles at the given location with the specified material, start size, and speed.
     *
     * @param location    The location at which to simulate the block particles.
     * @param material    The material of the block particles.
     * @param startSize   The starting size of the block particles.
     * @param speed       The speed of the block particles.
     */
    public void simulateBlockParticles(Location location, Material material, float startSize, float speed) {
        if(!fancyPhysics.getPluginConfig().isBlockParticles()) return;
        List<BlockDisplay> displayList = new ArrayList<>();
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    var display = new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, startSize, speed);
                    displayList.add(display.getBlockDisplay());
                }
            }
        }
        manageParticleEffectEvent(location, displayList);
    }

    /**
     * Returns a better particle material for the given type because some particles are looking weird with specific materials
     *
     * @param type  The type of the particle.
     * @return      The particle material.
     */
    public Material getParticleMaterial(Material type) {
        return switch (type) {
            case GRASS_BLOCK -> Material.DIRT;
            case VINE, ACACIA_LEAVES, OAK_LEAVES, DARK_OAK_LEAVES, MANGROVE_LEAVES, FIRE -> Material.AIR;
            default -> type;
        };
    }

    /**
     * Calls the ParticleEffectEvent and handles the removal of block displays if the event is cancelled.
     *
     * @param location      The location of the particle effect.
     * @param displayList   The list of block displays.
     */
    private void manageParticleEffectEvent(Location location, List<BlockDisplay> displayList) {
        ParticleEffectEvent event = new ParticleEffectEvent(location, displayList);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            for(BlockDisplay display : displayList) {
                display.remove();
                fancyPhysics.blockDisplayList.remove(display);
            }
        }
        displayList.clear();
    }

}
