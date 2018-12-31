package org.core.cgs;

import com.google.common.collect.ImmutableSet;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.cgs.data.DataStorageUtil;
import org.core.cgs.generic.abstracts.SubPlugin;
import org.core.cgs.subplugins.infinitechests.InfiniteChestsCore;
import org.core.cgs.subplugins.lightutils.LightUtilsCore;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Core extends JavaPlugin {
    private final static Set<Class<? extends SubPlugin>> ALL_SUB_PLUGIN_CLASSES = ImmutableSet.of(InfiniteChestsCore.class, LightUtilsCore.class);
    private final Set<SubPlugin> allSubPlugins;

    public static DataStorageUtil DATA_STORAGE_UTIL;
    public static Logger LOGGER; // I want a static logger!

    public Core() throws IllegalAccessException, InstantiationException {
        LOGGER = getLogger(); // I want a static logger!
        DATA_STORAGE_UTIL = setDataStorageUtil();
        allSubPlugins = getAllSubPlugins();
    }

    private DataStorageUtil setDataStorageUtil() {
        if (getDataFolder().mkdirs()) {
            LOGGER.log(Level.INFO, "Had to generate metadata data folder");
        }

        return new DataStorageUtil(getDataFolder().getAbsolutePath());
    }

    private Set<SubPlugin> getAllSubPlugins() throws IllegalAccessException, InstantiationException {
        final ImmutableSet.Builder<SubPlugin> subPluginBuilder = ImmutableSet.builder();

        for (Class<? extends SubPlugin> sPClass : ALL_SUB_PLUGIN_CLASSES) {
            final SubPlugin instantiatedSP = sPClass.newInstance();

            DATA_STORAGE_UTIL.registerSubPlugin(instantiatedSP.getName());
            subPluginBuilder.add(instantiatedSP);
        }

        return subPluginBuilder.build();
    }

    @Override
    public void onEnable() {
        // Set up the listeners
        final PluginManager pm = getServer().getPluginManager();

        allSubPlugins.forEach(subPlugin -> {
            subPlugin.registerEvents(this, pm);
            subPlugin.registerCommands(this);
        });
    }

    @Override
    public void onDisable() { allSubPlugins.forEach(sP -> DATA_STORAGE_UTIL.replacePreviousSessionWithCurrent(sP.getName())); }
}
