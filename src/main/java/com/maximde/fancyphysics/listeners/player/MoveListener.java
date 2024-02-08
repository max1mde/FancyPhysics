package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    private final FancyPhysics fancyPhysics;

    public MoveListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        doorBreakManager(event);
    }

    private void doorBreakManager(PlayerMoveEvent event) {
        if(this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getPlayer().getLocation().getWorld().getName())) return;
        if(!this.fancyPhysics.getPluginConfig().isSprintDoorBreak() && !this.fancyPhysics.getPluginConfig().isSprintGlassBreak()) return;
        Player player = event.getPlayer();
        if(!player.isSprinting()) return;
        Block block = player.getTargetBlock(null, 1);

        if (isBreakable(block.getType()) || isBreakable(block.getRelative(BlockFace.UP).getType())) {
            Block doorBlock = isBreakable(block.getType()) ? block : block.getRelative(BlockFace.UP);
            Block aboveBlock = doorBlock.getRelative(BlockFace.UP);
            doorBlock.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, doorBlock.getLocation(), 4);
            this.fancyPhysics.getParticleGenerator().simulateSplashBloodParticles(doorBlock.getLocation(), doorBlock.getType());
            doorBlock.breakNaturally();
        }
    }

    private boolean isBreakable(Material material) {
        boolean isDoor = material.name().contains("DOOR");
        boolean isGlass = material.name().contains("GLASS");

        if(isGlass && this.fancyPhysics.getPluginConfig().isSprintGlassBreak()) return true;

        return isDoor && this.fancyPhysics.getPluginConfig().isSprintDoorBreak();
    }
}
