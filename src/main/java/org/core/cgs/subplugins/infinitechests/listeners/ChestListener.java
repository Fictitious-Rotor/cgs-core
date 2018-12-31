package org.core.cgs.subplugins.infinitechests.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.core.cgs.subplugins.infinitechests.AirMaterialUtils;
import org.core.cgs.subplugins.infinitechests.InfiniteChestsCore;
import org.core.cgs.subplugins.infinitechests.functions.ICFunctions;
import org.core.cgs.subplugins.infinitechests.metadata.ChestStoredMH;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;

import static org.core.cgs.subplugins.infinitechests.InfiniteChestsCore.PLI;

public class ChestListener implements Listener {
    private final ChestStoredMH metadataHandler;

    public ChestListener(ChestStoredMH metadataHandler) {
        this.metadataHandler = metadataHandler;
    }

    private static Chest toChest(Block givenBlock) {
        return ((Chest)(givenBlock.getState()));
    }

    /**
     * Ensures that the chest is filled with the chest's bound item before the player sees the contents.
     * @param event Eventargs
     */
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        HumanEntity player = event.getPlayer();
        Block block = player.getTargetBlock(null, 6);

        if (metadataHandler.givenBlockIsInfiniteChest(block)) {
            Inventory chestInv = toChest(block).getBlockInventory();

            HorrificBytesMaterialBundle infiniteItem = metadataHandler.getInfiniteItem(block);
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
                    PLI.sendToPlayer(player,
                                     "ALERT:",
                                     String.format("Your request for item of: %s", infiniteItem.getItemType()),
                                     "triggered a timeout!"
                    );
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

        if (metadataHandler.givenBlockIsInfiniteChest(block)) {
            ICFunctions.unbindInfinite(event.getPlayer(), InfiniteChestsCore.ICStoredMH.getHandlerAs(ChestStoredMH.class));
            metadataHandler.makeBlockFiniteChest(block);
        }
    }
}
