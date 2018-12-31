package org.core.cgs.generic.interfaces;

import java.util.Arrays;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public interface SubPluginMH {
    class UnrecognisedClassException extends RuntimeException {
        private static String getNullSafeClass(final String format, final Class givenClass) {
            final String nullSafeMessage = (givenClass == null) ? "null" : givenClass.getName();

            return String.format(format, nullSafeMessage);
        }

        UnrecognisedClassException(Class givenClass) {
            super(getNullSafeClass("Unable to match the given class '%s' to the contents of the enum!", givenClass));
        }
    }

    MetadataHandler getHandler();

    static <T extends MetadataHandler> T getHandlerAs(Class<T> desiredType, SubPluginMH[] values) {
        checkNotNull(desiredType);

        Optional<SubPluginMH> foundEnum = Arrays.stream(values).filter(m -> desiredType.equals(m.getHandler().getClass()))
                                                      .findFirst();

        if (foundEnum.isPresent()) {
            return desiredType.cast(foundEnum.get().getHandler());
        } else {
            throw new SubPluginMH.UnrecognisedClassException(desiredType);
        }
    }
}
