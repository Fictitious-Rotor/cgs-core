package org.core.splitcgs.infinitechests.commons;

import org.bukkit.Location;

public abstract class ICCommons extends PlayerNeededCommons {
    protected static String formatChestLocation(final Location chestLocation) {
        return String.format("%s:(%s,%s,%s)",
                             chestLocation.getWorld().getName(),
                             chestLocation.getBlockX(),
                             chestLocation.getBlockY(),
                             chestLocation.getBlockZ()
        );
    }
}
