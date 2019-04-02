package org.core.cgs.generic.classes;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.core.cgs.Core;
import org.core.cgs.codegen.FoundSubpluginMaterials;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.HelpCommandUtils;
import org.core.cgs.generic.utilities.PlayerInterface;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

import static java.util.logging.Level.*;
import static org.core.cgs.Core.LOGGER;

public final class SubPluginCommandExecutor implements CommandExecutor {
    private final SubPluginCommandFileConfig config;
    private final PlayerInterface PLI;
    private final MetadataBundle metadataBundle;
    private final Set<CommandBackend> allCommandBackends;

    SubPluginCommandExecutor(final String subPluginName,
                             final SubPluginCommandFileConfig config,
                             final MetadataBundle metadataBundle,
                             final PlayerInterface PLI) {
        this.PLI = PLI;
        this.config = config;
        this.metadataBundle = metadataBundle;
        this.allCommandBackends = FoundSubpluginMaterials.getFoundCommandBackends(subPluginName);
    }

    private Optional<CommandBackend> getCommandBackendFromName(final String commandName) {
        return allCommandBackends.stream()
                                 .filter(cB -> cB.getClass()
                                                 .getSimpleName()
                                                 .equalsIgnoreCase(commandName))
                                 .findFirst();
    }

    @Override
    public boolean onCommand(final CommandSender sender,
                             final Command command,
                             final String label,
                             final String[] arguments) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        final Player runningPlayer = (Player)sender;

        if (ArrayUtils.isEmpty(arguments) || "help".equals(arguments[0])) {
            final String commandName = (arguments.length > 1) ? arguments[1] : null;

            HelpCommandUtils.displayHelp(PLI.prime(runningPlayer), config, commandName);

            return true;
        } else {
            final Optional<CommandBackend> foundBackend = getCommandBackendFromName(arguments[0]);

            foundBackend.ifPresent(commandBackend -> commandBackend.run(runningPlayer, PLI.prime(runningPlayer), arguments, metadataBundle));
            return foundBackend.isPresent();
        }
    }
}
