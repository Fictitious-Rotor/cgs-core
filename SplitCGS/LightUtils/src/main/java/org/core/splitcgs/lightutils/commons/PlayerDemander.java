package org.core.splitcgs.lightutils.commons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerDemander implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String aliasUsed,
                             final String[] args) {
        if (commandSender instanceof Player) {
            handlePlayerCommand((Player)commandSender, command, aliasUsed, args);
            return true;
        } else {
            return false;
        }
    }

    protected abstract void handlePlayerCommand(final Player runningPlayer,
                                                   final Command command,
                                                   final String aliasUsed,
                                                   final String[] args);
}
