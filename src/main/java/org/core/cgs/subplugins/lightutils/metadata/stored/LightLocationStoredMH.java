package org.core.cgs.subplugins.lightutils.metadata.stored;

import org.bukkit.Location;
import org.core.cgs.generic.interfaces.StoredMetadataHandler;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationMetadata;

public class LightLocationStoredMH implements StoredMetadataHandler {
    private final LightLocationMetadata metadata;

    public LightLocationStoredMH(String subPluginName) {
        metadata = new LightLocationMetadata(subPluginName);
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
