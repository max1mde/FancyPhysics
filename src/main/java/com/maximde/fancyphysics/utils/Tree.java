package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.ArrayList;

public class Tree {
    /**
     * Returns true if the tree's properties are characteristic of a naturally generated tree.
     */
    private boolean isNatural;
    /**
     * The block that was broken by the player
     */
    private final Block origin;
    /**
     * The material of the tree's stem.
     */
    private final Material wood_material;
    /**
     * The material of the tree's leaves.
     */
    private final Material leave_material;
    private final ArrayList<Block> stem = new ArrayList<>();
    private final ArrayList<Block> leaves = new ArrayList<>();
    private final FancyPhysics fancyPhysics;

    /**
     * Constructs a Tree object with the given origin block and FancyPhysics instance.
     *
     * @param origin        The block from which the tree originated.
     * @param fancyPhysics  The FancyPhysics instance.
     */
    public Tree(Block origin, FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
        this.origin = origin;
        this.wood_material = origin.getType();
        this.leave_material = getLeaveType(this.wood_material);
        scanTree(origin);
        this.isNatural = (this.stem.size() > 3 && this.leaves.size() > 10);
    }

    /**
     * Breaks the tree with a falling animation if the tree is natural.
     */
    public void breakWithFallAnimation() {
        if(!isNatural) return;
        for (Block b : this.stem)  spawnDisplay(b);
        for (Block b : this.leaves) spawnDisplay(b);
    }

    private void spawnDisplay(Block block) {
        final var location = block.getLocation();
        final BlockData blockData = block.getType().createBlockData();

        /*
         * Spawn block display
         */
        location.getWorld().spawn(location, BlockDisplay.class, blockDisplay -> {
            this.fancyPhysics.blockDisplayList.add(blockDisplay);
            blockDisplay.setBlock(blockData);
            if(block != origin) block.setType(Material.AIR);

            var transformationY = - 1 + (this.origin.getY() - (block.getY()));
            var transformationZ = (this.origin.getY() - block.getY()) + (this.origin.getY() - block.getY()) / 0.9F;

            /*
            Transform display (Falling animation)
             */
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
                Transformation transformation = new Transformation(
                        new Vector3f(0, transformationY + (this.origin.getY() - (block.getY() + 0.6F)) / 2, transformationZ),//translation
                        new Quaternionf(-1.0F,0,0,0.1),   //left rotation
                        new Vector3f(1F, 1F,1F),    //scale
                        blockDisplay.getTransformation().getRightRotation()  //right rotation
                );
                blockDisplay.setInterpolationDuration(40);
                blockDisplay.setInterpolationDelay(-1);
                blockDisplay.setTransformation(transformation);

                /*
                Break tree
                 */
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
                    final var loc = blockDisplay.getLocation().add(0.5,(this.origin.getY() - (block.getY() + 0.7F)) + 1, transformationY);
                    if(false) { //TODO add option to config
                        blockDisplay.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 50, blockData);
                    } else {
                        this.fancyPhysics.particleGenerator.simulateBlockParticles(blockDisplay.getLocation().add(0,(this.origin.getY() - (block.getY() + 0.7F)) + 1.5F, transformationY -0.5F), blockData.getMaterial());
                    }
                    removeTree(blockDisplay, transformationY, blockData);
                }, 18L);

            }, 2L);
        });
    }

    /**
     * Removes the tree after the falling animation is completed.
     *
     * @param blockDisplay     The block display to remove.
     * @param transformationY  The Y transformation value.
     * @param blockData        The block data of the block display to get the material.
     */
    private void removeTree(BlockDisplay blockDisplay, float transformationY, BlockData blockData) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            if(this.fancyPhysics.config.isDropSaplings()) {
                var b = blockDisplay.getLocation().add(0, transformationY + 2, transformationY).getBlock();
                if(b.getType() == Material.AIR) {
                    b.setType(blockData.getMaterial());
                    b.breakNaturally();
                }
            } else {
                blockDisplay.getLocation().getWorld().dropItem(blockDisplay.getLocation().add(0, transformationY + 2, transformationY), new ItemStack(blockData.getMaterial()));
            }
            this.fancyPhysics.blockDisplayList.remove(blockDisplay);
            blockDisplay.remove();
        }, 4L);
    }

    /**
     * Breaks the tree instantly without any animation if the tree is natural.
     */
    public void breakInstant() {
        if(!isNatural) return;
        for (Block b : this.stem)
            b.breakNaturally();
        for (Block b : this.leaves)
            b.breakNaturally();
    }

    /**
     * Breaks the tree instantly with a 3D particle animation if the tree is natural.
     */
    public void breakInstantWithParticles() {
        if(!isNatural) return;
        for (Block b : this.stem) {
            this.fancyPhysics.particleGenerator.simulateBlockParticles(b);
            b.breakNaturally();
        }
        for (Block b : this.leaves) {
            this.fancyPhysics.particleGenerator.simulateBlockParticles(b);
            b.breakNaturally();
        }
    }

    /**
     * Determines the material of the leaves based on the wood material of the tree.
     *
     * @param wood  The wood material of the tree.
     * @return      The material of the leaves.
     */
    private Material getLeaveType(Material wood) {
        return switch (wood) {
            case OAK_LOG -> Material.OAK_LEAVES;
            case DARK_OAK_LOG -> Material.DARK_OAK_LEAVES;
            case JUNGLE_LOG -> Material.JUNGLE_LEAVES;
            case ACACIA_LOG -> Material.ACACIA_LEAVES;
            case BIRCH_LOG -> Material.BIRCH_LEAVES;
            case SPRUCE_LOG -> Material.SPRUCE_LEAVES;
            case CHERRY_LOG -> Material.CHERRY_LEAVES;
            case MANGROVE_LOG -> Material.MANGROVE_LEAVES;
            case WARPED_STEM -> Material.WARPED_WART_BLOCK;
            case CRIMSON_STEM -> Material.NETHER_WART_BLOCK;
            default -> Material.AIR;
        };
    }

    /**
     * Recursively scans the tree structure, populating the stem and leaves lists.
     *
     * @param block  The current block being scanned.
     */
    private void scanTree(Block block) {
        if (Math.abs(block.getX() - this.origin.getX()) > 3 || Math.abs(block.getZ() - this.origin.getZ()) > 3)
            return;
        if (block.getType() == this.wood_material) {
            if (this.stem.size() < 100) {
                if (this.stem.contains(block))
                    return;
                this.stem.add(block);
            } else {
                this.isNatural = false;
                return;
            }
        } else if (block.getType() == this.leave_material) {
            if (this.leaves.size() < 100) {
                if (this.leaves.contains(block))
                    return;
                this.leaves.add(block);
            } else {
                this.isNatural = false;
                return;
            }
        }
        var down = block.getRelative(BlockFace.DOWN);
        var up = block.getRelative(BlockFace.UP);
        var south = block.getRelative(BlockFace.SOUTH);
        var north = block.getRelative(BlockFace.NORTH);
        var east = block.getRelative(BlockFace.EAST);
        var west = block.getRelative(BlockFace.WEST);

        if (up.getType() == this.wood_material || up.getType() == this.leave_material)
            scanTree(up);
        if (down.getType() == this.leave_material || (down.getType() == this.wood_material && down.getY() >= this.origin.getY()))
            scanTree(down);
        if (south.getType() == this.wood_material || south.getType() == this.leave_material)
            scanTree(south);
        if (north.getType() == this.wood_material || north.getType() == this.leave_material)
            scanTree(north);
        if (east.getType() == this.wood_material || east.getType() == this.leave_material)
            scanTree(east);
        if (west.getType() == this.wood_material || west.getType() == this.leave_material)
            scanTree(west);
    }

    public ArrayList<Block> getStem() {
        return stem;
    }

    public ArrayList<Block> getLeaves() {
        return leaves;
    }

}