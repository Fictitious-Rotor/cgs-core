package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.stored.LightLocationStoredMH;

public class islit implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final LightLocationStoredMH lightLocationMH = metadataBundle.getHandler(LightLocationStoredMH.class);

        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);

        PPLI.sendToPlayer(lightLocationMH.lightIsAtLocation(targetedBlock.getLocation()) ? "An artificial light source is present here" : "There are no artificial light sources here");
    }
}
