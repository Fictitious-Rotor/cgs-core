package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.Map;
import java.util.stream.Collectors;

public class InfiniteChestsMetadata extends MetadataPretender<Block, BlockData, String, BlockData> {
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
    protected BlockData reduceComplexValueToSimpleValue(BlockData givenBlockData) {
        return givenBlockData;
    }

    @Override
    protected BlockData expandSimpleValueToComplexValue(BlockData givenValue) {
        return givenValue;
    }

    @Override
    protected String convertSimpleKeyToString(String givenKey) {
        return givenKey;
    }

    @Override
    protected String convertSimpleValueToString(BlockData givenValue) {
        plugin.getLogger().info("LOOK AT ME! I AM THE TOSTRING OF THE BLOCKDATA! " + givenValue.toString());
        plugin.getLogger().info("LOOK AT ME! I AM GETASSTRING OF THE BLOCKDATA! " + givenValue.getAsString());

        return givenValue.getAsString();
    }

    @Override
    protected String convertStringToSimpleKey(String givenString) {
        return givenString;
    }

    @Override
    protected BlockData convertStringToSimpleValue(String givenString) {
        return plugin.getServer().createBlockData(givenString);
    }
}
