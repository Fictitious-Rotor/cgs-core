package org.core.splitcgs.infinitechests.metadata;

import org.bukkit.Material;

public class HorrificBytesMaterialBundle {
    private Material itemType;
    private byte itemMetadata;

    public HorrificBytesMaterialBundle(Material itemType, byte itemMetadata) {
        this.itemType = itemType;
        this.itemMetadata = itemMetadata;
    }

    public Material getItemType() {
        return itemType;
    }

    public byte getItemMetadata() {
        return itemMetadata;
    }
}
