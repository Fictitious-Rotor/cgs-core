package org.core.splitcgs.infinitechests.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.TextLine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.splitcgs.infinitechests.commons.BindCommons;
import org.core.splitcgs.infinitechests.metadata.HorrificBytesMaterialBundle;
import org.core.splitcgs.infinitechests.metadata.stored.ChestStoredMH;
import org.core.splitcgs.infinitechests.utilities.AirMaterialUtils;
import org.core.splitcgs.infinitechests.utilities.PrimedPLI;

public class Bind extends BindCommons implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        return false;
    }

    private static void bindToChest(final Player runningPlayer,
                                    final Block foundBlock,
                                    final ChestStoredMH chestMetadataHandler,
                                    final PrimedPLI PPLI) {
        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            PPLI.sendToPlayer("This command only works on chests", "Run '/infinitechests help bind' for more information");
            return;
        }

        final ItemStack heldItem = runningPlayer.getInventory().getItemInMainHand();
        final Material itemType = heldItem.getType();
        final byte itemHorrificMetadata = heldItem.getData().getData();

        if (AirMaterialUtils.materialIsSomeFormOfAir(itemType)) {
            createVoidChest(foundBlock, PPLI);
        } else {
            reportInfiniteChestCreation(PPLI, foundBlock, itemType, itemHorrificMetadata);
        }

        chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(itemType, itemHorrificMetadata));
    }

    private static void createVoidChest(final Block foundBlock,
                                        final PrimedPLI PPLI) {
        final Location chestLocation = foundBlock.getLocation().clone();
        final String hologramName = formatChestLocation(chestLocation);

        final Hologram hologram = new Hologram(hologramName, chestLocation.add(0.5, 0.65, 0.5));
        hologram.addLine(new TextLine(hologram, "Void_Chest"));

        final HologramManager hologramManager = JavaPlugin.getPlugin(HologramPlugin.class)
                                                          .getHologramManager();
        hologramManager.addActiveHologram(hologram);
        hologramManager.saveHologram(hologram);

        PPLI.sendToPlayer("Created new void chest");
    }
}
