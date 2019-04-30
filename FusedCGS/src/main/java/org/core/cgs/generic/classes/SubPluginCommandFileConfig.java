package org.core.cgs.generic.classes;

import com.esotericsoftware.yamlbeans.YamlReader;
import org.core.cgs.Core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class SubPluginCommandFileConfig {
    private final String pluginName;
    private final String commandName;
    private final Map<String, SubPluginSubCommand> allSubCommands;
    private final SubPluginSubCommand helpCommand;

    public SubPluginCommandFileConfig(final String pathToConfigFile) {
        final Map<Object, Object> unpackedMapList = obtainMapListFromYamlFile(pathToConfigFile);

        pluginName = getString(unpackedMapList, "plugin-name");
        commandName = getString(unpackedMapList, "super-command");
        allSubCommands = unloadFromYamlFile(unpackedMapList);
        helpCommand = allSubCommands.get("help");
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getCommandName() {
        return commandName;
    }

    public Map<String, SubPluginSubCommand> getAllSubCommands() {
        return allSubCommands;
    }

    public SubPluginSubCommand getSubCommandFromName(final String subCommandName) {
        return allSubCommands.getOrDefault(subCommandName, helpCommand);
    }

    private Map<Object, Object> obtainMapListFromYamlFile(final String pathToConfigFile) {
        try {
            final InputStream in = Core.class.getResourceAsStream(pathToConfigFile);
            final InputStreamReader iSR = new InputStreamReader(in);
            final BufferedReader reader = new BufferedReader(iSR);
            final YamlReader yR = new YamlReader(reader);
            final Map<Object, Object> output = (Map<Object, Object>)(yR.read());

            yR.close();
            reader.close();
            iSR.close();
            in.close();
            return output;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getString(final Map<Object, Object> configMap, final String key) {
        return configMap.get(key)
                        .toString();
    }

    private Map<String, SubPluginSubCommand> unloadFromYamlFile(final Map<Object, Object> configMap) {
        final Map<String, SubPluginSubCommand> allSubCommands = new HashMap<>();
        final Map<Object, Object> allSubCommandInformation = (Map<Object, Object>)configMap.get("sub-commands");

        for (Map.Entry<Object, Object> subCommand : allSubCommandInformation.entrySet()) {
            final String subCommandName = subCommand.getKey().toString();
            final Map<Object, Object> subCommandValues = (Map<Object, Object>)subCommand.getValue();

            allSubCommands.put(subCommandName, getSubCommandFromMap(subCommandName, subCommandValues));
        }

        return allSubCommands;
    }

    private SubPluginSubCommand getSubCommandFromMap(final String subCommandName, final Map<Object, Object> objectMap) {
        return new SubPluginSubCommand(
                subCommandName,
                objectMap.getOrDefault("description", "").toString(),
                objectMap.getOrDefault("usage", String.format("Run /%s %s", commandName, subCommandName)).toString(),
                objectMap.getOrDefault("arguments", "<None>").toString(),
                objectMap.getOrDefault("minimum-arity", 0).toString()
        );
    }
}
