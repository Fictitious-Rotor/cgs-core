package net.core.cgs.subplugins.lightutils;

import net.core.cgs.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.beykerykt.lightapi.LightAPI;

import java.util.Collections;
import java.util.logging.Level;

public class LightUtilsCommandExecutor implements CommandExecutor {
    static final String CREATE_LIGHT_COMMAND = "createlight";
    static final String REMOVE_LIGHT_COMMAND = "removelight";
    static final String MAKE_BLOCK_INTO_LIGHT_COMMAND = "makeblockintolight";
    static final String BLOCKIFY_LIGHT_IN_RADIUS = "blockifylightinradius";

    private static final int DEFAULT_LIGHT_LEVEL = 15;
    private static final int DEFAULT_RADIUS = 5;

    private Core plugin;

    public LightUtilsCommandExecutor(Core plugin) {
        this.plugin = plugin;
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
            case CREATE_LIGHT_COMMAND: return createLight(runningPlayer, split);
            case REMOVE_LIGHT_COMMAND: return removeLight(runningPlayer);
            case MAKE_BLOCK_INTO_LIGHT_COMMAND: return convertBlockIntoLight(runningPlayer);
            case BLOCKIFY_LIGHT_IN_RADIUS: return blockifyLightInRadius(runningPlayer, split);
            default: return false;
        }
    }

    private int getIntFromFirstArg(String[] args, int defaultValue) {
        if((args != null) && (args.length > 0)) {
            try {
                return Integer.parseInt(args[0]);
            } catch (Exception ex) {
                plugin.getLogger().log(Level.FINE, "Unable to parse args[1] of {0}", args[0]);
            }
        }

        return defaultValue;
    }

    private boolean makeLight(Player player, Location location, int level) {
        boolean output = LightAPI.createLight(location, level, true);
        LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        return output;
    }

    private boolean killLight(Player player, Location location) {
        boolean output = LightAPI.deleteLight(location, true);
        LightAPI.updateChunks(player.getLocation(), Collections.singleton(player));
        return output;
    }

    private boolean createLight(Player runningPlayer, String[] arguments) {
        int level = getIntFromFirstArg(arguments, DEFAULT_LIGHT_LEVEL);

        Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        boolean successful = makeLight(runningPlayer, targetedBlock.getLocation(), level);
        runningPlayer.sendMessage(successful ? "Created light source at block" : "Unable to create light source at block");
        return successful;
    }

    private boolean removeLight(Player runningPlayer) {
        Block targetedBlock = runningPlayer.getTargetBlock(null, 10);
        boolean successful = killLight(runningPlayer, targetedBlock.getLocation());
        runningPlayer.sendMessage(successful ? "Removed light source block" : "Unable to remove light source block");
        return successful;
    }

//    private boolean convertLightIntoBlock(Player runningPlayer) {
//        Location location = runningPlayer.getTargetBlock(null, 10).getLocation();
//        ItemStack itemInPlayersHand = getPlayerHeldItem(runningPlayer);
//
//        return convertLightIntoBlock(runningPlayer, location);
//    }

    private ItemStack getPlayerHeldItem(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private boolean convertLightIntoBlock(Player player, Location location, Material blockType) {
        if (killLight(player, location)) {
            Block block = player.getWorld().getBlockAt(location);
            block.setType(blockType);
            block.setBlockData(blockType.createBlockData());
            return true;
        }
        return false;
    }

    private boolean convertBlockIntoLight(Player runningPlayer) {
        Location location = runningPlayer.getTargetBlock(null, 10).getLocation();
        runningPlayer.getWorld().getBlockAt(location).setType(Material.AIR);
        return makeLight(runningPlayer, location, 15);
    }

    private boolean blockifyLightInRadius(Player runningPlayer, String[] args) {
        int radius = getIntFromFirstArg(args, DEFAULT_RADIUS);
        Location location = runningPlayer.getLocation();
        World world = runningPlayer.getWorld();
        int noOfLightsConverted = 0;

        for (int xPos = location.getBlockX() - radius; xPos <= location.getBlockX() + radius; xPos++) {
            for (int yPos = location.getBlockY() - radius; yPos <= location.getBlockY() + radius; yPos++) {
                for (int zPos = location.getBlockZ() - radius; zPos <= location.getBlockZ() + radius; zPos++) {
                    boolean successful = convertLightIntoBlock(runningPlayer, new Location(world, xPos, yPos, zPos), getPlayerHeldItem(runningPlayer).getType());

                    if (successful) {
                        noOfLightsConverted++;
                    }
                }
            }
        }

        runningPlayer.sendMessage(String.format("Managed to convert %s", noOfLightsConverted));
        return noOfLightsConverted > 0;
    }
}
