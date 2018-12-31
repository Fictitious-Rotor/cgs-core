package org.core.cgs.subplugins.infinitechests.functions;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.TextLine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.cgs.Core;
import org.core.cgs.subplugins.infinitechests.AirMaterialUtils;
import org.core.cgs.subplugins.infinitechests.metadata.ChestStoredMH;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;

import java.util.logging.Level;

import static org.core.cgs.subplugins.infinitechests.InfiniteChestsCore.PLI;

public class ICFunctions {
    private static String formatChestLocation(final Location chestLocation) {
        return String.format("%s:(%s,%s,%s)",
                             chestLocation.getWorld().getName(),
                             chestLocation.getBlockX(),
                             chestLocation.getBlockY(),
                             chestLocation.getBlockZ()
        );
    }

    private static void createVoidChest(final Block foundBlock) {
        final Location chestLocation = foundBlock.getLocation().clone();
        final String hologramName = formatChestLocation(chestLocation);

        Core.LOGGER.log(Level.INFO, "Created void sign with name of = {0}", hologramName);
        final Hologram hologram = new Hologram(hologramName, chestLocation.add(0.5, 0.65, 0.5));
        hologram.addLine(new TextLine(hologram, "Void_Chest"));

        final HologramManager hologramManager = JavaPlugin.getPlugin(HologramPlugin.class)
                                                          .getHologramManager();
        hologramManager.addActiveHologram(hologram);
        hologramManager.saveHologram(hologram);
    }

    private static void reportInfiniteChestCreation(final Player runningPlayer, final Block foundBlock, final Material specifiedMaterial, final byte specifiedMetadata) {
        if (AirMaterialUtils.materialIsSomeFormOfAir(specifiedMaterial)) {
            createVoidChest(foundBlock);
        }

        PLI.sendToPlayer(runningPlayer,
                         String.format("You have bound the chest at (%s, %s, %s)",
                                       foundBlock.getLocation().getBlockX(),
                                       foundBlock.getLocation().getBlockY(),
                                       foundBlock.getLocation().getBlockZ()),
                         String.format("to the material '%s'", specifiedMaterial),
                         String.format("With a damage value of '%s'", specifiedMetadata)
        );
    }

    public static boolean bindInfiniteItem(final Player runningPlayer, final ChestStoredMH chestMetadataHandler) {
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            final ItemStack heldItem = runningPlayer.getInventory().getItemInMainHand();
            final Material itemType = heldItem.getType();
            final byte itemHorrificMetadata = heldItem.getData().getData();

            reportInfiniteChestCreation(runningPlayer, foundBlock, itemType, itemHorrificMetadata);

            return chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(itemType, itemHorrificMetadata));
        }
        return false;
    }

    public static boolean unbindInfinite(final Player runningPlayer, final ChestStoredMH chestMetadataHandler) {
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        final boolean wasInfinite = chestMetadataHandler.givenBlockIsInfiniteChest(foundBlock);
        boolean isNowFinite = false;

        if (wasInfinite) {
            final HorrificBytesMaterialBundle itemInfo = chestMetadataHandler.getInfiniteItem(foundBlock);
            isNowFinite = chestMetadataHandler.makeBlockFiniteChest(foundBlock);

            if (isNowFinite) {
                if (AirMaterialUtils.materialIsSomeFormOfAir(itemInfo.getItemType())) {
                    final String hologramName = formatChestLocation(foundBlock.getLocation());
                    final HologramManager hologramManager = JavaPlugin.getPlugin(HologramPlugin.class)
                                                                      .getHologramManager();

                    Hologram foundHologram = hologramManager.getHologram(hologramName);

                    if (foundHologram != null) {
                        hologramManager.deleteHologram(foundHologram);
                    } else {
                        PLI.sendToPlayer(runningPlayer, "Warning: Infinite chest did not have a linked hologram!");
                    }
                }

                PLI.sendToPlayer(runningPlayer,
                                 String.format("Unbound infinite item of '%s'", itemInfo.getItemType()),
                                 String.format("And damage value of '%s' from chest", itemInfo.getItemMetadata())
                );
            }
        } else {
            PLI.sendToPlayer(runningPlayer,"Chest was not infinite");
        }

        return wasInfinite && isNowFinite;
    }

    public static boolean forceBindInfiniteItem(final Player runningPlayer, final String specifiedMaterial, final byte specifiedMetadata, final ChestStoredMH chestMetadataHandler) {
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            final Material specifiedBD = Material.matchMaterial(specifiedMaterial, false);

            if (specifiedBD != null) {
                reportInfiniteChestCreation(runningPlayer, foundBlock, specifiedBD, specifiedMetadata);

                return chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(specifiedBD, specifiedMetadata));
            } else {
                PLI.sendToPlayer(runningPlayer, String.format("Unable to bind chest to unrecognised argument: %s", specifiedMaterial));
            }
        }
        return false;
    }
}
