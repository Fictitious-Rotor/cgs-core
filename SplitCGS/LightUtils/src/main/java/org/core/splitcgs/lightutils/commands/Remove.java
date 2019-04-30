package org.core.splitcgs.lightutils.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.PlayerDemander;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;

import javax.inject.Inject;

import static org.core.splitcgs.lightutils.utils.LightUtils.killLight;
import static org.core.splitcgs.lightutils.utils.ReportUtils.reportDestruction;

public class Remove extends PlayerDemander {
    @Inject private LightLocationStoredMH lightLocationHandler;

    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        final boolean succeeded = killLight(runningPlayer, targetedBlock.getLocation(), lightLocationHandler);

        reportDestruction(runningPlayer, succeeded);
    }
}