package org.core.cgs.subplugins.infinitechests.commons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.core.cgs.generic.utilities.PrimedPLI;

public class BindCommons extends ICCommons {
    protected static void reportInfiniteChestCreation(final PrimedPLI PPLI,
                                               final Block foundBlock,
                                               final Material specifiedMaterial,
                                               final byte specifiedMetadata) {
        PPLI.sendToPlayer(String.format("You have bound the chest at (%s, %s, %s)",
                                        foundBlock.getLocation().getBlockX(),
                                        foundBlock.getLocation().getBlockY(),
                                        foundBlock.getLocation().getBlockZ()),
                          String.format("to the material '%s'", specifiedMaterial),
                          String.format("With a damage value of '%s'", specifiedMetadata)
        );
    }



}
