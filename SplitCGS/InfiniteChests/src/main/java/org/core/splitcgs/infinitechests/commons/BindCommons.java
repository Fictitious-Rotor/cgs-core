package org.core.splitcgs.infinitechests.commons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.core.splitcgs.infinitechests.InfiniteChestsCore;
import org.core.splitcgs.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.splitcgs.infinitechests.utilities.PrimedPLI;

public abstract class BindCommons extends ICCommons {
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

    protected static void reportInfiniteChestCreation(final Player player,
                                                      final Block foundBlock,
                                                      final HorrificBytesMaterialBundle specifiedHorror) {
        InfiniteChestsCore.sendToPlayer(player,
                                        String.format("You have bound the chest at (%s, %s, %s)",
                                        foundBlock.getLocation().getBlockX(),
                                        foundBlock.getLocation().getBlockY(),
                                        foundBlock.getLocation().getBlockZ()),
                                        String.format("to the material '%s'", specifiedHorror.getItemType()),
                                        String.format("With a damage value of '%s'", specifiedHorror.getItemMetadata())
        );
    }
}
