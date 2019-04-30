package org.core.splitcgs.lightutils.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.core.splitcgs.lightutils.commons.PlayerDemander;

public class Wand extends PlayerDemander {
    @Override
    protected void handlePlayerCommand(final Player runningPlayer,
                                       final Command command,
                                       final String aliasUsed,
                                       final String[] args) {
        runningPlayer.getInventory()
                     .addItem(new ItemStack(Material.WOODEN_SHOVEL, 1));
    }
}
