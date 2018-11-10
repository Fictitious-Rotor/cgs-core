package net.core.cgs.subplugins.lightutils;

import com.google.common.collect.ImmutableSet;
import net.core.cgs.Core;
import net.core.cgs.subplugins.SubPlugin;

import static net.core.cgs.subplugins.lightutils.LightUtilsCommandExecutor.*;

public class LightUtilsCore extends SubPlugin {
    public LightUtilsCore(Core plugin) {
        super(plugin);
        this.commandExecutor = new LightUtilsCommandExecutor(plugin);
        this.listeners = ImmutableSet.of(); // No listeners
        this.subPluginName = "LightUtils";
        this.metadataHandler = null; // TODO implement metadata for this class!
        this.commands = ImmutableSet.of(CREATE_LIGHT_COMMAND,
                                        REMOVE_LIGHT_COMMAND,
                                        MAKE_BLOCK_INTO_LIGHT_COMMAND,
                                        BLOCKIFY_LIGHT_IN_RADIUS
        );
    }
}
