package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryClickListener implements Listener {
    private final FancyPhysics fancyPhysics;
    private HashMap<UUID, List<Display>> displayList = new HashMap<>();

    public InventoryClickListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final var previousItems = new ArrayList<>(List.of(event.getInventory().getContents()));
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            craftingVisualizer(event, previousItems);
        }, 1L);
    }

    /**
        Remove all displays on close
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        var player = event.getPlayer();
        /*
         * Check if player was crafting
         */
        if(event.getInventory().getType() != InventoryType.WORKBENCH) return;
        if(!fancyPhysics.craftingTableMap.containsKey(player.getUniqueId())) return;
        if(!displayList.containsKey(player.getUniqueId())) return;

        /*
         * Remove all displays
         */
        for(Display display : displayList.get(player.getUniqueId())) display.remove();
        displayList.remove(player.getUniqueId());
        fancyPhysics.craftingTableMap.remove(player.getUniqueId());
    }

    private void craftingVisualizer(InventoryClickEvent event, ArrayList<ItemStack> previousItems) {
        //TODO config check
        if(!(event.getWhoClicked() instanceof Player player)) return;
        if(event.getInventory().getType() != InventoryType.WORKBENCH) return;

        if(!fancyPhysics.craftingTableMap.containsKey(player.getUniqueId())) return;
        CraftingInventory inventory = (CraftingInventory) event.getInventory();
        Block craftingTable = fancyPhysics.craftingTableMap.get(player.getUniqueId());

        updateCraftingField(craftingTable, inventory, event, player, previousItems);
    }

    private void updateCraftingField(Block craftingTable, CraftingInventory inventory, InventoryClickEvent event, Player player, ArrayList<ItemStack> previousItems) {
        if(displayList.get(player.getUniqueId()) != null) {
            for(Display display : displayList.get(player.getUniqueId())) display.remove();
            displayList.remove(player.getUniqueId());
        }

        for(int i = 1; i < inventory.getSize(); i++) {

            boolean instantSwitch = false;

            var finalItem = inventory.getItem(i) == null ? new ItemStack(Material.AIR) : inventory.getItem(i);
            if(finalItem.getType() == previousItems.get(i).getType()) instantSwitch = true;

            var height = switch (i) {
                case 1, 2, 3 -> 0F;
                case 4, 5, 6 -> 0.2F;
                case 7, 8, 9 -> 0.4F;
                default -> 0;
            };


            var firstSlotTransformation =
                    new Transformation(
                            new Vector3f((float) i / 5 - (height * 3) - 0.4F,1.01F,height - 0.205F),
                            new Quaternionf(1,0,0,1),
                            new Vector3f(0.08F,0.08F,0.08F),
                            new Quaternionf(0,0,0,1)
                    );
            spawnItemDisplay(craftingTable.getLocation(), firstSlotTransformation, inventory.getItem(i), player, instantSwitch);
        }
    }

    private void spawnItemDisplay(Location location, Transformation transformation, ItemStack itemStack, Player player, boolean instantSwitch) {
        final var finalLocation = location.clone().add(0.5, 0, 0.5);

        /*
         * Spawn the display
         */
        var entity = location.getWorld().spawn(finalLocation, ItemDisplay.class, itemDisplay -> {
            var item = itemStack;
            if(item == null) item = new ItemStack(Material.AIR);
            itemDisplay.setItemStack(item);
            itemDisplay.setBrightness(new Display.Brightness(15, 15));

            /*
             * Transform the display
             */
            var startTransformation =
                    new Transformation(
                            new Vector3f(0,1.3F,0),
                            new Quaternionf(0,0.4F,0,1),
                            new Vector3f(0,0,0),
                            new Quaternionf(0,0,0,1)
                    );

            if(!instantSwitch) {
                itemDisplay.setInterpolationDuration(0);
                itemDisplay.setInterpolationDelay(-1);
                itemDisplay.setTransformation(startTransformation);
                runFinalTransformation(itemDisplay, transformation);
            } else {
                itemDisplay.setInterpolationDuration(0);
                itemDisplay.setInterpolationDelay(-1);
                itemDisplay.setTransformation(transformation);
            }

        });
        var list = displayList.get(player.getUniqueId());
        if(list == null) list = new ArrayList<>();
        list.add(entity);
        displayList.put(player.getUniqueId(), list);
        fancyPhysics.displayList.add(entity);
    }

    private void runFinalTransformation(ItemDisplay itemDisplay, Transformation transformation) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            itemDisplay.setInterpolationDuration(10);
            itemDisplay.setInterpolationDelay(-1);
            itemDisplay.setTransformation(transformation);
        }, 2L);
    }

}
