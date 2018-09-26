package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfiniteChestsCommandExecutor implements CommandExecutor {
    public static final String BIND_INFINITE_COMMAND = "bindinfiniteitem";
    public static final String UNBIND_INFINITE_COMMAND = "unbindinfinite";

    private Core plugin;

    public InfiniteChestsCommandExecutor(Core instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        String formalised = command.getName().toLowerCase();
        Player runningPlayer;

        if (sender instanceof Player) {
            runningPlayer = (Player)sender;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        switch (formalised) {
            case BIND_INFINITE_COMMAND : return bindInfiniteItem(runningPlayer);
            case UNBIND_INFINITE_COMMAND : return unbindInfinite(runningPlayer);
            default: return false;
        }
    }

    private boolean bindInfiniteItem(Player runningPlayer) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            Material heldItemType = plugin.playerInteractor.getPlayerHeldMaterial(runningPlayer);

            return plugin.chestMetadataHandler.makeBlockInfiniteChest(foundBlock, heldItemType);
        }
        return false;
    }

    private boolean unbindInfinite(Player runningPlayer) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsInfiniteChest(foundBlock)) {
            return plugin.chestMetadataHandler.makeBlockFiniteChest(foundBlock);
        }
        return false;
    }
}
