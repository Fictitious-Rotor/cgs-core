package org.core.cgs.generic.utilities;

public final class ExceptionUtils {
    public static String getNullSafeMessage(final String format, final Object message) {
        final String nullSafeMessage = (message == null) ? "null" : message.toString();

        return String.format(format, nullSafeMessage);
    }
}
