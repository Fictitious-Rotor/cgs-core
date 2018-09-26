package net.core.cgs.metadata;

import net.core.cgs.Core;
import net.core.cgs.data.JavaCheckedHissyFitException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MetadataPretender<ComplexKey, ComplexValue, SimpleKey, SimpleValue> {
    private Map<SimpleKey, SimpleValue> metadataSet;
    protected Core plugin;

    public MetadataPretender(Core instance) {
        plugin = instance;
        assembleFromStdFile();
    }

    public void addMetadata(ComplexKey givenKey, ComplexValue givenValue) {
        metadataSet.put(reduceComplexKeyToSimpleKey(givenKey), reduceComplexValueToSimpleValue(givenValue));
        storeToFile();
    }

    public SimpleValue getMetadata(ComplexKey givenKey) {
        return metadataSet.get(reduceComplexKeyToSimpleKey(givenKey));
    }

    public void removeMetadata(ComplexKey toRemove) {
        metadataSet.remove(reduceComplexKeyToSimpleKey(toRemove));
        storeToFile();
    }

    public boolean metadataContains(ComplexKey queryKey) {
        return metadataSet.containsKey(reduceComplexKeyToSimpleKey(queryKey));
    }

    private Map<SimpleKey, SimpleValue> convertStringMapIntoSimpleKVMap(Map<String, String> givenMap) {
        return givenMap.entrySet().stream().collect(
                Collectors.toMap(
                        entry -> convertStringToSimpleKey(((Map.Entry)entry).getKey().toString()),
                        entry -> convertStringToSimpleValue(((Map.Entry)entry).getValue().toString())
                )
        );
    }

    private Map<String, String> convertSimpleKVMapIntoStringMap(Map<SimpleKey, SimpleValue> givenMap) {
        return givenMap.entrySet().stream().collect(
                Collectors.toMap(
                        entry -> convertSimpleKeyToString(entry.getKey()),
                        entry -> convertSimpleValueToString(entry.getValue())
                )
        );
    }

    private void assembleFromStdFile() {
        try {
            Map<String, String> caughtSet = plugin.dataStorageUtil.getStdFile();

            metadataSet = (caughtSet == null) ? new HashMap<>() : convertStringMapIntoSimpleKVMap(caughtSet);
        } catch (JavaCheckedHissyFitException ex) {
            plugin.getLogger().info("Exception at assembleFromStdFile: " + ex.getMessage());
            // File is probably missing. No action required
        }
    }

    private void storeToFile() {
        plugin.dataStorageUtil.storeCurrentSession(convertSimpleKVMapIntoStringMap(metadataSet));
    }

    protected abstract SimpleKey reduceComplexKeyToSimpleKey(ComplexKey givenKey);

    protected abstract SimpleValue reduceComplexValueToSimpleValue(ComplexValue givenValue);

    protected abstract ComplexValue expandSimpleValueToComplexValue(SimpleValue givenValue);

    protected abstract String convertSimpleKeyToString(SimpleKey givenKey);

    protected abstract String convertSimpleValueToString(SimpleValue givenValue);

    protected abstract SimpleKey convertStringToSimpleKey(String givenString);

    protected abstract SimpleValue convertStringToSimpleValue(String givenString);
}
