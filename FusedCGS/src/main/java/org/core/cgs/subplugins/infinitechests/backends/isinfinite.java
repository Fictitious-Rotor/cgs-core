package org.core.cgs.subplugins.infinitechests.backends;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.infinitechests.metadata.stored.ChestStoredMH;

public class isinfinite implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final ChestStoredMH chestMetadataHandler = metadataBundle.getHandler(ChestStoredMH.class);
        final Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            PPLI.sendToPlayer("This command only works on chests.", "Run '/infinitechests help isinfinite' for more information");
            return;
        }

        PPLI.sendToPlayer(chestMetadataHandler.givenBlockIsInfinite(foundBlock) ? "Chest is infinite" : "Chest is not infinite");
    }
}
