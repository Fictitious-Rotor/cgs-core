package org.core.splitcgs.lightutils.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.LightUtilsCore;
import org.core.splitcgs.lightutils.commons.PlayerDemander;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;

import javax.inject.Inject;

public class IsLit extends PlayerDemander {
    @Inject private LightLocationStoredMH lightLocationMetadata;

    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        final Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        LightUtilsCore.sendToPlayer(runningPlayer, lightLocationMetadata.lightIsAtLocation(targetedBlock.getLocation()) ? "An artificial light source is present here" : "There are no artificial light sources here");
    }
}
