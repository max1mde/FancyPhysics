package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class DeathListener implements Listener {

    private FancyPhysics fancyPhysics;

    public DeathListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(!this.fancyPhysics.config.isEntityDeathParticles()) return;
        var entity = event.getEntity();

        final var height = entity.getHeight();
        var material = switch (entity.getType()) {
            case MAGMA_CUBE -> Material.ORANGE_CONCRETE;
            case SLIME, CREEPER -> Material.SLIME_BLOCK;
            case ALLAY -> Material.LIGHT_BLUE_STAINED_GLASS;
            case ENDERMAN, WITHER, WITHER_SKELETON -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            case SNOWMAN -> Material.SNOW_BLOCK;
            case WARDEN -> Material.SCULK;
            case SKELETON -> Material.BONE_BLOCK;
            case ZOMBIE, ZOMBIE_HORSE, ZOMBIE_VILLAGER -> Material.GREEN_CONCRETE;
            case ENDER_DRAGON -> Material.AIR;
            default -> Material.RED_CONCRETE;
        };

        var lightLevel = switch (entity.getType()) {
            case BLAZE -> 15;
            default -> -1;
        };

        if (height > 0) {
            int roundedHeight = (int) Math.ceil(height);
            Block block = entity.getLocation().getBlock();
            for (int i = 0; i < roundedHeight; i++) {
                this.fancyPhysics.particleGenerator.simulateBloodParticles(block.getLocation(), material, lightLevel);
                block = block.getRelative(0, 1, 0);
            }
        }
    }
}
