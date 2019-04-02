package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.stored.LightLocationStoredMH;

import static org.core.cgs.subplugins.lightutils.utils.LightUtils.makeLight;
import static org.core.cgs.subplugins.lightutils.utils.ReportUtils.reportCreation;

public class tolight implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        final LightLocationStoredMH mH = metadataBundle.getHandler(LightLocationStoredMH.class);
        final Location location = runningPlayer.getTargetBlock(null, 10)
                                               .getLocation();
        runningPlayer.getWorld()
                     .getBlockAt(location)
                     .setType(Material.AIR);
        final boolean succeeded = makeLight(runningPlayer, location, 15, mH);

        reportCreation(PPLI, succeeded);
    }
}