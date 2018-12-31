package org.core.cgs.subplugins.lightutils.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;

import static org.core.cgs.subplugins.lightutils.LightUtilsCore.PLI;

public class ShovelListener implements Listener {
    private static final Material ACTIVATING_MATERIAL = Material.WOODEN_SHOVEL;
    private final CuboidSelectionSessionMH metadataHandler;

    public ShovelListener(CuboidSelectionSessionMH metadataHandler) {
        this.metadataHandler = metadataHandler;
    }

    private static void sendToPlayer(Player player, Location location, String positionType) {
        PLI.sendToPlayer(player,
                         String.format("Set %s position as (%s, %s, %s)",
                                       positionType,
                                       location.getBlockX(),
                                       location.getBlockY(),
                                       location.getBlockZ()
                         )
        );
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.hasBlock() && event.getMaterial() == ACTIVATING_MATERIAL) {
            event.setCancelled(true);
            Location location = event.getClickedBlock()
                                     .getLocation();
            Action action = event.getAction();

            if (action == Action.LEFT_CLICK_BLOCK) {
                metadataHandler.setStartingPos(player, location);
                sendToPlayer(player, location, "starting");
            } else if (action == Action.RIGHT_CLICK_BLOCK) {
                metadataHandler.setFinishingPos(player, location);
                sendToPlayer(player, location, "finishing");
            }
        }
    }
}
