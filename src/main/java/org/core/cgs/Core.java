package org.core.cgs;

import com.google.common.collect.ImmutableSet;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.cgs.data.DataStorageUtil;
import org.core.cgs.generic.classes.SubPlugin;
import org.core.cgs.generic.exceptions.MalformedSubPluginFolderException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.logging.Level.*;

public final class Core extends JavaPlugin {
//    private final String locationOfThisFile = new File(Core.class.getProtectionDomain()
//                                                                 .getCodeSource()
//                                                                 .getLocation()
//                                                                 .toURI()).getPath();
    private final Set<SubPlugin> allSubPlugins;

    public static DataStorageUtil DATA_STORAGE_UTIL;
    public static Logger LOGGER; // I want a static logger!

    public Core() throws URISyntaxException {
        LOGGER = getLogger(); // I want a static logger!
        DATA_STORAGE_UTIL = setDataStorageUtil();
        allSubPlugins = getAllSubPlugins();
        LOGGER.log(INFO, "locationOfThisFile = {0}", locationOfThisFile);
    }

    private DataStorageUtil setDataStorageUtil() {
        if (getDataFolder().mkdirs()) {
            LOGGER.log(INFO, "CGS-Core: Had to generate metadata data folder");
        }

        return new DataStorageUtil(getDataFolder().getAbsolutePath());
    }

    private Set<SubPlugin> getAllSubPlugins() {
        final ImmutableSet.Builder<SubPlugin> subPluginBuilder = ImmutableSet.builder();
        new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/org/core/cgs/subplugins/" + filename)))
        final String subPluginFolderLocation = Paths.get(locationOfThisFile, "org", "core", "cgs", "subplugins").toUri().toString();
        LOGGER.log(INFO, "subPluginFolderLocation = {0}", subPluginFolderLocation);
        final File subPluginFolder = new File(subPluginFolderLocation);
        final File[] subPluginPackages = subPluginFolder.listFiles();

        if (subPluginPackages == null) {
            throw new MalformedSubPluginFolderException("No subplugins were found!");
        }

        for (final File sPFolder : subPluginPackages) {
            DATA_STORAGE_UTIL.registerSubPlugin(sPFolder.getName());

            final String commandFileLocation = Paths.get(sPFolder.getPath(), "Commands.yml").toString();
            subPluginBuilder.add(new SubPlugin(commandFileLocation));
//            final String packageLocation = String.format("org.core.cgs.subplugins.%s", sPName);
//            final Set<Class<? extends SubPlugin>> foundSubPlugins = new Reflections(packageLocation).getSubTypesOf(SubPlugin.class);
//
//            if (foundSubPlugins.size() == 0) {
//                throw new NoCoreClassException();
//            } else if (foundSubPlugins.size() > 1) {
//                throw new TooManyCoreClassesException(foundSubPlugins.toString());
//            }
//
//            final Class<? extends SubPlugin> firstSubPluginClass = Lists.newArrayList(foundSubPlugins).get(0);
//            subPluginBuilder.add(firstSubPluginClass.newInstance());
        }

        return subPluginBuilder.build();
    }

    private static void bolg() throws URISyntaxException, IOException {
        URI uri = Core.class.getResource("/resources").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/resources");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            System.out.println(it.next());
        }
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
