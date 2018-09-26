package net.core.cgs;

import net.core.cgs.data.DataStorageUtil;
import net.core.cgs.infinitechests.*;
import net.core.cgs.metadata.MetadataPretender;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {
    public ChestListener chestListener = new ChestListener(this);
    public PlayerInteractor playerInteractor = new PlayerInteractor(this);
    public DataStorageUtil dataStorageUtil = new DataStorageUtil(this);
    public MetadataPretender MARKED_CHESTS = new InfiniteChestsMetadata(this);
    public ChestMetadataHandler chestMetadataHandler = new ChestMetadataHandler(this);

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(chestListener, this);

        getCommand(InfiniteChestsCommandExecutor.BIND_INFINITE_COMMAND).setExecutor(new InfiniteChestsCommandExecutor(this));
        getCommand(InfiniteChestsCommandExecutor.UNBIND_INFINITE_COMMAND).setExecutor(new InfiniteChestsCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        dataStorageUtil.replaceStdFileWithCurrentSession();
    }
}
