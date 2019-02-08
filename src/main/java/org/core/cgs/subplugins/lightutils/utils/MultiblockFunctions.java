package org.core.cgs.subplugins.lightutils.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH.LocationPair;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;

import java.util.Optional;

import static org.core.cgs.generic.utilities.ParsingUtils.getIntFromFirstArg;

public class MultiblockFunctions {
    private static final int DEFAULT_RADIUS = 5;
    private static final int MAXIMUM_PERMITTED_OPERATIONS = 421875; // 75 cubed

    private static ItemStack getPlayerHeldItem(final Player player) {
        return player.getInventory()
                     .getItemInMainHand();
    }

    private static int getPolarity(final int startPos, final int finishPos) {
        return ((startPos - finishPos) >= 0) ? 1 : -1;
    }

    private static boolean isSafeOpCount(final PrimedPLI PPLI, final Integer noOfOps) {
        if (noOfOps > MAXIMUM_PERMITTED_OPERATIONS) {
            PPLI.sendToPlayer("Player has requested operations on too many blocks! (", noOfOps.toString(), ")");
            return false;
        }

        return true;
    }

    private static boolean isSafeToUseLocationPair(final PrimedPLI PPLI, final CuboidSelectionSessionMH cSMH) {
        final LocationPair positionPair = cSMH.getLocationPair((Player)PPLI.getReceiver());

        if ((positionPair == null) || (positionPair.isCompleteLP())) {
            PPLI.sendToPlayer("You have not yet chosen two locations for a range!");
            return false;
        }

        return true;
    }

    @FunctionalInterface
    public interface BlockOperation {
        boolean perform(final Player player,
                        final Location location,
                        final Material blockType,
                        final LightLocationStoredMH metadataHandler);
    }


    public static Optional<Integer> performRadialBlockOperation(final PrimedPLI PPLI,
                                                                final String[] args,
                                                                final BlockOperation operationToPerform,
                                                                final LightLocationStoredMH metadataHandler) {
        final int radius = getIntFromFirstArg(args, DEFAULT_RADIUS);
        final Player runningPlayer = PPLI.getPlayer();
        final Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        final Location location = runningPlayer.getLocation();
        final World world = runningPlayer.getWorld();
        int noOfLightsConverted = 0;

        final int x = location.getBlockX();
        final int y = location.getBlockY();
        final int z = location.getBlockZ();

        if (!(isSafeOpCount(PPLI, (int)Math.pow(radius + 1 + radius, 3)))) {
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

    public static Optional<Integer> performRangeBlockOperation(final PrimedPLI PPLI,
                                                               final BlockOperation operationToPerform,
                                                               final CuboidSelectionSessionMH cSSMH,
                                                               final LightLocationStoredMH lightLocationStoredMH) {
        final Player runningPlayer = PPLI.getPlayer();
        final Material heldItemType = getPlayerHeldItem(runningPlayer).getType();
        final World world = runningPlayer.getWorld();

        if (!(isSafeToUseLocationPair(PPLI, cSSMH))) {
            return Optional.empty();
        }

        final LocationPair positions = cSSMH.getLocationPair(runningPlayer);
        final Location posA = positions.startingPos;
        final Location posB = positions.finishingPos;

        int noOfLightsConverted = 0;

        if (!(isSafeOpCount(PPLI, (int)posA.distanceSquared(posB)))) {
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
