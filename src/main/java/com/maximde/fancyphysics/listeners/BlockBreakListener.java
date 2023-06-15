package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.components.ParticleDisplay;
import com.maximde.fancyphysics.utils.Config;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private FancyPhysics fancyPhysics;
    public BlockBreakListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        final var block = event.getBlock();
        if(event.isCancelled()) return;
        if(!Config.isBlockParticles()) return;
        simulate3DParticles(block);
    }

    private void simulate3DParticles(Block block) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(block, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics);
                }
            }
        }
    }


}
