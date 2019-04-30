package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.stored.LightLocationStoredMH;

import static org.core.cgs.subplugins.lightutils.utils.LightUtils.killLight;
import static org.core.cgs.subplugins.lightutils.utils.ReportUtils.reportDestruction;

public class remove implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final LightLocationStoredMH mH = metadataBundle.getHandler(LightLocationStoredMH.class);

        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        final boolean succeeded = killLight(runningPlayer, targetedBlock.getLocation(), mH);

        reportDestruction(PPLI, succeeded);
    }
}