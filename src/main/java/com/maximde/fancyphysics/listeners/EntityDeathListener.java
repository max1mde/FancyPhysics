package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.components.ParticleDisplay;
import com.maximde.fancyphysics.utils.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private FancyPhysics fancyPhysics;

    public EntityDeathListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        var entity = event.getEntity();
        final var height = entity.getHeight();

        if(!this.fancyPhysics.config.isEntityDeathParticles()) return;
        var material = switch (entity.getType()) {
            case MAGMA_CUBE -> Material.FIRE;
            case SLIME -> Material.SLIME_BLOCK;
            case ALLAY -> Material.DIAMOND_BLOCK;
            case ENDERMAN -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            default -> Material.RED_CONCRETE;
        };
        if (height > 0) {
            int roundedHeight = (int) Math.ceil(height);
            Block block = entity.getLocation().getBlock();
            for (int i = 0; i < roundedHeight; i++) {
                simulate3DParticles(block.getLocation(), material);
                block = block.getRelative(0, 1, 0);
            }
        }
    }

    private void simulate3DParticles(Location location, Material material) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 40);
                }
            }
        }
    }
}
