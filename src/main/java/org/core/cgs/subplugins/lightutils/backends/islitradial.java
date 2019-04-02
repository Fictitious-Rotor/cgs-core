package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.commons.RadialCommons;
import org.core.cgs.subplugins.lightutils.utils.LightUtils;
import org.core.cgs.subplugins.lightutils.utils.ReportUtils;

public class islitradial extends RadialCommons implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        performRadialOperation(PPLI, arguments, metadataBundle, LightUtils::identifyBlock, ReportUtils::reportMassIdentification);
    }
}
