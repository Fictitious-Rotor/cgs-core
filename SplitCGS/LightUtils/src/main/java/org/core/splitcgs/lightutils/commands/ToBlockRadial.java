package org.core.splitcgs.lightutils.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.RadialCommons;
import org.core.splitcgs.lightutils.utils.LightUtils;
import org.core.splitcgs.lightutils.utils.ReportUtils;

public class ToBlockRadial extends RadialCommons {
    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        performRadialOperation(runningPlayer, args, LightUtils::convertLightIntoBlock, ReportUtils::reportMassBlockification);
    }
}
