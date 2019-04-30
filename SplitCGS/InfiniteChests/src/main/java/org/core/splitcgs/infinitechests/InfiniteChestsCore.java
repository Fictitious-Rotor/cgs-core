package org.core.splitcgs.infinitechests;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.splitcgs.infinitechests.commands.*;
import org.core.splitcgs.infinitechests.listeners.ChestListener;
import org.core.splitcgs.infinitechests.metadata.stored.ChestStoredMH;
import org.core.splitcgs.infinitechests.utilities.DataStorageUtil;

import java.util.logging.Logger;

public class InfiniteChestsCore extends JavaPlugin {
    private static DataStorageUtil DATA_STORAGE_UTIL;
    public static Logger LOGGER; // I want a static logger!
    private ChestStoredMH csmh;
    private ChestListener cl;

    public InfiniteChestsCore() {
        LOGGER = getLogger(); // I want a static logger!
        getDataFolder().mkdirs();
        DATA_STORAGE_UTIL = new DataStorageUtil(getDataFolder().getAbsolutePath());
        csmh = new ChestStoredMH();
        cl = new ChestListener(csmh);
    }

    public static void sendToPlayer(final HumanEntity receiver, final String... messages) {
        final StringBuilder toSend = new StringBuilder();

        toSend.append(ChatColor.BOLD).append(ChatColor.GOLD)
              .append("Infinite Chests:")
              .append(ChatColor.ITALIC).append(ChatColor.DARK_AQUA);

        for (String message : messages) {
            toSend.append(message)
                  .append('\n');
        }

        toSend.append(ChatColor.RESET);

        receiver.sendMessage(toSend.toString());
    }

    @Override
    public void onEnable() {
        // Commands
        getCommand("bind").setExecutor(new Bind());
        getCommand("bindinfo").setExecutor(new BindInfo(new ChestStoredMH()));
        getCommand("forcebind").setExecutor(new ForceBind());
        getCommand("isinfinite").setExecutor(new IsInfinite());
        getCommand("unbind").setExecutor(new Unbind());

        // Listeners
        final PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(cl, this);
    }

    @Override
    public void onDisable() {
        DATA_STORAGE_UTIL.write(csmh);
    }
}
