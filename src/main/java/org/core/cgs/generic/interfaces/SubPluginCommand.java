package org.core.cgs.generic.interfaces;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface SubPluginCommand {
    String getCommand();

    static Set<String> getCommands(final SubPluginCommand[] values) {
        return Arrays.stream(values)
                     .map(SubPluginCommand::getCommand)
                     .collect(Collectors.toSet());
    }

    static <T extends SubPluginCommand> Optional<T> matchCommand(final T[] commands, final String toMatch) {
        return Arrays.stream(commands)
                     .filter(command -> command.getCommand()
                                               .equalsIgnoreCase(toMatch))
                     .findFirst();
    }
}
