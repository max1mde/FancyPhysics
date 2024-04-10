package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    @Getter final HashMap<Location, Material> oldBlockList = new HashMap<Location, Material>();

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
        this.leave_material = Material.valueOf(getLeaveType(this.wood_material));
        scanTree(origin);
        this.isNatural = (this.stem.size() > 3 && this.leaves.size() > 8);
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
            this.fancyPhysics.displayList.add(blockDisplay);
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
                blockDisplay.setInterpolationDuration(39);
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
                        this.fancyPhysics.getParticleGenerator().simulateBlockParticles(blockDisplay.getLocation().add(0,(this.origin.getY() - (block.getY() + 0.7F)) + 1.5F, transformationY -0.5F), blockData.getMaterial());
                    }
                    removeTree(blockDisplay, transformationY, blockData);
                }, 16L);

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
            if(this.fancyPhysics.getPluginConfig().isDropSaplings()) {
                var b = blockDisplay.getLocation().add(0, transformationY + 2, transformationY).getBlock();
                if(b.getType() == Material.AIR) {
                    b.setType(blockData.getMaterial());
                    b.breakNaturally();
                }
            } else {
                blockDisplay.getLocation().getWorld().dropItem(blockDisplay.getLocation().add(0, transformationY + 2, transformationY), new ItemStack(blockData.getMaterial()));
            }
            this.fancyPhysics.displayList.remove(blockDisplay);
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
            this.fancyPhysics.getParticleGenerator().simulateBlockParticles(b);
            b.breakNaturally();
        }
        for (Block b : this.leaves) {
            this.fancyPhysics.getParticleGenerator().simulateBlockParticles(b);
            b.breakNaturally();
        }
    }

    /**
     * Determines the material of the leaves based on the wood material of the tree.
     *
     * @param wood  The wood material of the tree.
     * @return      The material of the leaves.
     */
    private String getLeaveType(Material wood) {
        return switch (wood.name()) {
            case "OAK_LOG" -> "OAK_LEAVES";
            case "DARK_OAK_LOG" -> "DARK_OAK_LEAVES";
            case "JUNGLE_LOG" -> "JUNGLE_LEAVES";
            case "ACACIA_LOG" -> "ACACIA_LEAVES";
            case "BIRCH_LOG" -> "BIRCH_LEAVES";
            case "SPRUCE_LOG" -> "SPRUCE_LEAVES";
            case "CHERRY_LOG" -> "CHERRY_LEAVES";
            case "MANGROVE_LOG" -> "MANGROVE_LEAVES";
            case "WARPED_STEM" -> "WARPED_WART_BLOCK";
            case "CRIMSON_STEM" -> "NETHER_WART_BLOCK";
            default -> "AIR";
        };
    }

    private int distanceToLastValid = 0;
    private int amount = 0;

    private List<Block> scannedBlocks = new ArrayList<>();
    /**
     * Recursively scans the tree structure, populating the stem and leaves lists.
     *
     * @param block  The current block being scanned.
     */
    private void scanTree(Block block) {
        scannedBlocks.add(block);
        amount++;
        if (Math.abs(block.getX() - this.origin.getX()) > 10 || Math.abs(block.getZ() - this.origin.getZ()) > 10)
            return;
        if (block.getType() == this.wood_material) {
            if (this.stem.size() < this.fancyPhysics.getPluginConfig().getTreeMaxStemSize()) {
                if (this.stem.contains(block))
                    return;
                this.stem.add(block);
                this.oldBlockList.put(block.getLocation(), block.getType());
            } else {
                this.isNatural = false;
                return;
            }
        } else if (block.getType() == this.leave_material) {
            if (this.leaves.size() < this.fancyPhysics.getPluginConfig().getTreeMaxLeavesSize()) {
                if (this.leaves.contains(block))
                    return;
                this.leaves.add(block);
                this.oldBlockList.put(block.getLocation(), block.getType());
            } else {
                this.isNatural = false;
                return;
            }
        }

        boolean advancedStemScan = this.fancyPhysics.getPluginConfig().isAdvancedStemScan();

        if(Arrays.asList(Material.COCOA_BEANS, Material.VINE, Material.SNOW).contains(block.getType()) && advancedStemScan) {
            block.breakNaturally();
        }

        Arrays.asList(BlockFace.DOWN, BlockFace.UP, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.WEST, BlockFace.EAST).forEach(blockFace -> {
            final var currentBlock = block.getRelative(blockFace);

            boolean scan = (currentBlock.getType() == this.wood_material || currentBlock.getType() == this.leave_material);
            if(blockFace == BlockFace.DOWN && currentBlock.getY() <= this.origin.getY()) {
                scan = false;
            }
            if (scan) {
                scanTree(currentBlock);
                distanceToLastValid = 0;
                return;
            }

            if(amount < this.fancyPhysics.getPluginConfig().getTreeMaxInvalidScans() && this.stem.size() > 4 && advancedStemScan && distanceToLastValid < this.fancyPhysics.getPluginConfig().getTreeMaxInvalidBlockDistance()) {
                distanceToLastValid++;
                if(!scannedBlocks.contains(currentBlock)) scanTree(currentBlock);
            }
            
        });
    }

    public ArrayList<Block> getStem() {
        return stem;
    }

    public ArrayList<Block> getLeaves() {
        return leaves;
    }

}