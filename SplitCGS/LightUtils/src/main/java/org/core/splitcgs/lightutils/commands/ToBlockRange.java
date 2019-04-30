package org.core.splitcgs.lightutils.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.commons.RangeCommons;
import org.core.splitcgs.lightutils.utils.LightUtils;
import org.core.splitcgs.lightutils.utils.ReportUtils;

public class ToBlockRange extends RangeCommons {
    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        performRangeOperation(runningPlayer, LightUtils::convertLightIntoBlock, ReportUtils::reportMassBlockification);
    }
}
