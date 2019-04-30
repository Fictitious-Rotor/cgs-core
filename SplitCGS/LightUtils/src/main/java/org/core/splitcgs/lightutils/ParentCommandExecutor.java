package org.core.splitcgs.lightutils;

import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.core.splitcgs.lightutils.commands.*;

import javax.inject.Inject;
import java.util.Map;

public class ParentCommandExecutor implements CommandExecutor {
    @Inject private Create create;
    @Inject private IsLit isLit;
    @Inject private IsLitRadial isLitRadial;
    @Inject private IsLitRange isLitRange;
    @Inject private Remove remove;
    @Inject private RemoveRadial removeRadial;
    @Inject private RemoveRange removeRange;
    @Inject private ToBlockRadial toBlockRadial;
    @Inject private ToBlockRange toBlockRange;
    @Inject private ToLight toLight;
    @Inject private ToLightRadial toLightRadial;
    @Inject private ToLightRange toLightRange;
    @Inject private Wand wand;

    private final Map<String, CommandExecutor> redirection;

    @Inject public ParentCommandExecutor() { // Instantiate all fields
        redirection = ImmutableMap.<String, CommandExecutor>builder()
                                  .put("create", create)
                                  .put("isLit", isLit)
                                  .put("isLitRadial", isLitRadial)
                                  .put("isLitRange", isLitRange)
                                  .put("remove", remove)
                                  .put("removeRadial", removeRadial)
                                  .put("removeRange", removeRange)
                                  .put("toBlockRadial", toBlockRadial)
                                  .put("toBlockRange", toBlockRange)
                                  .put("toLight", toLight)
                                  .put("toLightRadial", toLightRadial)
                                  .put("toLightRange", toLightRange)
                                  .put("wand", wand)
                                  .build();
    }

    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String aliasUsed,
                             final String[] args) {
        if (args.length == 0) { return false; }

        final CommandExecutor subCommand = redirection.get(args[0]);

        return (subCommand != null)
                && subCommand.onCommand(commandSender, command, args[0], args);
    }
}
