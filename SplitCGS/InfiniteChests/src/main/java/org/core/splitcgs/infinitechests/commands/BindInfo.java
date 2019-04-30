package org.core.splitcgs.infinitechests.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.core.splitcgs.infinitechests.InfiniteChestsCore;
import org.core.splitcgs.infinitechests.commons.BindCommons;
import org.core.splitcgs.infinitechests.metadata.stored.ChestStoredMH;

public class BindInfo extends BindCommons implements CommandExecutor {
    private final ChestStoredMH chestMetadataHandler;

    public BindInfo(final ChestStoredMH chestMetadataHandler) {
        this.chestMetadataHandler = chestMetadataHandler;
    }

    @Override
    protected boolean runPlayerCommand(Player player, Command command, String s, String[] arguments) {
        final Block foundBlock = player.getTargetBlock(null, 6);

        if (!(chestMetadataHandler.givenBlockIsChest(foundBlock))) {
            InfiniteChestsCore.sendToPlayer(player, "This command only works on chests");
            return false;
        }

        if (!(chestMetadataHandler.givenBlockIsInfinite(foundBlock))) {
            InfiniteChestsCore.sendToPlayer(player, "The specified chest is not infinite");
            return false;
        }

        reportInfiniteChestCreation(player, foundBlock, chestMetadataHandler.getInfiniteItem(foundBlock));
        return true;
    }
}
