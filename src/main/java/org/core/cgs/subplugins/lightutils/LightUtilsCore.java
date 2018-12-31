package org.core.cgs.subplugins.lightutils;

import com.google.common.collect.ImmutableSet;
import org.core.cgs.generic.abstracts.SubPlugin;
import org.core.cgs.generic.interfaces.*;
import org.core.cgs.generic.utilities.PlayerInterface;
import org.core.cgs.subplugins.lightutils.listeners.ShovelListener;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;

public class LightUtilsCore extends SubPlugin {
    public static final String PLUGIN_NAME = "LightUtils";
    public static final PlayerInterface PLI = new PlayerInterface(PLUGIN_NAME);

    public enum LUStoredMH implements SubPluginStoredMH {
        LIGHT_LOCATION(new LightLocationStoredMH(PLUGIN_NAME));

        private final StoredMetadataHandler handler;

        LUStoredMH(StoredMetadataHandler handler) { this.handler = handler; }
        @Override public StoredMetadataHandler getHandler() { return handler; }
        static <T extends StoredMetadataHandler> T getHandlerAs(Class<T> desiredType) {
            return SubPluginMH.getHandlerAs(desiredType, values());
        }
    }

    public enum LUSessionMH implements SubPluginSessionMH {
        CUBOID_SELECTION(new CuboidSelectionSessionMH());

        private final SessionMetadataHandler handler;

        LUSessionMH(SessionMetadataHandler handler) { this.handler = handler; }
        @Override public SessionMetadataHandler getHandler() { return handler; }
        static <T extends SessionMetadataHandler> T getHandlerAs(Class<T> desiredType) {
            return SubPluginMH.getHandlerAs(desiredType, values());
        }
    }

    public LightUtilsCore() {
        super(PLUGIN_NAME,
              new LUCommandExecutor(LUStoredMH.getHandlerAs(LightLocationStoredMH.class),
                                    LUSessionMH.getHandlerAs(CuboidSelectionSessionMH.class)),
              ImmutableSet.of(
                      new ShovelListener(LUSessionMH.getHandlerAs(CuboidSelectionSessionMH.class))
              )
        );
    }
}
