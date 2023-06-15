package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.utils.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EntityExplodeListener implements Listener {

    public EntityExplodeListener(FancyPhysics fancyPhysics) {
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        realisticExplosion(event);
    }

    private void realisticExplosion(EntityExplodeEvent event) {
        if(!Config.isRealisticExplosion()) return;
        for (Block b : event.blockList()) {
            float x = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
            float y = -0.1F + (float) (ThreadLocalRandom.current().nextDouble() + 0.5D);
            float z = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
            FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(
                    b.getLocation().add(0, 1, 0), b.getType(), b.getData());
            fallingBlock.setDropItem(false);
            fallingBlock.setVelocity(new Vector(x, y, z));
            b.setType(Material.AIR);
        }
    }


}
