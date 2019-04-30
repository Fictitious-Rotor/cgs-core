package org.core.cgs.generic.classes;

public final class SubPluginSubCommand {
    private final String name;
    private final String description;
    private final String usage;
    private final String arguments;
    private final int minimumArity;
    private final boolean isHelpCommand;

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

        this.isHelpCommand = "help".equals(name);
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

    public boolean isHelpCommand() {
        return isHelpCommand;
    }

    private final class MissingParameterException extends RuntimeException {
        private MissingParameterException(final String parameterName) {
            super(parameterName);
        }
    }
}
