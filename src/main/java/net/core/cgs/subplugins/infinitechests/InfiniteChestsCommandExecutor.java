package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.beykerykt.lightapi.LightAPI;

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
            case FORCE_BIND_INFINITE_ITEM_COMMAND: return (split.length > 1) && forceBindInfiniteItem(runningPlayer, split[0], Byte.parseByte(split[1]));
            default: return false;
        }
    }

    private boolean bindInfiniteItem(Player runningPlayer) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            ItemStack heldItem = runningPlayer.getInventory().getItemInMainHand();
            Material itemType = heldItem.getType();
            byte itemHorrificMetadata = heldItem.getData().getData();

            runningPlayer.sendMessage(String.format("InfiniteChests: You have bound the chest at %s to the material %s with the metadata of %s", foundBlock.getLocation().toString(), itemType, itemHorrificMetadata));

            if (AirMaterialUtils.materialIsSomeFormOfAir(itemType)) {
                runningPlayer.sendMessage(new String[]{ "CAUTION:", "YOU HAVE BOUND THIS CHEST TO AIR, WHICH MEANS THAT ANY ITEMS PLACED INTO THIS CHEST WILL BE DELETED WHEN THE CHEST IS NEXT OPENED", "ENSURE THAT YOU MARK THIS CHEST APPROPRIATELY, SO THAT VALUABLES ARE NOT PLACED WITHIN" });
            }

            return plugin.chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(itemType, itemHorrificMetadata));
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

    private boolean forceBindInfiniteItem(Player runningPlayer, String specifiedMaterial, byte specifiedMetadata) {
        Block foundBlock = runningPlayer.getTargetBlock(null, 6);

        if (plugin.chestMetadataHandler.givenBlockIsChest(foundBlock)) {
            Material specifiedBD = Material.matchMaterial(specifiedMaterial, false);

            if (specifiedBD != null) {
                runningPlayer.sendMessage(String.format("InfiniteChests: You have bound the chest at %s to the material %s", foundBlock.getLocation().toString(), specifiedMaterial));

                return plugin.chestMetadataHandler.makeBlockInfiniteChest(foundBlock, new HorrificBytesMaterialBundle(specifiedBD, specifiedMetadata));
            } else {
                String errorMessage = String.format("InfiniteChests: Unable to bind chest to unrecognised argument: %s", specifiedMaterial);
                runningPlayer.sendMessage(errorMessage);
                plugin.getLogger().log(Level.FINE, errorMessage);
            }
        }

        return false;
    }
}
