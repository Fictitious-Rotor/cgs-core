package org.core.splitcgs.infinitechests.metadata;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.core.cgs.generic.abstracts.MetadataPretender;

public class InfiniteChestsMetadata extends MetadataPretender<Block, HorrificBytesMaterialBundle> {
    public InfiniteChestsMetadata(final String subPluginName) {
        super(subPluginName);
    }

    @Override
    protected String convertMetadataKeyToString(final Block givenBlock) {
        return serialiseSerialisable(givenBlock.getLocation());
    }

    @Override
    protected String convertMetadataValueToString(final HorrificBytesMaterialBundle givenValue) {
        String itemTypeAsString = givenValue.getItemType().toString();
        String horrificBytesAsString = Byte.toString(givenValue.getItemMetadata());

        return String.format("%s %s", itemTypeAsString, horrificBytesAsString);
    }

    @Override
    protected HorrificBytesMaterialBundle convertStringToMetadataValue(final String givenString) {
        String[] splitString = givenString.split(" ");

        Material foundMaterial = Material.getMaterial(splitString[0]);
        byte foundMetadata = Byte.valueOf(splitString[1]);

        return new HorrificBytesMaterialBundle(foundMaterial, foundMetadata);
    }
}
