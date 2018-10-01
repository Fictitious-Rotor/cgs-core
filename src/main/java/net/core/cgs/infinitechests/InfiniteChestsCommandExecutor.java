package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class InfiniteChestsCommandExecutor implements CommandExecutor {
    public static final String BIND_INFINITE_COMMAND = "bindinfiniteitem";
    public static final String UNBIND_INFINITE_COMMAND = "unbindinfiniteitem";
    public static final String FORCE_BIND_INFINITE_ITEM_COMMAND = "forcebindinfiniteitem";

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
            case FORCE_BIND_INFINITE_ITEM_COMMAND: if (split.length > 0) return forceBindInfiniteItem(runningPlayer, split[0]);
            default: return false;
        }
    }

    private boolean bindInfiniteItem(Player runningPlayer) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            BlockData heldItemType = plugin.playerInteractor.getPlayerHeldMaterial(runningPlayer).createBlockData();

            runningPlayer.sendMessage(String.format("InfiniteChests: You have bound the chest at %s to the material %s", foundBlock.getLocation().toString(), heldItemType.getAsString()));
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

    private boolean forceBindInfiniteItem(Player runningPlayer, String specifiedBlockData) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            runningPlayer.sendMessage(String.format("InfiniteChests: You have bound the chest at %s to the material %s", foundBlock.getLocation().toString(), specifiedBlockData));

            try {
                BlockData specifiedBD = plugin.getServer().createBlockData(specifiedBlockData);

                return plugin.chestMetadataHandler.makeBlockInfiniteChest(foundBlock, specifiedBD);
            } catch (IllegalArgumentException exception) {
                runningPlayer.sendMessage(String.format("InfiniteChests: Unable to bind chest to unrecognised argument: %s", specifiedBlockData));
                plugin.getLogger().log(Level.FINE, "Chest could not be bound due to unrecognised blockData argument: ", exception);
            }
        }

        return false;
    }
}
