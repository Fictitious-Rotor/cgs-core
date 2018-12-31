package org.core.cgs.subplugins.infinitechests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.core.cgs.generic.interfaces.CGSCommandExecutor;
import org.core.cgs.generic.interfaces.SubPluginCommand;
import org.core.cgs.subplugins.infinitechests.metadata.ChestStoredMH;

import java.util.Optional;
import java.util.Set;

import static org.core.cgs.subplugins.infinitechests.functions.ICFunctions.*;

public class ICCommandExecutor implements CGSCommandExecutor {
    private enum ICCommand implements SubPluginCommand {
        INFINITE_CHESTS("infinitechests"),
        BIND_INFINITE_ITEM("bindinfiniteitem"),
        UNBIND_INFINITE_ITEM("unbindinfiniteitem"),
        FORCE_BIND_INFINITE_ITEM("forcebindinfiniteitem");

        private String commandName;

        ICCommand(String commandName) {
            this.commandName = commandName;
        }
        public String getCommand() {
            return commandName;
        }
        static Set<String> getCommands() {
            return SubPluginCommand.getCommands(values());
        }
        static Optional<ICCommand> matchCommand(final String toMatch) {
            return SubPluginCommand.matchCommand(values(), toMatch);
        }
    }

    private final ChestStoredMH chestMetadataHandler;

    ICCommandExecutor(ChestStoredMH chestMetadataHandler) {
        this.chestMetadataHandler = chestMetadataHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        Player runningPlayer;

        if (sender instanceof Player) {
            runningPlayer = (Player)sender;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        ICCommand sPC = ICCommand.matchCommand(command.getName())
                                 .orElse(ICCommand.INFINITE_CHESTS);

        switch (sPC) {
            case BIND_INFINITE_ITEM : return bindInfiniteItem(runningPlayer, chestMetadataHandler);
            case UNBIND_INFINITE_ITEM : return unbindInfinite(runningPlayer, chestMetadataHandler);
            case FORCE_BIND_INFINITE_ITEM: return (arguments.length > 1) && forceBindInfiniteItem(runningPlayer, arguments[0], Byte.parseByte(arguments[1]), chestMetadataHandler);
            default: return false;
        }
    }

    @Override
    public Set<String> getCommands() {
        return ICCommand.getCommands();
    }


}
