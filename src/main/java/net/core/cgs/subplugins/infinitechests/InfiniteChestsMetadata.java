package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;
import java.util.stream.Collectors;

public class InfiniteChestsMetadata extends MetadataPretender<Block, HorrificBytesMaterialBundle, String, HorrificBytesMaterialBundle> {
    public InfiniteChestsMetadata(Core instance) {
        super(instance);
    }

    @Override
    protected String reduceComplexKeyToSimpleKey(Block givenBlock) {
        Map<String, String> outMap = givenBlock.getLocation().serialize().entrySet().stream().collect(
                Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().toString()
                )
        );

        return plugin.dataStorageUtil.convertMapIntoString(outMap);
    }

    @Override
    protected HorrificBytesMaterialBundle reduceComplexValueToSimpleValue(HorrificBytesMaterialBundle givenMaterial) {
        return givenMaterial;
    }

    @Override
    protected HorrificBytesMaterialBundle expandSimpleValueToComplexValue(HorrificBytesMaterialBundle givenValue) {
        return givenValue;
    }

    @Override
    protected String convertSimpleKeyToString(String givenKey) {
        return givenKey;
    }

    @Override
    protected String convertSimpleValueToString(HorrificBytesMaterialBundle givenValue) {
        return String.format("%s %s", givenValue.getItemType().toString(), Byte.toString(givenValue.getItemMetadata()));
    }

    @Override
    protected String convertStringToSimpleKey(String givenString) {
        return givenString;
    }

    @Override
    protected HorrificBytesMaterialBundle convertStringToSimpleValue(String givenString) {
        String[] splitString = givenString.split(" ");

        Material foundMaterial = Material.getMaterial(splitString[0]);
        byte foundMetadata = Byte.valueOf(splitString[1]);

        return new HorrificBytesMaterialBundle(foundMaterial, foundMetadata);
    }
}
