package org.core.splitcgs.lightutils.metadata.session;

import dagger.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.ProviderModule;
import org.core.splitcgs.lightutils.metadata.MetadataHandler;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static java.util.logging.Level.INFO;
import static org.core.splitcgs.lightutils.LightUtilsCore.LOGGER;

@Component(modules = ProviderModule.class)
@Singleton
public class CuboidSelectionSessionMH implements MetadataHandler {
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
