package org.core.cgs.generic.classes;

import org.core.cgs.generic.exceptions.MissingMetadataHandlerException;
import org.core.cgs.generic.interfaces.MetadataHandler;

import java.util.Arrays;
import java.util.Collection;

public final class MetadataBundle {
    private final MetadataHandler[] allHandlers;

    public MetadataBundle(final MetadataHandler... allHandlers) {
        this.allHandlers = allHandlers;
    }

    public MetadataBundle(final Collection<? extends MetadataHandler> allHandlers) {
        this.allHandlers = allHandlers.toArray(new MetadataHandler[allHandlers.size()]);
    }

    public <T extends MetadataHandler> T getHandler(final Class<T> handlerType) {
        return (T)Arrays.stream(allHandlers)
                        .filter(cb -> cb.getClass().equals(handlerType))
                        .findFirst()
                        .orElseThrow(MissingMetadataHandlerException::new);
    }
}
