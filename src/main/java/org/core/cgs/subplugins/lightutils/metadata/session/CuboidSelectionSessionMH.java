package org.core.cgs.subplugins.lightutils.metadata.session;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.core.cgs.generic.interfaces.SessionMetadataHandler;

import java.util.HashMap;
import java.util.Map;

public class CuboidSelectionSessionMH implements SessionMetadataHandler {
    public static final class LocationPair {
        public Location startingPos;
        public Location finishingPos;

        public boolean isCompleteLP() {
            return (startingPos != null) && (finishingPos != null);
        }
    }

    private final Map<Player, LocationPair> metadata = new HashMap<>();

    public void setStartingPos(final Player player, final Location startingPos) {
        if (!(metadata.containsKey(player))) { metadata.put(player, new LocationPair()); }

        metadata.get(player).startingPos = startingPos;
    }

    public void setFinishingPos(final Player player, final Location finishingPos) {
        if (!(metadata.containsKey(player))) { metadata.put(player, new LocationPair()); }

        metadata.get(player).finishingPos = finishingPos;
    }

    public LocationPair getLocationPair(final Player player) {
        return metadata.get(player);
    }
}
