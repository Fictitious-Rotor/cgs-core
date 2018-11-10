package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class ChestListener implements Listener {
    private Core plugin;

    public ChestListener(Core instance) {
        plugin = instance;
    }

    /**
     * Ensures that the chest is filled with the chest's bound item before the player sees the contents.
     * @param event Eventargs
     */
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        HumanEntity player = event.getPlayer();
        Block block = player.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsInfiniteChest(block)) {
            Inventory chestInv = ((Chest)(block.getState())).getBlockInventory();

            HorrificBytesMaterialBundle infiniteItem = (HorrificBytesMaterialBundle)plugin.MARKED_CHESTS.getMetadata(block);
            int nextEmptySlot = chestInv.firstEmpty();
            final int timeoutCount = chestInv.getSize();
            int iterationCount = 0;

            if (AirMaterialUtils.materialIsSomeFormOfAir(infiniteItem.getItemType())) {
                chestInv.clear();
                return;
            }

            while (nextEmptySlot != -1) {
                chestInv.setItem(nextEmptySlot, new ItemStack(infiniteItem.getItemType(), infiniteItem.getItemType().getMaxStackSize(), (short)infiniteItem.getItemMetadata()));
                nextEmptySlot = chestInv.firstEmpty();

                if (iterationCount >= timeoutCount) {
                    player.sendMessage(new String[]{ "ALERT:", "Your request for item of: ", infiniteItem.getItemType().toString(), "triggered a timeout!"});
                    break;
                }

                iterationCount++;
            }
        }
    }

    /**
     * Ensures that an infinite chest is removed from the metadata if it is destroyed
     * @param event Eventargs
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        if (plugin.chestMetadataHandler.givenBlockIsInfiniteChest(block)) {
            plugin.chestMetadataHandler.makeBlockFiniteChest(block);
        }
    }
}
