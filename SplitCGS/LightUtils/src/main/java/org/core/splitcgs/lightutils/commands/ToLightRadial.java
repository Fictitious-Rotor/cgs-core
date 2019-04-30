package org.core.splitcgs.lightutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.RadialCommons;
import org.core.splitcgs.lightutils.utils.LightUtils;
import org.core.splitcgs.lightutils.utils.ReportUtils;

public class ToLightRadial extends RadialCommons implements CommandExecutor {
    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        performRadialOperation(runningPlayer, args, LightUtils::convertBlockIntoLight, ReportUtils::reportMassLightification);
    }
}
