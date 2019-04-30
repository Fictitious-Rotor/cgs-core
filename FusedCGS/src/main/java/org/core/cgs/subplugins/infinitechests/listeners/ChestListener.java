package org.core.cgs.subplugins.infinitechests.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.core.cgs.generic.abstracts.CGSListener;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PlayerInterface;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.infinitechests.backends.unbind;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.cgs.subplugins.infinitechests.metadata.stored.ChestStoredMH;
import org.core.cgs.subplugins.infinitechests.utilities.AirMaterialUtils;

public class ChestListener extends CGSListener {
    private final ChestStoredMH chestMetadataHandler;

    public ChestListener(final MetadataBundle metadataBundle,
                         final PlayerInterface playerInterface) {
        super(metadataBundle, playerInterface);

        this.chestMetadataHandler = metadataBundle.getHandler(ChestStoredMH.class);
    }

    private static Chest toChest(final Block givenBlock) {
        return (Chest)(givenBlock.getState());
    }

    /**
     * Ensures that the chest is filled with the chest's bound item before the player sees the contents.
     * @param event Eventargs
     */
    @EventHandler
    public void onInventoryOpenEvent(final InventoryOpenEvent event) {
        final HumanEntity player = event.getPlayer();
        final Block block = player.getTargetBlock(null, 6);
        final PrimedPLI PPLI = playerInterface.prime(player);

        if (chestMetadataHandler.givenBlockIsInfiniteChest(block)) {
            Inventory chestInv = toChest(block).getBlockInventory();

            final HorrificBytesMaterialBundle infiniteItem = chestMetadataHandler.getInfiniteItem(block);
            final int timeoutCount = chestInv.getSize();
            int nextEmptySlot = chestInv.firstEmpty();
            int iterationCount = 0;

            if (AirMaterialUtils.materialIsSomeFormOfAir(infiniteItem.getItemType())) {
                chestInv.clear();
                return;
            }

            while (nextEmptySlot != -1) {
                chestInv.setItem(nextEmptySlot, new ItemStack(infiniteItem.getItemType(), infiniteItem.getItemType().getMaxStackSize(), (short)infiniteItem.getItemMetadata()));
                nextEmptySlot = chestInv.firstEmpty();

                if (iterationCount >= timeoutCount) {
                    PPLI.sendToPlayer("ALERT:",
                                      String.format("Your request for item of type: '%s'", infiniteItem.getItemType()),
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

        if (chestMetadataHandler.givenBlockIsInfiniteChest(block)) {
            unbind.unbindInfinite(block, playerInterface.prime(event.getPlayer()), chestMetadataHandler);
        }
    }
}
