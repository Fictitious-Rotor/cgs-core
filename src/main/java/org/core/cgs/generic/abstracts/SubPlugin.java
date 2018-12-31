package org.core.cgs.generic.abstracts;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.core.cgs.Core;
import org.core.cgs.generic.exceptions.CommandNotInYamlException;
import org.core.cgs.generic.interfaces.CGSCommandExecutor;

import java.util.Set;

public abstract class SubPlugin {
    private final String subPluginName;
    private final Set<String> commands;
    private final CommandExecutor commandExecutor;
    private final Set<Listener> listeners;

    public SubPlugin(final String subPluginName, final CGSCommandExecutor commandExecutor, final Set<Listener> listeners) {
        this.subPluginName = subPluginName;
        this.commandExecutor = commandExecutor;
        this.commands = commandExecutor.getCommands();
        this.listeners = listeners;
    }

    public String getName() {
        return subPluginName;
    }

    public void registerCommands(Core plugin) {
        for (String command : commands) {
            PluginCommand pluginCommand = plugin.getCommand(command);

            if (pluginCommand != null) {
                pluginCommand.setExecutor(commandExecutor);
            } else {
                throw new CommandNotInYamlException(command);
            }
        }
    }

    public void registerEvents(Core plugin, PluginManager pluginManager) {
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }
}
