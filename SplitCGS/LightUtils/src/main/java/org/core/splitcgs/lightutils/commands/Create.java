package org.core.splitcgs.lightutils.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.PlayerDemander;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;

import javax.inject.Inject;

import static org.core.splitcgs.lightutils.utils.LightUtils.getLightLevelFromArgs;
import static org.core.splitcgs.lightutils.utils.LightUtils.makeLight;
import static org.core.splitcgs.lightutils.utils.ReportUtils.reportCreation;

public class Create extends PlayerDemander {
    @Inject private LightLocationStoredMH lightLocationMetadata;

    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                         final Command command,
                                         final String aliasUsed,
                                         final String[] args) {
        final int level = getLightLevelFromArgs(args);
        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        final boolean succeeded = makeLight(runningPlayer, targetedBlock.getLocation(), level, lightLocationMetadata);

        reportCreation(runningPlayer, succeeded);
    }
}
