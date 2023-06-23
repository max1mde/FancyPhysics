package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private FancyPhysics fancyPhysics;

    public EntityDamageListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!this.fancyPhysics.config.isDamageParticles()) return;

        var entity = event.getEntity();
        var height = entity.getHeight();
        if (height > 0) {
            int roundedHeight = (int) Math.ceil(height);
            Block block = entity.getLocation().getBlock();
            for (int i = 0; i < roundedHeight; i++) {
                this.fancyPhysics.utils.simulateSplashBloodParticles(block.getLocation(), Material.RED_CONCRETE);
                block = block.getRelative(0, 1, 0);
            }
        }
    }
}
