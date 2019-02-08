package org.core.cgs.generic.classes;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.core.cgs.Core;
import org.core.cgs.generic.abstracts.CGSListener;
import org.core.cgs.generic.interfaces.MetadataHandler;
import org.core.cgs.generic.utilities.PlayerInterface;

import java.util.Set;

import static org.core.cgs.generic.utilities.ExceptionUtils.getNullSafeMessage;
import static org.core.cgs.generic.utilities.ReflectionUtils.instantiateClasses;

public final class SubPlugin {
    private final String subPluginName;
    private final String subPluginParentCommand;
    private final SubPluginCommandExecutor commandExecutor;
    private final Set<? extends CGSListener> listeners;

    public SubPlugin(final String configLocation) {
        final SubPluginCommandFileConfig config = new SubPluginCommandFileConfig(configLocation);

        this.subPluginName = config.getPluginName();
        this.subPluginParentCommand = config.getCommandName();

        final PlayerInterface playerInterface = new PlayerInterface(subPluginParentCommand);
        final String subPluginPackage = String.format("org.core.cgs.subplugins.%s", config.getCommandName());
        final MetadataBundle metadataBundle = new MetadataBundle(instantiateClasses(subPluginPackage, "metadata", MetadataHandler.class));

        this.commandExecutor = new SubPluginCommandExecutor(config, metadataBundle, playerInterface);
        this.listeners = instantiateClasses(subPluginPackage,
                                            "listeners",
                                            CGSListener.class,
                                            new Class<?>[]{ MetadataBundle.class, PlayerInterface.class },
                                            new Object[]{metadataBundle, playerInterface });
    }

    public String getName() {
        return subPluginName;
    }

    public void registerCommands(Core plugin) {
        final PluginCommand pluginCommand = plugin.getCommand(subPluginParentCommand);

        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);
        } else {
            throw new PluginNotInYamlException(subPluginParentCommand);
        }
    }

    public void registerEvents(Core plugin, PluginManager pluginManager) {
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }

    private class PluginNotInYamlException extends RuntimeException {
        private PluginNotInYamlException(String command) {
            super(getNullSafeMessage("Command '%s' is not in the plugin.yml file!", command));
        }
    }
}
