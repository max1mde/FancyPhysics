package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class InteractListener implements Listener {
    public static List<Location> animatedLocations = new ArrayList<>();
    private final FancyPhysics fancyPhysics;

    public InteractListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        craftingVisualizer(event);
        spawnTrapdoorDisplay(event.getClickedBlock(), event);
    }

    private void craftingVisualizer(PlayerInteractEvent event) {
        if(!fancyPhysics.getPluginConfig().isVisualCrafting()) return;
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType() != Material.CRAFTING_TABLE) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(fancyPhysics.craftingTableMap.containsValue(event.getClickedBlock())) return;
        fancyPhysics.craftingTableMap.put(event.getPlayer().getUniqueId(), event.getClickedBlock());
    }

    /**
     * Spawns a display for animating the trapdoor on open/close
     *
     * @param clickedBlock The clicked block.
     * @param event        The PlayerInteractEvent.
     */
    private void spawnTrapdoorDisplay(Block clickedBlock, PlayerInteractEvent event) {
        if(!this.fancyPhysics.getPluginConfig().isTrapdoorPhysics()) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(clickedBlock == null) return;
        if(!clickedBlock.getType().name().contains("TRAPDOOR")) return;
        if(animatedLocations.contains(clickedBlock.getLocation())) return;
        final BlockData blockData = clickedBlock.getBlockData();
        event.setCancelled(true);
        animatedLocations.add(clickedBlock.getLocation());
        clickedBlock.setType(Material.AIR);

        clickedBlock.getLocation().getWorld().spawn(clickedBlock.getLocation(), BlockDisplay.class, blockDisplay -> {
            blockDisplay.setInvulnerable(true);
            blockDisplay.setPersistent(true);
            blockDisplay.setBlock(blockData);
            runTrapdoorAnimation(blockDisplay, blockData, clickedBlock);
        });
    }

    /**
     * Performs the animation for opening/closing the trapdoor.
     *
     * @param blockDisplay     The BlockDisplay entity representing the trapdoor.
     * @param previousBlockData The previous BlockData of the trapdoor.
     * @param clickedBlock     The clicked block.
     */
    private void runTrapdoorAnimation(BlockDisplay blockDisplay, BlockData previousBlockData, Block clickedBlock) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            clickedBlock.setType(Material.AIR);
            var leftRotation = new Quaternionf(0,0,1,0);
            var transformation = new Vector3f(0.3F, 0, 0);
            if (previousBlockData instanceof TrapDoor slab) {
                if(slab.isOpen()) {
                    leftRotation = new Quaternionf(0,0,-1,0);
                    transformation = new Vector3f(0, 0.3F, 0);
                }
            }
            Transformation transformationMove = new Transformation(
                    transformation,
                    leftRotation,
                    new Vector3f(1,1,1),
                    blockDisplay.getTransformation().getRightRotation()
            );
            blockDisplay.setInterpolationDuration(19);
            blockDisplay.setInterpolationDelay(-1);
            blockDisplay.setTransformation(transformationMove);

            removeTrapdoor(blockDisplay, previousBlockData);
        }, 2L);
    }

    /**
     * Removes the trapdoor after 9 ticks
     */
    private void removeTrapdoor(BlockDisplay blockDisplay, BlockData previousBlockData) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            var block = blockDisplay.getLocation().getBlock();
            if (previousBlockData instanceof TrapDoor slab) {
                slab.setOpen(!(slab.isOpen()));
                slab.setFacing(BlockFace.EAST);
            }
            block.setBlockData(previousBlockData);
            blockDisplay.remove();
            animatedLocations.remove(block.getLocation());
        }, 9L);
    }

}