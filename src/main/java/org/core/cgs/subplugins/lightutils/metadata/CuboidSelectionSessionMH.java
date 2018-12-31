package org.core.cgs.subplugins.lightutils.metadata;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.core.cgs.generic.interfaces.SessionMetadataHandler;

import java.util.HashMap;
import java.util.Map;

public class CuboidSelectionSessionMH implements SessionMetadataHandler {
    public static final class LocationPair {
        public Location startingPos;
        public Location finishingPos;
    }

    private final Map<Player, LocationPair> metadata = new HashMap<>();

    public boolean setStartingPos(final Player player, final Location startingPos) {
        final boolean containsKey = metadata.containsKey(player);
        if (!containsKey) { metadata.put(player, new LocationPair()); }

        metadata.get(player).startingPos = startingPos;
        return containsKey;
    }

    public boolean setFinishingPos(final Player player, final Location finishingPos) {
        final boolean containsKey = metadata.containsKey(player);

        if (!containsKey) { metadata.put(player, new LocationPair()); }
        metadata.get(player).finishingPos = finishingPos;

        return containsKey;
    }

    public LocationPair getLocationPair(final Player player) {
        return metadata.get(player);
    }
}
