package net.core.cgs.infinitechests;

import org.bukkit.Material;

public class AirMaterialUtils {
    public static boolean materialIsSomeFormOfAir(Material givenMaterial) {
        return givenMaterial == Material.AIR
                || givenMaterial == Material.CAVE_AIR
                || givenMaterial == Material.VOID_AIR
                || givenMaterial == Material.LEGACY_AIR;
    }
}
