package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ChestMetadataHandler {
    private Core plugin;
    private MetadataPretender metadata;
    private static final Material VALID_MATERIAL = Material.CHEST;

    public ChestMetadataHandler(Core instance) {
        plugin = instance;
        metadata = plugin.MARKED_CHESTS;
    }

    public boolean givenBlockIsChest(Block givenBlock) {
        return givenBlock.getType() == VALID_MATERIAL;
    }

    public boolean givenBlockIsInfinite(Block givenBlock) {
        return metadata.metadataContains(givenBlock);
    }

    public boolean givenBlockIsInfiniteChest(Block givenBlock) {
        return givenBlockIsChest(givenBlock) && givenBlockIsInfinite(givenBlock);
    }

    public boolean makeBlockInfiniteChest(Block givenChest, Material infiniteItem) {
        if (!givenBlockIsChest(givenChest)) return false;

        // Ensure chest isn't already infinite!
        makeBlockFiniteChest(givenChest);

        metadata.addMetadata(givenChest, infiniteItem);

        return true;
    }

    public boolean makeBlockFiniteChest(Block givenBlock) {
        if (!givenBlockIsChest(givenBlock) || !metadata.metadataContains(givenBlock)) return false;

        metadata.removeMetadata(givenBlock);

        return true;
    }
}
