package org.core.cgs.generic.interfaces;

import org.bukkit.command.CommandExecutor;

import java.util.Set;

public interface CGSCommandExecutor extends CommandExecutor {
    Set<String> getCommands();
}
