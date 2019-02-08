package org.core.cgs.generic.utilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.SubPluginCommandFileConfig;
import org.core.cgs.generic.classes.SubPluginSubCommand;

import java.util.stream.Collectors;

public final class HelpCommandUtils {
    private static String formatCommandAndArguments(final String pluginName, final SubPluginSubCommand sPSC) {
        return String.format("%s/%s %s %s%s",
                             ChatColor.GRAY,
                             pluginName,
                             sPSC.getName(),
                             ChatColor.WHITE,
                             sPSC.getArguments()
        );
    }

    public static boolean displayHelp(final Player runningPlayer,
                                      final PlayerInterface playerInterface,
                                      final String[] args,
                                      final SubPluginCommandFileConfig sPCH,
                                      final SubPluginSubCommand sPSC) {
        if (args.length > 1) {
            playerInterface.sendToPlayer(runningPlayer, "",
                                            String.format("%sHelp for subcommand '%s\'", ChatColor.YELLOW, sPSC.getName()),
                                            String.format("%sArguments: %s",                             ChatColor.YELLOW,                                   formatCommandAndArguments(sPCH.getCommandName(), sPSC)),
                                            String.format("%sDescription: %s%s",                         ChatColor.YELLOW, ChatColor.GRAY,                   sPSC.getDescription()),
                                            String.format("%sUsage: %s%s%s and run the command /%s %s'", ChatColor.YELLOW, ChatColor.GRAY, ChatColor.ITALIC, sPSC.getUsage(), sPCH.getCommandName(), sPSC.getName())
            );
        } else {
            final String[] content = sPCH.getAllSubCommands()
                                         .entrySet()
                                         .stream()
                                         .map((entry) -> formatCommandAndArguments(sPCH.getCommandName(), entry.getValue()))
                                         .collect(Collectors.toList())
                                         .toArray(new String[sPCH.getAllSubCommands().size()]); // I deeply despise java

            playerInterface.sendToPlayer(runningPlayer, content);
        }
        return true;
    }
}
