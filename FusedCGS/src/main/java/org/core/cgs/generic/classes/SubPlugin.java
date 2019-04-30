package org.core.cgs.generic.classes;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.core.cgs.Core;
import org.core.cgs.generic.abstracts.CGSListener;
import org.core.cgs.generic.utilities.PlayerInterface;

import java.util.Set;

import static org.core.cgs.generic.utilities.ExceptionUtils.getNullSafeMessage;

public final class SubPlugin {
    private final String subPluginEnglishName;
    private final String subPluginSimpleName;
    private final String subPluginParentCommand;
    private final SubPluginCommandExecutor commandExecutor;
    private final Set<? extends CGSListener> listeners;
    private final MetadataBundle metadataBundle;

    public SubPlugin(final String pluginName, final MetadataBundle metadataBundle, final Set<? extends CGSListener> allListeners) {
        final SubPluginCommandFileConfig config = new SubPluginCommandFileConfig(String.format("subplugins/commands/%s.yml", pluginName));

        this.subPluginSimpleName = pluginName;
        this.subPluginEnglishName = config.getPluginName();
        this.subPluginParentCommand = config.getCommandName();

        final PlayerInterface playerInterface = new PlayerInterface(subPluginParentCommand);

        this.commandExecutor = new SubPluginCommandExecutor(pluginName, config, metadataBundle, playerInterface);
        this.listeners = allListeners;
        this.metadataBundle = metadataBundle;
    }

    public String getSimpleName() {
        return subPluginSimpleName;
    }

    public String getEnglishName() {
        return subPluginEnglishName;
    }

    public void registerCommands(final Core plugin) {
        final PluginCommand pluginCommand = plugin.getCommand(subPluginParentCommand);

        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);
        } else {
            throw new PluginNotInYamlException(subPluginParentCommand);
        }
    }

    public void registerEvents(final Core plugin, final PluginManager pluginManager) {
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }

    public void registerMetadataHandlers() {
        metadataBundle.registerHandlers();
    }

    private class PluginNotInYamlException extends RuntimeException {
        private PluginNotInYamlException(String command) {
            super(getNullSafeMessage("Command '%s' is not in the plugin.yml file!", command));
        }
    }
}
