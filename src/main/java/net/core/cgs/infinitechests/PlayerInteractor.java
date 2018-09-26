package net.core.cgs.infinitechests;

import net.core.cgs.Core;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

public class PlayerInteractor {
    private Core plugin;

    public PlayerInteractor(Core instance) {
        plugin = instance;
    }

    public Material getPlayerHeldMaterial(HumanEntity player) {
        return player.getInventory().getItemInMainHand().getType();
    }
}
