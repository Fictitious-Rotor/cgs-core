package org.core.cgs.subplugins.lightutils.metadata;

import org.bukkit.Location;
import org.core.cgs.generic.interfaces.StoredMetadataHandler;

public class LightLocationStoredMH implements StoredMetadataHandler {
    private LightLocationMetadata metadata;

    public LightLocationStoredMH(String subPluginName) {
        metadata = new LightLocationMetadata(subPluginName);
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
