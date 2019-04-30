package org.core.splitcgs.lightutils.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.PlayerDemander;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;

import javax.inject.Inject;

import static org.core.splitcgs.lightutils.utils.LightUtils.makeLight;
import static org.core.splitcgs.lightutils.utils.ReportUtils.reportCreation;

public class ToLight extends PlayerDemander{
    @Inject private LightLocationStoredMH lightLocationHandler;

    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        final Location location = runningPlayer.getTargetBlock(null, 10)
                                               .getLocation();
        runningPlayer.getWorld()
                     .getBlockAt(location)
                     .setType(Material.AIR);
        final boolean succeeded = makeLight(runningPlayer, location, 15, lightLocationHandler);

        reportCreation(runningPlayer, succeeded);
    }
}