package org.core.splitcgs.lightutils.metadata;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.core.cgs.Core;
import org.core.cgs.data.JavaCheckedHissyFitException;
import org.core.splitcgs.lightutils.LightUtilsCore;
import org.core.splitcgs.lightutils.utils.filesystem.DataStorageUtil;
import org.core.splitcgs.lightutils.utils.filesystem.JavaCheckedHissyFitException;

import javax.inject.Inject;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class MetadataPretender<MetadataKey, MetadataValue> {
    @Inject private DataStorageUtil dataStorageUtil;
    private Map<String, String> metadataSet;
    private final String subPluginName;

    public MetadataPretender(String subPluginName) {
        this.subPluginName = subPluginName;
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

    public void assembleFromPreviousSession() {
        try {
            metadataSet = dataStorageUtil.getPreviousSession(subPluginName);
        } catch (JavaCheckedHissyFitException ex) {
            LightUtilsCore.LOGGER.log(Level.WARNING, "Exception at assembleFromPreviousSession", ex);
            // File is probably missing. No action required
        }
    }

    private void storeToCurrentSession() {
        dataStorageUtil.storeCurrentSession(metadataSet, subPluginName);
    }

    protected String serialiseSerialisable(ConfigurationSerializable serialisable) {
        Map<String, String> outMap = serialisable.serialize().entrySet().stream().collect(
                Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().toString()
                )
        );

        return dataStorageUtil.convertMapIntoString(outMap);
    }

    protected abstract String convertMetadataKeyToString(MetadataKey givenKey);

    protected abstract String convertMetadataValueToString(MetadataValue givenValue);

    protected abstract MetadataValue convertStringToMetadataValue(String givenString);
}
