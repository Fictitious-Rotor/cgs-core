package net.core.cgs.subplugins.lightutils;

import net.core.cgs.Core;
import net.core.cgs.metadata.MetadataPretender;
import net.core.cgs.subplugins.MetadataHandler;
import net.core.cgs.subplugins.SubPlugin;

public class LightLocationMetadataHandler extends MetadataHandler {
    public LightLocationMetadataHandler(Core plugin, SubPlugin subPlugin) {
        super(plugin, subPlugin, new LightLocationMetadata());
    }


}
