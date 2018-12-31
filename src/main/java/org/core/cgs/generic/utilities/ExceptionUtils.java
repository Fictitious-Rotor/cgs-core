package org.core.cgs.generic.utilities;

public final class ExceptionUtils {
    public static String getNullSafeMessage(final String format, final String message) {
        final String nullSafeMessage = (message == null) ? "null" : message;

        return String.format(format, nullSafeMessage);
    }
}
