package org.core.cgs.subplugins.lightutils.backends;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.interfaces.CommandBackend;
import org.core.cgs.generic.utilities.PrimedPLI;

public class wand implements CommandBackend {
    @Override
    public void run(final Player runningPlayer,
                       final PrimedPLI PPLI,
                       final String[] arguments,
                       final MetadataBundle metadataBundle) {
        runningPlayer.getInventory()
                     .addItem(new ItemStack(Material.WOODEN_SHOVEL, 1));
    }
}
