package org.core.cgs.subplugins.lightutils.utils;

import org.core.cgs.generic.utilities.PrimedPLI;

public class ReportUtils {
    @FunctionalInterface
    public interface ReportMassOperationFunction {
        void report(final PrimedPLI PPLI, final int noOfConvertedBlocks);
    }

    public static void reportCreation(final PrimedPLI PPLI, final boolean succeeded) {
        PPLI.sendToPlayer(succeeded ? "Created light source at block" : "Unable to create light source at block");
    }

    public static void reportDestruction(final PrimedPLI PPLI, final boolean succeeded) {
        PPLI.sendToPlayer(succeeded ? "Removed light source block" : "Unable to remove light source block");
    }

    public static void reportMassLightification(final PrimedPLI PPLI, final int noOfConvertedBlocks) {
        PPLI.sendToPlayer(String.format("Managed to convert %s blocks into light sources", noOfConvertedBlocks));
    }

    public static void reportMassBlockification(final PrimedPLI PPLI, final int noOfConvertedBlocks) {
        PPLI.sendToPlayer(String.format("Managed to convert %s light sources into blocks", noOfConvertedBlocks));
    }

    public static void reportMassIdentification(final PrimedPLI PPLI, final int noOfConvertedBlocks) {
        PPLI.sendToPlayer(String.format("Managed to identify %s light sources in total", noOfConvertedBlocks));
    }
}
