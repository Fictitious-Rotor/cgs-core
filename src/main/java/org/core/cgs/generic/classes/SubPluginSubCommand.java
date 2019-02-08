package org.core.cgs.generic.classes;

public final class SubPluginSubCommand {
    private final String name;
    private final String description;
    private final String usage;
    private final String arguments;
    private final int minimumArity;

    private String checkArg(final String argName, final String arg) {
        if (arg == null || arg.isEmpty()) {
            throw new MissingParameterException(argName);
        }
        return arg;
    }

    public SubPluginSubCommand(final String name,
                               final String description,
                               final String usage,
                               final String arguments,
                               final String minimumArity) {
        this.name           = checkArg("SubCommandName", name);
        this.description    = checkArg("Description", description);
        this.usage          = checkArg("Usage", usage);
        this.arguments      = checkArg("Arguments", arguments);
        this.minimumArity   = Integer.parseInt(checkArg("MinimumArity", minimumArity));
    }

    public String getHelp() {
        return String.format("Description: %s\nUsage: %s\nArguments: %s\nMinimim arity: %s", description, usage, arguments, minimumArity);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getArguments() {
        return arguments;
    }

    public int getMinimumArity() {
        return minimumArity;
    }

    private final class MissingParameterException extends RuntimeException {
        private MissingParameterException(final String parameterName) {
            super(parameterName);
        }
    }
}
