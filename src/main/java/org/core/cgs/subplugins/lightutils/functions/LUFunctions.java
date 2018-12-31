package org.core.cgs.subplugins.lightutils.functions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;
import ru.beykerykt.lightapi.LightAPI;

import java.util.Collections;
import java.util.logging.Level;

import static org.core.cgs.Core.LOGGER;
import static org.core.cgs.subplugins.lightutils.LightUtilsCore.PLI;
import static org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH.LocationPair;

public class LUFunctions {
    private static final int DEFAULT_LIGHT_LEVEL = 15;
    private static final int DEFAULT_RADIUS = 5;

    private static void reportCreation(final Player runningPlayer, final boolean succeeded) {
        PLI.sendToPlayer(runningPlayer, succeeded ? "Created light source at block" : "Unable to create light source at block");
    }

    private static void reportDestruction(final Player runningPlayer, final boolean succeeded) {
        PLI.sendToPlayer(runningPlayer, succeeded ? "Removed light source block" : "Unable to remove light source block");
    }

    private static void reportMassLightification(final Player runningPlayer, final int noOfConvertedBlocks) {
        PLI.sendToPlayer(runningPlayer, String.format("Managed to convert %s blocks into light sources", noOfConvertedBlocks));
    }

    private static void reportMassBlockification(final Player runningPlayer, final int noOfConvertedBlocks) {
        PLI.sendToPlayer(runningPlayer, String.format("Managed to convert %s light sources into blocks", noOfConvertedBlocks));
    }

    // region Commands

    public static boolean commandCreateLight(final Player runningPlayer, final String[] arguments, final LightLocationStoredMH metadataHandler) {
        int level = getIntFromFirstArg(arguments, DEFAULT_LIGHT_LEVEL);

        Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        boolean succeeded = makeLight(runningPlayer, targetedBlock.getLocation(), level, metadataHandler);

        reportCreation(runningPlayer, succeeded);
        return succeeded;
    }

    public static boolean commandRemoveLight(final Player runningPlayer, final LightLocationStoredMH metadataHandler) {
        Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        boolean succeeded = killLight(runningPlayer, targetedBlock.getLocation(), metadataHandler);

        reportDestruction(runningPlayer, succeeded);
        return succeeded;
    }

    public static boolean convertBlockIntoLight(Player runningPlayer, final LightLocationStoredMH metadataHandler) {
        Location location = runningPlayer.getTargetBlock(null, 10)
                                         .getLocation();
        runningPlayer.getWorld()
                     .getBlockAt(location)
                     .setType(Material.AIR);
        boolean succeeded = makeLight(runningPlayer, location, 15, metadataHandler);

        reportCreation(runningPlayer, succeeded);
        return succeeded;
    }

    public static boolean blockifyLightInRadius(Player runningPlayer, String[] args, final LightLocationStoredMH metadataHandler) {
        int noOfConvertedBlocks = performRadialBlockOperation(runningPlayer, args, LUFunctions::convertLightIntoBlock, metadataHandler);

        reportMassBlockification(runningPlayer, noOfConvertedBlocks);
        return noOfConvertedBlocks > 0;
    }

    public static boolean lightifyBlockInRadius(Player runningPlayer, String[] args, final LightLocationStoredMH metadataHandler) {
        int noOfConvertedBlocks = performRadialBlockOperation(runningPlayer, args, LUFunctions::convertBlockIntoLight, metadataHandler);

        reportMassLightification(runningPlayer, noOfConvertedBlocks);
        return noOfConvertedBlocks > 0;
    }

    public static boolean blockifyLightInRange(final Player runningPlayer, final CuboidSelectionSessionMH cuboidSelectionSessionMH, final LightLocationStoredMH lightLocationStoredMH) {
        int noOfConvertedBlocks = performRangeBlockOperation(runningPlayer, LUFunctions::convertLightIntoBlock, cuboidSelectionSessionMH, lightLocationStoredMH);

        reportMassLightification(runningPlayer, noOfConvertedBlocks);
        return noOfConvertedBlocks > 0;
    }

    public static boolean lightifyBlocksInRange(final Player runningPlayer, final CuboidSelectionSessionMH cuboidSelectionSessionMH, final LightLocationStoredMH lightLocationStoredMH) {
        int noOfConvertedBlocks = performRangeBlockOperation(runningPlayer, LUFunctions::convertBlockIntoLight, cuboidSelectionSessionMH, lightLocationStoredMH);

        reportMassLightification(runningPlayer, noOfConvertedBlocks);
        return noOfConvertedBlocks > 0;
    }

    // endregion

    private static int getPolarity(final int startPos, final int finishPos) {
        return ((startPos - finishPos) >= 0) ? 1 : -1;
    }

    private static int getIntFromFirstArg(final String[] args, final int defaultValue) {
        if((args != null) && (args.length > 0)) {
            try {
                return Integer.parseInt(args[0]);
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Unable to parse first arg of {0}", args[0]);
            }
        }

        return defaultValue;
    }

    private static boolean makeLight(final Player player, final Location location, final int level, final LightLocationStoredMH metadataHandler) {
        LightAPI.createLight(location, level, true);
        LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        return metadataHandler.addLight(location, level);
    }

    private static boolean killLight(final Player player, final Location location, final LightLocationStoredMH metadataHandler) {
        boolean foundAndKilledLight = metadataHandler.removeLight(location);

        if (foundAndKilledLight) {
            LightAPI.deleteLight(location, true);
            LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        }

        return foundAndKilledLight;
    }

    private static ItemStack getPlayerHeldItem(final Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private static boolean convertLightIntoBlock(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
        boolean didKill = killLight(player, location, metadataHandler);

        if (didKill) {
            Block block = player.getWorld().getBlockAt(location);
            block.setType(blockType);
            block.setBlockData(blockType.createBlockData());
        }

        return didKill;
    }

    private static boolean convertBlockIntoLight(final Player player, final Location location, final Material blockType, final LightLocationStoredMH metadataHandler) {
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

    @FunctionalInterface
    private interface BlockOperation {
        boolean perform(Player player, Location location, Material blockType, final LightLocationStoredMH metadataHandler);
    }

    private static int performRadialBlockOperation(final Player runningPlayer, final String[] args, final BlockOperation operationToPerform, final LightLocationStoredMH metadataHandler) {
        int radius = getIntFromFirstArg(args, DEFAULT_RADIUS);
        Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        Location location = runningPlayer.getLocation();
        World world = runningPlayer.getWorld();
        int noOfLightsConverted = 0;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int xPos = x - radius; xPos <= x + radius; xPos++) {
            for (int yPos = y - radius; yPos <= y + radius; yPos++) {
                for (int zPos = z - radius; zPos <= z + radius; zPos++) {
                    boolean successful =
                            operationToPerform.perform(runningPlayer,
                                                       new Location(world, xPos, yPos, zPos),
                                                       heldItemType,
                                                       metadataHandler
                            );

                    if (successful) {
                        noOfLightsConverted++;
                    }
                }
            }
        }

        return noOfLightsConverted;
    }

    private static int performRangeBlockOperation(final Player runningPlayer, final BlockOperation operationToPerform, final CuboidSelectionSessionMH cuboidSelectionSessionMH, final LightLocationStoredMH lightLocationStoredMH) {
        Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        World world = runningPlayer.getWorld();
        LocationPair positions = cuboidSelectionSessionMH.getLocationPair(runningPlayer);
        Location posA = positions.startingPos;
        Location posB = positions.finishingPos;
        int noOfLightsConverted = 0;

        LOGGER.log(Level.INFO, "world = {0}", world);
        LOGGER.log(Level.INFO, "posA = {0}", posA);
        LOGGER.log(Level.INFO, "posB = {0}", posB);

        for (int xPos = posA.getBlockX(); xPos <= posB.getBlockX(); xPos += getPolarity(posA.getBlockX(), posB.getBlockX())) {
            for (int yPos = posA.getBlockY(); yPos <= posB.getBlockY(); yPos += getPolarity(posA.getBlockY(), posB.getBlockY())) {
                for (int zPos = posA.getBlockZ(); zPos <= posB.getBlockZ(); zPos += getPolarity(posA.getBlockZ(), posB.getBlockZ())) {
                    final boolean successful =
                            operationToPerform.perform(runningPlayer,
                                                       new Location(world, xPos, yPos, zPos),
                                                       heldItemType,
                                                       lightLocationStoredMH
                            );

                    if (successful) {
                        noOfLightsConverted++;
                    }
                }
            }
        }

        return noOfLightsConverted;
    }
}
