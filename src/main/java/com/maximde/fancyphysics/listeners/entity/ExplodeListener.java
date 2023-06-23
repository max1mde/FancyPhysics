package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import org.bukkit.util.Vector;


import java.util.concurrent.ThreadLocalRandom;

public class ExplodeListener implements Listener {

    private FancyPhysics fancyPhysics;
    public ExplodeListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        realisticExplosion(event);
    }

    private void realisticExplosion(EntityExplodeEvent event) {
        if(!this.fancyPhysics.config.isRealisticExplosion()) return;
        for (Block b : event.blockList()) {
            float x = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
            float y = -0.1F + (float) (ThreadLocalRandom.current().nextDouble() + 0.5D);
            float z = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
                FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(
                        b.getLocation(), b.getType(), b.getData());
                fallingBlock.setDropItem(true);
                fallingBlock.setVelocity(new Vector(x, y, z));
            b.setType(Material.AIR);
        }
    }


}
