package org.core.cgs.subplugins.infinitechests.backends;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.infinitechests.commons.BindCommons;
import org.core.cgs.subplugins.infinitechests.metadata.stored.ChestStoredMH;

public class bindinfo extends BindCommons implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final ChestStoredMH chestMetadataHandler = metadataBundle.getHandler(ChestStoredMH.class);
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            PPLI.sendToPlayer("This command only works on chests", "Run '/infinitechests help bindinfo' for more information");
            return;
        }

        if (!(chestMetadataHandler.givenBlockIsInfinite(foundBlock))) {
            PPLI.sendToPlayer("The specified chest is not infinite");
            return;
        }

        reportInfiniteChestCreation(PPLI, foundBlock, chestMetadataHandler.getInfiniteItem(foundBlock));
    }
}
