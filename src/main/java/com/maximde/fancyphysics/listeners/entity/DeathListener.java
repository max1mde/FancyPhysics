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
            case MAGMA_CUBE -> Material.FIRE;
            case SLIME -> Material.SLIME_BLOCK;
            case ALLAY -> Material.DIAMOND_BLOCK;
            case ENDERMAN -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            default -> Material.RED_MUSHROOM_BLOCK;
        };
        if (height > 0) {
            int roundedHeight = (int) Math.ceil(height);
            Block block = entity.getLocation().getBlock();
            for (int i = 0; i < roundedHeight; i++) {
                this.fancyPhysics.particleGenerator.simulateBloodParticles(block.getLocation(), material);
                block = block.getRelative(0, 1, 0);
            }
        }
    }
}
