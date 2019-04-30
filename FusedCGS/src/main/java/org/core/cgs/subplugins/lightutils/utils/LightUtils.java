package org.core.cgs.subplugins.lightutils.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.subplugins.lightutils.metadata.stored.LightLocationStoredMH;
import ru.beykerykt.lightapi.LightAPI;

import java.util.Collections;

import static org.core.cgs.generic.utilities.ParsingUtils.getIntFromFirstArg;

public class LightUtils {
    private static final int DEFAULT_LIGHT_LEVEL = 15;

    public static boolean makeLight(final Player player,
                                    final Location location,
                                    final int level,
                                    final LightLocationStoredMH metadataHandler) {
        return LightAPI.createLight(location, level, true)
            && LightAPI.updateChunks(player.getLocation(), Collections.singleton(player))
            && metadataHandler.addLight(location, level);
    }

    public static boolean killLight(final Player player, final Location location, final LightLocationStoredMH metadataHandler) {
        final boolean foundAndKilledLight = metadataHandler.removeLight(location);
        return foundAndKilledLight
            && LightAPI.deleteLight(location, true)
            && LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
    }

    // This overloaded method exists to make the killLight function compliant with the BlockOperation functional interface
    public static boolean killLight(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
        return killLight(player, location, metadataHandler);
    }

    public static boolean convertLightIntoBlock(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
        final boolean didKill = killLight(player, location, metadataHandler);

        if (didKill) {
            final Block block = player.getWorld().getBlockAt(location);
            block.setType(blockType);
            block.setBlockData(blockType.createBlockData());
        }

        return didKill;
    }

    public static boolean convertBlockIntoLight(final Player player,
                                                final Location location,
                                                final Material blockType,
                                                final LightLocationStoredMH metadataHandler) {
        final Block blockAtPos = player.getWorld().getBlockAt(location);
        final boolean didMatch = blockAtPos.getType() == blockType;
        boolean didMake = false;

        if (didMatch) {
            didMake = makeLight(player, location, 15, metadataHandler);

            if (didMake) {
                blockAtPos.setType(Material.AIR);
            }
        }

        return didMatch && didMake;
    }

    public static int getLightLevelFromArgs(final String[] arguments) {
        return getIntFromFirstArg(arguments, DEFAULT_LIGHT_LEVEL);
    }

    public static boolean identifyBlock(final Player player,
                                        final Location location,
                                        final Material blockType,
                                        final LightLocationStoredMH metadataHandler) {
        final boolean isLit = metadataHandler.lightIsAtLocation(location);

        if (isLit) {
            /* This breaks the cohesion a bit as it joins player interaction with backend functionality */
            player.sendMessage(String.format("%s%s Light found at (%s,%s,%s)", ChatColor.ITALIC, ChatColor.BLUE, location.getX(), location.getY(), location.getZ()));
        }

        return isLit;
    }
}
