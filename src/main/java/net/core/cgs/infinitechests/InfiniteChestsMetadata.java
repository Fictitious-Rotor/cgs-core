package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;
import java.util.stream.Collectors;

public class InfiniteChestsMetadata extends MetadataPretender<Block, Material, String, Material> {
    public InfiniteChestsMetadata(Core instance) {
        super(instance);
    }

    @Override
    protected String reduceComplexKeyToSimpleKey(Block givenBlock) {
        Map<String, String> outMap = givenBlock.getLocation().serialize().entrySet().stream().collect(
                Collectors.toMap(
                        entry -> ((Map.Entry)entry).getKey().toString(),
                        entry -> ((Map.Entry)entry).getKey().toString()
                )
        );

        return plugin.dataStorageUtil.convertMapIntoString(outMap);
    }

    @Override
    protected Material reduceComplexValueToSimpleValue(Material givenMaterial) {
        return givenMaterial;
    }

    @Override
    protected Material expandSimpleValueToComplexValue(Material givenValue) {
        return givenValue;
    }

    @Override
    protected String convertSimpleKeyToString(String givenKey) {
        return givenKey;
    }

    @Override
    protected String convertSimpleValueToString(Material givenValue) {
        return givenValue.name();
    }

    @Override
    protected String convertStringToSimpleKey(String givenString) {
        return givenString;
    }

    @Override
    protected Material convertStringToSimpleValue(String givenString) {
        return Material.getMaterial(givenString);
    }
}
