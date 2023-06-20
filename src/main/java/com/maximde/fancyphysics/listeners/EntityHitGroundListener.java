package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.components.ParticleDisplay;
import com.maximde.fancyphysics.utils.Config;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityHitGroundListener implements Listener {

    private FancyPhysics fancyPhysics;

    public EntityHitGroundListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if(!this.fancyPhysics.config.isBlockParticles()) return;
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            event.setCancelled(true);
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            Material material = fallingBlock.getMaterial();
            Location loc = event.getEntity().getLocation();
            loc.getWorld().playSound(loc, Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            simulate3DParticles(fallingBlock.getLocation(), material);
        }
    }

    private void simulate3DParticles(Location location, Material material) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 30);
                }
            }
        }
    }
}
