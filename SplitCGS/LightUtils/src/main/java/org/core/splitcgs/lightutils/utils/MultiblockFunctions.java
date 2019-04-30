package org.core.splitcgs.lightutils.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.core.splitcgs.lightutils.LightUtilsCore;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH.LocationPair;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;

import java.util.Optional;

import static java.util.logging.Level.INFO;
import static org.core.splitcgs.lightutils.LightUtilsCore.LOGGER;
import static org.core.splitcgs.lightutils.utils.ParsingUtils.getIntFromFirstArg;

public class MultiblockFunctions {
    private static final int DEFAULT_RADIUS = 5;
    private static final int MAXIMUM_PERMITTED_OPERATIONS = 75 * 75 * 75;

    private static ItemStack getPlayerHeldItem(final Player player) {
        return player.getInventory()
                     .getItemInMainHand();
    }

    private static int getPolarity(final int startPos, final int finishPos) {
        return ((startPos - finishPos) >= 0) ? 1 : -1;
    }

    private static boolean isSafeOpCount(final Player runningPlayer, final Integer noOfOps) {
        if (noOfOps > MAXIMUM_PERMITTED_OPERATIONS) {
            LightUtilsCore.sendToPlayer(runningPlayer, "Player has requested operations on too many blocks! (", noOfOps.toString(), ")");
            return false;
        }

        return true;
    }

    private static boolean isSafeToUseLocationPair(final Player runningPlayer, final CuboidSelectionSessionMH cSMH) {
        LOGGER.log(INFO, "PPLI.getPlayer() = {0}", runningPlayer);
        final LocationPair positionPair = cSMH.getLocationPair(runningPlayer);
        LOGGER.log(INFO, "positionPair = {0}", positionPair);

        return (positionPair != null) && (!positionPair.isCompleteLP());
    }

    @FunctionalInterface
    public interface BlockOperation {
        boolean perform(final Player player,
                        final Location location,
                        final Material blockType,
                        final LightLocationStoredMH metadataHandler);
    }

    public static Optional<Integer> performRadialBlockOperation(final Player runningPlayer,
                                                                final String[] args,
                                                                final BlockOperation operationToPerform,
                                                                final LightLocationStoredMH metadataHandler) {
        final int radius = getIntFromFirstArg(args, DEFAULT_RADIUS);
        final Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        final Location location = runningPlayer.getLocation();
        final World world = runningPlayer.getWorld();
        int noOfLightsConverted = 0;

        final int x = location.getBlockX();
        final int y = location.getBlockY();
        final int z = location.getBlockZ();

        if (!(isSafeOpCount(runningPlayer, (int)Math.pow(radius + 1 + radius, 3)))) {
            return Optional.empty();
        }

        for (int xPos = x - radius; xPos <= x + radius; xPos++) {
            for (int yPos = y - radius; yPos <= y + radius; yPos++) {
                for (int zPos = z - radius; zPos <= z + radius; zPos++) {
                    final boolean successful =
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

        return Optional.of(noOfLightsConverted);
    }

    public static Optional<Integer> performRangeBlockOperation(final Player runningPlayer,
                                                               final BlockOperation operationToPerform,
                                                               final CuboidSelectionSessionMH cSSMH,
                                                               final LightLocationStoredMH lightLocationStoredMH) {
        final Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        final World world = runningPlayer.getWorld();

        if (!(isSafeToUseLocationPair(runningPlayer, cSSMH))) {
            LightUtilsCore.sendToPlayer(runningPlayer, "You have not yet defined two points for the range!", "Run '/infinitechests help Wand' for more information");
            return Optional.empty();
        }

        final LocationPair positions = cSSMH.getLocationPair(runningPlayer);
        final Location posA = positions.startingPos;
        final Location posB = positions.finishingPos;

        int noOfLightsConverted = 0;

        if (!(isSafeOpCount(runningPlayer, (int)posA.distanceSquared(posB)))) {
            return Optional.empty();
        }

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

        return Optional.of(noOfLightsConverted);
    }
}
