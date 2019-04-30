package org.core.splitcgs.lightutils.listeners;

import dagger.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.core.splitcgs.lightutils.LightUtilsCore;
import org.core.splitcgs.lightutils.ProviderModule;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH;

import javax.inject.Inject;
import javax.inject.Singleton;

@Component(modules = ProviderModule.class)
@Singleton
public class ShovelListener implements Listener {
    private static final Material ACTIVATING_MATERIAL = Material.WOODEN_SHOVEL;
    @Inject private CuboidSelectionSessionMH cuboidHandler;

    private static void sendToPlayer(final Player runningPlayer,
                                     final Location location,
                                     final String positionType) {
        LightUtilsCore.sendToPlayer(runningPlayer,
                                    String.format("Set %s position as (%s, %s, %s)",
                                    positionType,
                                    location.getBlockX(),
                                    location.getBlockY(),
                                    location.getBlockZ()));
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.hasBlock() && event.getMaterial() == ACTIVATING_MATERIAL) {
            event.setCancelled(true);
            final Location location = event.getClickedBlock()
                                           .getLocation();
            final Action action = event.getAction();

            if (action == Action.LEFT_CLICK_BLOCK) {
                cuboidHandler.setStartingPos(player, location);
                LightUtilsCore.sendToPlayer(player, location.toString(), "starting");
            } else if (action == Action.RIGHT_CLICK_BLOCK) {
                cuboidHandler.setFinishingPos(player, location);
                LightUtilsCore.sendToPlayer(player, location.toString(), "finishing");
            }
        }
    }
}
