package net.core.cgs.subplugins;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;

public abstract class AMetadataHandler {
    protected Core plugin;
    protected SubPlugin subPlugin;
    protected MetadataPretender metadata;

    public AMetadataHandler(Core plugin, SubPlugin subPlugin, MetadataPretender metadata) {
        this.plugin = plugin;
        this.subPlugin = subPlugin;
        this.metadata = metadata;
    }
}
