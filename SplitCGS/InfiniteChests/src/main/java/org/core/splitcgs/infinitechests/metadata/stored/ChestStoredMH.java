package org.core.splitcgs.infinitechests.metadata.stored;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.core.cgs.generic.abstracts.MetadataPretender;
import org.core.cgs.generic.interfaces.StoredMetadataHandler;
import org.core.cgs.subplugins.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.cgs.subplugins.infinitechests.metadata.InfiniteChestsMetadata;
import org.core.splitcgs.infinitechests.metadata.HorrificBytesMaterialBundle;

import java.util.Map;

public class ChestStoredMH {
    private static final Material VALID_MATERIAL = Material.CHEST;
    private final Map<Block, HorrificBytesMaterialBundle> metadata;

    public ChestStoredMH() {
        metadata = new InfiniteChestsMetadata(subPluginName);
    }

    @Override
    public void registerPretenders() {
        metadata.assembleFromPreviousSession();
    }

    public boolean givenBlockIsChest(Block givenBlock) {
        return givenBlock.getType() == VALID_MATERIAL;
    }

    public boolean givenBlockIsInfinite(Block givenBlock) {
        return metadata.metadataContainsKey(givenBlock);
    }

    public boolean givenBlockIsInfiniteChest(Block givenBlock) {
        return givenBlockIsChest(givenBlock) && givenBlockIsInfinite(givenBlock);
    }

    public boolean makeBlockInfiniteChest(Block givenChest, HorrificBytesMaterialBundle infiniteItem) {
        if (!givenBlockIsChest(givenChest)) return false;

        // Ensure chest isn't already infinite!
        makeBlockFiniteChest(givenChest);

        metadata.addMetadata(givenChest, infiniteItem);

        return true;
    }

    public boolean makeBlockFiniteChest(Block givenBlock) {
        if (!givenBlockIsChest(givenBlock) || !metadata.metadataContainsKey(givenBlock)) return false;

        metadata.removeMetadata(givenBlock);

        return true;
    }

    public HorrificBytesMaterialBundle getInfiniteItem(Block infiniteChestBlock) {
        return metadata.getMetadata(infiniteChestBlock);
    }
}
