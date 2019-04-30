package org.core.splitcgs.lightutils.metadata.stored;

import dagger.Component;
import org.bukkit.Location;
import org.core.cgs.generic.interfaces.StoredMetadataHandler;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationMetadata;
import org.core.splitcgs.lightutils.ProviderModule;
import org.core.splitcgs.lightutils.metadata.LightLocationMetadata;
import org.core.splitcgs.lightutils.metadata.MetadataHandler;

import javax.inject.Singleton;

@Component(modules = ProviderModule.class)
@Singleton
public class LightLocationStoredMH implements MetadataHandler {
    private final LightLocationMetadata metadata;

    public LightLocationStoredMH() {
        metadata = new LightLocationMetadata();
    }

    @Override
    public void registerPretenders() {
        metadata.assembleFromPreviousSession();
    }

    public boolean addLight(Location lightLocation, int lightLevel) {
        return metadata.addMetadata(lightLocation, lightLevel);
    }

    public boolean removeLight(Location lightLocation) {
        return metadata.removeMetadata(lightLocation);
    }

    public boolean lightIsAtLocation(Location locationToCheck) {
        return metadata.metadataContainsKey(locationToCheck);
    }
}
