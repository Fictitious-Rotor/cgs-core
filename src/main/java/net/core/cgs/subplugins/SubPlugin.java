package net.core.cgs.subplugins;

import net.core.cgs.Core;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;

public abstract class ASubPlugin {
    protected final Core plugin;
    protected CommandExecutor commandExecutor;
    protected String subPluginName;
    protected Set<Listener> listeners;
    protected Set<String> commands;

    public ASubPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public String getName() {
        return subPluginName;
    }

    public void registerCommands() {
        commands.forEach(c -> plugin.getCommand(c).setExecutor(commandExecutor));
    }

    public void registerEvents(PluginManager pluginManager) {
        listeners.forEach(l -> pluginManager.registerEvents(l, plugin));
    }
}
