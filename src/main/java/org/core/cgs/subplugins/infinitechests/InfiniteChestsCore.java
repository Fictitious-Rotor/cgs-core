package org.core.cgs.subplugins.infinitechests;

import com.google.common.collect.ImmutableSet;
import org.core.cgs.generic.abstracts.SubPlugin;
import org.core.cgs.generic.interfaces.StoredMetadataHandler;
import org.core.cgs.generic.interfaces.SubPluginMH;
import org.core.cgs.generic.interfaces.SubPluginStoredMH;
import org.core.cgs.generic.utilities.PlayerInterface;
import org.core.cgs.subplugins.infinitechests.listeners.ChestListener;
import org.core.cgs.subplugins.infinitechests.metadata.ChestStoredMH;

public class InfiniteChestsCore extends SubPlugin {
    public static final String PLUGIN_NAME = "InfiniteChests";
    public static final PlayerInterface PLI = new PlayerInterface(PLUGIN_NAME);

    public enum ICStoredMH implements SubPluginStoredMH {
        CHEST(new ChestStoredMH(PLUGIN_NAME));

        private final StoredMetadataHandler handler;

        ICStoredMH(StoredMetadataHandler handler) { this.handler = handler; }
        @Override public StoredMetadataHandler getHandler() { return handler; }
        public static <T extends StoredMetadataHandler> T getHandlerAs(Class<T> desiredType) {
            return SubPluginMH.getHandlerAs(desiredType, values());
        }
    }

    public InfiniteChestsCore() {
        super(PLUGIN_NAME,
              new ICCommandExecutor(ICStoredMH.getHandlerAs(ChestStoredMH.class)),
              ImmutableSet.of(
                      new ChestListener(ICStoredMH.getHandlerAs(ChestStoredMH.class))
              )
        );
    }
}
