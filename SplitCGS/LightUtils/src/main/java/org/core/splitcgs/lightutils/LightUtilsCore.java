package org.core.splitcgs.lightutils;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.splitcgs.lightutils.listeners.ShovelListener;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;
import org.core.splitcgs.lightutils.utils.filesystem.DataStorageUtil;

import javax.inject.Inject;
import java.util.logging.Logger;

public class LightUtilsCore extends JavaPlugin {
    private static DataStorageUtil DATA_STORAGE_UTIL;
    public static Logger LOGGER; // I want a static logger!
    @Inject private CuboidSelectionSessionMH selectionHandler;
    @Inject private LightLocationStoredMH lightLocationHandler;
    @Inject private ShovelListener shovelListener;
    @Inject private ParentCommandExecutor commandExecutor;

    public LightUtilsCore() {
        getDataFolder().mkdirs();
        DATA_STORAGE_UTIL = new DataStorageUtil(getDataFolder().getAbsolutePath());
        LOGGER = getLogger();
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
        getCommand("lightutils").setExecutor(commandExecutor);

        // Listeners
        final PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(shovelListener, this);
    }

    @Override
    public void onDisable() {
        DATA_STORAGE_UTIL.write(lightLocationHandler);
        DATA_STORAGE_UTIL.write(selectionHandler);
    }
}
