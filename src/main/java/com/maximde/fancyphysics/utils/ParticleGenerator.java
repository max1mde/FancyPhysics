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
    private FancyPhysics fancyPhysics;
    public ParticleGenerator(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    public void simulateBloodParticles(Location location, Material material) {
        simulateBloodParticles(location, material, -1);
    }

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

    public void simulateSplashBloodParticles(Location location, Material material) {
        simulateSplashBloodParticles(location, material, -1);
    }

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

    public void simulateBlockParticles(Block block) {
        simulateBlockParticles(block.getLocation(), block.getType());
    }

    public void simulateBlockParticles(Location location, Material material) {
        simulateBlockParticles(location, material, 0, 1);
    }

    public void simulateBlockParticles(Location location, Material material, float startSize, float speed) {
        if(!fancyPhysics.config.isBlockParticles()) return;
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

    public Material getParticleMaterial(Material type) {
        return switch (type) {
            case GRASS_BLOCK -> Material.DIRT;
            case VINE, ACACIA_LEAVES, OAK_LEAVES, DARK_OAK_LEAVES, MANGROVE_LEAVES, FIRE -> Material.AIR;
            default -> type;
        };
    }

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
