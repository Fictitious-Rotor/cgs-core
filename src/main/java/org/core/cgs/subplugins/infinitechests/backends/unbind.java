package org.core.cgs.subplugins.infinitechests.backends;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.infinitechests.commons.ICCommons;
import org.core.cgs.subplugins.infinitechests.metadata.stored.ChestStoredMH;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.cgs.subplugins.infinitechests.utilities.AirMaterialUtils;

public class unbind extends ICCommons implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);
        final ChestStoredMH chestMetadataHandler = metadataBundle.getHandler(ChestStoredMH.class);

        unbindInfinite(foundBlock, PPLI, chestMetadataHandler);
    }

    public static void unbindInfinite(final Block foundBlock,
                                         final PrimedPLI PPLI,
                                         final ChestStoredMH chestMetadataHandler) {
        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            PPLI.sendToPlayer("This command only works on chests", "Run '/infinitechests help unbind' for more information");
        }

        final boolean wasInfinite = chestMetadataHandler.givenBlockIsInfiniteChest(foundBlock);

        if (wasInfinite) {
            final HorrificBytesMaterialBundle itemInfo = chestMetadataHandler.getInfiniteItem(foundBlock);
            final boolean isNowFinite = chestMetadataHandler.makeBlockFiniteChest(foundBlock);

            if (isNowFinite) {
                if (AirMaterialUtils.materialIsSomeFormOfAir(itemInfo.getItemType())) {
                    removeVoidChest(PPLI, foundBlock);
                }

                PPLI.sendToPlayer(String.format("Unbound infinite item of '%s'", itemInfo.getItemType()),
                                  String.format("And damage value of '%s' from chest", itemInfo.getItemMetadata())
                );
            }
        } else {
            PPLI.sendToPlayer("Chest was not infinite");
        }
    }

    private static void removeVoidChest(final PrimedPLI PPLI,
                                        final Block foundBlock) {
        final String hologramName = formatChestLocation(foundBlock.getLocation());
        final HologramManager hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
        final Hologram foundHologram = hologramManager.getHologram(hologramName);

        if (foundHologram != null) {
            hologramManager.deleteHologram(foundHologram);
        } else {
            PPLI.sendToPlayer("Warning: Infinite chest did not have a linked hologram!");
        }
    }
}
