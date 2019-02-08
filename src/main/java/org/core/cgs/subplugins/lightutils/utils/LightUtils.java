package org.core.cgs.subplugins.lightutils.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;
import ru.beykerykt.lightapi.LightAPI;

import java.util.Collections;

import static org.core.cgs.generic.utilities.ParsingUtils.getIntFromFirstArg;

public class LightUtils {
    private static final int DEFAULT_LIGHT_LEVEL = 15;

    public static boolean makeLight(final Player player,
                                    final Location location,
                                    final int level,
                                    final LightLocationStoredMH metadataHandler) {
        LightAPI.createLight(location, level, true);
        LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        return metadataHandler.addLight(location, level);
    }

    public static boolean killLight(final Player player, final Location location, final LightLocationStoredMH metadataHandler) {
        boolean foundAndKilledLight = metadataHandler.removeLight(location);

        if (foundAndKilledLight) {
            LightAPI.deleteLight(location, true);
            LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        }

        return foundAndKilledLight;
    }

    public static boolean convertLightIntoBlock(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
        boolean didKill = killLight(player, location, metadataHandler);

        if (didKill) {
            Block block = player.getWorld().getBlockAt(location);
            block.setType(blockType);
            block.setBlockData(blockType.createBlockData());
        }

        return didKill;
    }

    public static boolean convertBlockIntoLight(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
        Block blockAtPos = player.getWorld().getBlockAt(location);
        boolean didMatch = blockAtPos.getType() == blockType;
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
}
