package org.core.cgs.subplugins.lightutils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.core.cgs.generic.interfaces.CGSCommandExecutor;
import org.core.cgs.generic.interfaces.SubPluginCommand;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;

import java.util.Optional;
import java.util.Set;

import static org.core.cgs.subplugins.lightutils.functions.LUFunctions.*;

public class LUCommandExecutor implements CGSCommandExecutor {
    enum LUCommand implements SubPluginCommand {
        LIGHT_UTILS("lightutils"),
        CREATE_LIGHT("createlight"),
        REMOVE_LIGHT("removelight"),
        MAKE_BLOCK_INTO_LIGHT("makeblockintolight"),
        BLOCKIFY_LIGHT_IN_RADIUS("blockifylightinradius"),
        LIGHTIFY_BLOCKS_IN_RADIUS("lightifyblocksinradius"),
        BLOCKIFY_LIGHT_IN_RANGE("blockifylightinrange"),
        LIGHTIFY_BLOCKS_IN_RANGE("lightifyblocksinrange");

        private String commandName;

        LUCommand(String commandName) {
            this.commandName = commandName;
        }
        public String getCommand() {
            return commandName;
        }
        static Set<String> getCommands() {
            return SubPluginCommand.getCommands(values());
        }
        static Optional<LUCommand> matchCommand(final String toMatch) {
            return SubPluginCommand.matchCommand(values(), toMatch);
        }
    }

    private final LightLocationStoredMH lightLocationMetadata;
    private final CuboidSelectionSessionMH cuboidSelectionMetadata;

    LUCommandExecutor(LightLocationStoredMH lightLocationMetadata, CuboidSelectionSessionMH cuboidSelectionMetadata) {
        this.lightLocationMetadata = lightLocationMetadata;
        this.cuboidSelectionMetadata = cuboidSelectionMetadata;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        Player runningPlayer;

        if (sender instanceof Player) {
            runningPlayer = (Player)sender;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        LUCommand sPC = LUCommand.matchCommand(command.getName())
                                 .orElse(LUCommand.LIGHT_UTILS);

        switch (sPC) {
            case CREATE_LIGHT: return commandCreateLight(runningPlayer, split, lightLocationMetadata);
            case REMOVE_LIGHT: return commandRemoveLight(runningPlayer, lightLocationMetadata);
            case MAKE_BLOCK_INTO_LIGHT: return convertBlockIntoLight(runningPlayer, lightLocationMetadata);
            case BLOCKIFY_LIGHT_IN_RADIUS: return blockifyLightInRadius(runningPlayer, split, lightLocationMetadata);
            case LIGHTIFY_BLOCKS_IN_RADIUS: return lightifyBlockInRadius(runningPlayer, split, lightLocationMetadata);
            case BLOCKIFY_LIGHT_IN_RANGE: return blockifyLightInRange(runningPlayer, cuboidSelectionMetadata, lightLocationMetadata);
            case LIGHTIFY_BLOCKS_IN_RANGE: return lightifyBlocksInRange(runningPlayer, cuboidSelectionMetadata, lightLocationMetadata);
            default: return false;
        }
    }

    @Override
    public Set<String> getCommands() {
        return LUCommand.getCommands();
    }
}
