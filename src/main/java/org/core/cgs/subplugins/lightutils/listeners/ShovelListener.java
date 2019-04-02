package org.core.cgs.subplugins.lightutils.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.core.cgs.generic.abstracts.CGSListener;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PlayerInterface;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.session.CuboidSelectionSessionMH;

public class ShovelListener extends CGSListener {
    private static final Material ACTIVATING_MATERIAL = Material.WOODEN_SHOVEL;
    private final CuboidSelectionSessionMH metadataHandler;

    public ShovelListener(final MetadataBundle metadataBundle, final PlayerInterface playerInterface) {
        super(metadataBundle, playerInterface);
        this.metadataHandler = metadataBundle.getHandler(CuboidSelectionSessionMH.class);
    }

    private static void sendToPlayer(final PrimedPLI PPLI,
                                     final Location location,
                                     final String positionType) {
        PPLI.sendToPlayer(String.format("Set %s position as (%s, %s, %s)",
                                        positionType,
                                        location.getBlockX(),
                                        location.getBlockY(),
                                        location.getBlockZ()
                          )
        );
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final PrimedPLI PPLI = playerInterface.prime(player);

        if (event.hasBlock() && event.getMaterial() == ACTIVATING_MATERIAL) {
            event.setCancelled(true);
            final Location location = event.getClickedBlock()
                                           .getLocation();
            final Action action = event.getAction();

            if (action == Action.LEFT_CLICK_BLOCK) {
                metadataHandler.setStartingPos(player, location);
                sendToPlayer(PPLI, location, "starting");
            } else if (action == Action.RIGHT_CLICK_BLOCK) {
                metadataHandler.setFinishingPos(player, location);
                sendToPlayer(PPLI, location, "finishing");
            }
        }
    }
}
