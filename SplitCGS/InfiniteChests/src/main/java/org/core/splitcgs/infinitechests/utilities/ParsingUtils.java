package org.core.splitcgs.infinitechests.utilities;

import java.util.logging.Level;

import static org.core.splitcgs.infinitechests.InfiniteChestsCore.LOGGER;

public class ParsingUtils {
    public static int getIntFromFirstArg(final String[] args, final int defaultValue) {
        if((args != null) && (args.length > 0)) {
            try {
                return Integer.parseInt(args[0]);
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Unable to parse first arg of {0}", args[0]);
            }
        }

        return defaultValue;
    }
}