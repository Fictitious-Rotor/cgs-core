package org.core.splitcgs.infinitechests.commons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerNeededCommons implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be run by a player.");
            return false;
        }
        return runPlayerCommand((Player)commandSender, command, s, strings);
    }

    protected abstract boolean runPlayerCommand(final Player player, final Command command, final String s, final String[] arguments);
}
