package org.core.cgs.subplugins.lightutils.metadata.session;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.core.cgs.generic.interfaces.SessionMetadataHandler;

import java.util.HashMap;
import java.util.Map;

import static java.util.logging.Level.INFO;
import static org.core.cgs.Core.LOGGER;

public class CuboidSelectionSessionMH implements SessionMetadataHandler {
    public static final class LocationPair {
        public Location startingPos;
        public Location finishingPos;

        public boolean isCompleteLP() {
            return (startingPos != null) && (finishingPos != null);
        }
    }

    private final Map<String, LocationPair> metadata = new HashMap<>();

    public void setStartingPos(final Player player, final Location startingPos) {
        final String playerName = player.getName();
        if (!(metadata.containsKey(playerName))) { metadata.put(playerName, new LocationPair()); }

        metadata.get(playerName).startingPos = startingPos;
    }

    public void setFinishingPos(final Player player, final Location finishingPos) {
        final String playerName = player.getName();
        if (!(metadata.containsKey(playerName))) { metadata.put(playerName, new LocationPair()); }

        metadata.get(playerName).finishingPos = finishingPos;
    }

    public LocationPair getLocationPair(final Player player) {
        LOGGER.log(INFO, "metadata = {0}", metadata);
        return metadata.get(player.getName());
    }
}
