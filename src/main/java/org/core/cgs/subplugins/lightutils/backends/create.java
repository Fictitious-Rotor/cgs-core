package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;

import static org.core.cgs.subplugins.lightutils.utils.LightUtils.getLightLevelFromArgs;
import static org.core.cgs.subplugins.lightutils.utils.LightUtils.makeLight;
import static org.core.cgs.subplugins.lightutils.utils.ReportUtils.reportCreation;

public class create implements CommandBackend {
    @Override
    public boolean run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final LightLocationStoredMH mH = metadataBundle.getHandler(LightLocationStoredMH.class);

        final int level = getLightLevelFromArgs(arguments);
        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        final boolean succeeded = makeLight(runningPlayer, targetedBlock.getLocation(), level, mH);

        reportCreation(PPLI.prime(runningPlayer), succeeded);
        return succeeded;
    }
}
