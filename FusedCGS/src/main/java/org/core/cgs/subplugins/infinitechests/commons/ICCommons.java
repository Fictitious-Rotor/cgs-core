package org.core.cgs.subplugins.infinitechests.commons;

import org.bukkit.Location;

public class ICCommons {
    protected static String formatChestLocation(final Location chestLocation) {
        return String.format("%s:(%s,%s,%s)",
                             chestLocation.getWorld().getName(),
                             chestLocation.getBlockX(),
                             chestLocation.getBlockY(),
                             chestLocation.getBlockZ()
        );
    }
}
