package org.core.cgs.generic.exceptions;

import static org.core.cgs.generic.utilities.ExceptionUtils.getNullSafeMessage;

public class CommandNotInYamlException extends RuntimeException {
    public CommandNotInYamlException(String command) {
        super(getNullSafeMessage("Command '%s' is not in the plugin.yml file!", command));
    }
}
