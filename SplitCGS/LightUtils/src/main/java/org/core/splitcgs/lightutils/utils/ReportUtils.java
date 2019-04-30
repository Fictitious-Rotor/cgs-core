package org.core.splitcgs.lightutils.utils;

import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.LightUtilsCore;

public class ReportUtils {
    @FunctionalInterface
    public interface ReportMassOperationFunction {
        void report(final Player runningPlayer, final int noOfConvertedBlocks);
    }

    public static void reportCreation(final Player runningPlayer, final boolean succeeded) {
        LightUtilsCore.sendToPlayer(runningPlayer, succeeded ? "Created light source at block" : "Unable to Create light source at block");
    }

    public static void reportDestruction(final Player runningPlayer, final boolean succeeded) {
        LightUtilsCore.sendToPlayer(runningPlayer, succeeded ? "Removed light source block" : "Unable to Remove light source block");
    }

    public static void reportMassLightification(final Player runningPlayer, final int noOfConvertedBlocks) {
        LightUtilsCore.sendToPlayer(runningPlayer, String.format("Managed to convert %s blocks into light sources", noOfConvertedBlocks));
    }

    public static void reportMassBlockification(final Player runningPlayer, final int noOfConvertedBlocks) {
        LightUtilsCore.sendToPlayer(runningPlayer, String.format("Managed to convert %s light sources into blocks", noOfConvertedBlocks));
    }

    public static void reportMassIdentification(final Player runningPlayer, final int noOfConvertedBlocks) {
        LightUtilsCore.sendToPlayer(runningPlayer, String.format("Managed to identify %s light sources", noOfConvertedBlocks));
    }

    public static void reportMassRemoval(final Player runningPlayer, final int noOfConvertedBlocks) {
        LightUtilsCore.sendToPlayer(runningPlayer, String.format("Managed to Remove %s light sources", noOfConvertedBlocks));
    }
}
