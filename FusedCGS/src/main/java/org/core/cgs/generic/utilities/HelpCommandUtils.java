package org.core.cgs.generic.utilities;

import org.bukkit.ChatColor;
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

    public static void displayHelp(final PrimedPLI primedPLI,
                                   final SubPluginCommandFileConfig sPCH,
                                   final String commandName) {
        final SubPluginSubCommand sPSC = sPCH.getSubCommandFromName(commandName);

        if (!(sPSC.isHelpCommand())) {
            primedPLI.sendToPlayer("",
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

            primedPLI.sendToPlayer(content);
        }
    }
}
