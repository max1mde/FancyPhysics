package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
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
        if(!this.fancyPhysics.getPluginConfig().isSprintDoorBreak()) return;
        Player player = event.getPlayer();
        if(!player.isSprinting()) return;
        Block block = player.getTargetBlock(null, 1);

        if (isDoor(block.getType()) || isDoor(block.getRelative(BlockFace.UP).getType())) {
            Block doorBlock = isDoor(block.getType()) ? block : block.getRelative(BlockFace.UP);
            Block aboveBlock = doorBlock.getRelative(BlockFace.UP);
            doorBlock.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, doorBlock.getLocation(), 4);
            this.fancyPhysics.getParticleGenerator().simulateSplashBloodParticles(doorBlock.getLocation(), doorBlock.getType());
            doorBlock.breakNaturally();
        }
    }

    private boolean isDoor(Material material) {
        switch (material) {
            case DARK_OAK_DOOR, BIRCH_DOOR, BAMBOO_DOOR, ACACIA_DOOR, CHERRY_DOOR, CRIMSON_DOOR, IRON_DOOR, JUNGLE_DOOR, MANGROVE_DOOR, OAK_DOOR, SPRUCE_DOOR, WARPED_DOOR -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
