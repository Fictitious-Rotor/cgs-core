package org.core.cgs.generic.abstracts;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.core.cgs.Core;
import org.core.cgs.data.JavaCheckedHissyFitException;

import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class MetadataPretender<MetadataKey, MetadataValue> {
    private Map<String, String> metadataSet;
    private final String subPluginName;

    public MetadataPretender(String subPluginName) {
        this.subPluginName = subPluginName;
        assembleFromPreviousSession();
    }

    public boolean addMetadata(MetadataKey givenKey, MetadataValue givenValue) {
        String keyAsString = convertMetadataKeyToString(givenKey);
        String valueAsString = convertMetadataValueToString(givenValue);

        boolean previouslyExisted = metadataSet.put(keyAsString, valueAsString) == null;
        storeToCurrentSession();

        return previouslyExisted;
    }

    public MetadataValue getMetadata(MetadataKey givenKey) {
        String metadataKeyAsString = convertMetadataKeyToString(givenKey);
        String metadataValueAsString = metadataSet.get(metadataKeyAsString);

        return convertStringToMetadataValue(metadataValueAsString);
    }

    public boolean removeMetadata(MetadataKey toRemove) {
        boolean previouslyExisted = metadataSet.remove(convertMetadataKeyToString(toRemove)) != null;

        if (previouslyExisted) {
            storeToCurrentSession();
        }

        return previouslyExisted;
    }

    public boolean metadataContainsKey(MetadataKey queryKey) {
        return metadataSet.containsKey(convertMetadataKeyToString(queryKey));
    }

    private void assembleFromPreviousSession() {
        try {
            metadataSet = Core.DATA_STORAGE_UTIL.getPreviousSession(subPluginName);
        } catch (JavaCheckedHissyFitException ex) {
            Core.LOGGER.log(Level.INFO, "Exception at assembleFromPreviousSession", ex);
            // File is probably missing. No action required
        }
    }

    private void storeToCurrentSession() {
        Core.DATA_STORAGE_UTIL.storeCurrentSession(metadataSet, subPluginName);
    }

    protected String serialiseSerialisable(ConfigurationSerializable serialisable) {
        Map<String, String> outMap = serialisable.serialize().entrySet().stream().collect(
                Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().toString()
                )
        );

        return Core.DATA_STORAGE_UTIL.convertMapIntoString(outMap);
    }

    protected abstract String convertMetadataKeyToString(MetadataKey givenKey);

    protected abstract String convertMetadataValueToString(MetadataValue givenValue);

    protected abstract MetadataValue convertStringToMetadataValue(String givenString);
}
