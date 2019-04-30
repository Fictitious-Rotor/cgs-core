package org.core.cgs.subplugins.infinitechests.backends;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.infinitechests.commons.BindCommons;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.cgs.subplugins.infinitechests.metadata.stored.ChestStoredMH;

public class forcebind extends BindCommons implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                    final PrimedPLI PPLI,
                    final String[] arguments,
                    final MetadataBundle metadataBundle) {
        final ChestStoredMH chestMetadataHandler = metadataBundle.getHandler(ChestStoredMH.class);

        if (arguments.length < 2) {
            PPLI.sendToPlayer("Not enough arguments provided!", "Run '/infinitechests help forcebind' for more information");
            return;
        }

        final String specifiedMaterial = arguments[0];
        final byte specifiedMetadata = Byte.parseByte(arguments[1]);
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        forceBindInfiniteItem(specifiedMaterial, specifiedMetadata, foundBlock, chestMetadataHandler, PPLI);
    }

    private static void forceBindInfiniteItem(final String specifiedMaterial,
                                              final byte specifiedMetadata,
                                              final Block foundBlock,
                                              final ChestStoredMH chestMetadataHandler,
                                              final PrimedPLI PPLI) {
        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            PPLI.sendToPlayer("This command only works on chests", "Run '/infinitechests help forcebind' for more information");
            return;
        }

        final Material specifiedBD = Material.matchMaterial(specifiedMaterial, false);

        if (specifiedBD != null) {
            reportInfiniteChestCreation(PPLI, foundBlock, specifiedBD, specifiedMetadata);

            chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(specifiedBD, specifiedMetadata));
        } else {
            PPLI.sendToPlayer(String.format("Unable to bind chest to unrecognised argument: %s", specifiedMaterial), "Run '/infinitechests help forcebind' for more information");
        }
    }
}
