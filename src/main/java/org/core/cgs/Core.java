package org.core.cgs;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.cgs.codegen.FoundSubpluginMaterials;
import org.core.cgs.data.DataStorageUtil;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public final class Core extends JavaPlugin {
    public static DataStorageUtil DATA_STORAGE_UTIL;
    public static Logger LOGGER; // I want a static logger!

    public Core() {
        LOGGER = getLogger(); // I want a static logger!
        DATA_STORAGE_UTIL = setDataStorageUtil();
        registerAllSubPlugins();
    }

    private DataStorageUtil setDataStorageUtil() {
        if (getDataFolder().mkdirs()) {
            LOGGER.log(INFO, "CGS-Core: Had to generate metadata data folder");
        }

        return new DataStorageUtil(getDataFolder().getAbsolutePath());
    }

    private void registerAllSubPlugins() {
        FoundSubpluginMaterials.getFoundSubPlugins()
                               .forEach(DATA_STORAGE_UTIL::registerSubPlugin);
    }

    @Override
    public void onEnable() {
        // Set up the listeners
        final PluginManager pm = getServer().getPluginManager();

        FoundSubpluginMaterials.getFoundSubPlugins()
                               .forEach(subPlugin -> {
            subPlugin.registerEvents(this, pm);
            subPlugin.registerCommands(this);
        });
    }

    @Override
    public void onDisable() {
        FoundSubpluginMaterials.getFoundSubPlugins()
                               .forEach(sP -> DATA_STORAGE_UTIL.replacePreviousSessionWithCurrent(sP.getSimpleName()));
    }
}
