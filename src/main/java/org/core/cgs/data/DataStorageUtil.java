package org.core.cgs.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.core.cgs.Core;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static org.core.cgs.data.DataStorageUtil.FileSystemName.*;

public class DataStorageUtil {
    private final Map<String, Map<FileSystemName, Path>> subPlugsToPaths;
    private final String basePath;

    public DataStorageUtil(String basePath) {
        this.basePath = basePath;
        subPlugsToPaths = new HashMap<>();
    }

    public void registerSubPlugin(String subPluginName) {
        createSubPluginFoldersIfNotExisting(basePath, subPluginName);

        subPlugsToPaths.put(subPluginName, constructSubPluginFileDetails(basePath, subPluginName));
    }

    private void createSubPluginFoldersIfNotExisting(final String curPath, final String subPluginName) {
        makeFolderIfNotExists(Paths.get(curPath, subPluginName));
    }

    private Map<FileSystemName, Path> constructSubPluginFileDetails(final String curPath, final String subPluginName) {
        ImmutableMap.Builder<FileSystemName, Path> subPluginPaths = ImmutableMap.builder();

        for (FileSystemName fileSystemName : FileSystemName.values()) {
            subPluginPaths.put(fileSystemName, Paths.get(curPath, subPluginName, fileSystemName.name));
        }

        return subPluginPaths.build();
    }

    public void storeCurrentSession(final Map<String, String> mapToWrite, final String subPluginName) {
        try {
            final Path pluginPath = getPathFromPlugin(subPluginName, FileSystemName.CURRENT_SESSION);

            Files.write(pluginPath, convertMapIntoString(mapToWrite).getBytes());
        } catch (Exception ex) {
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    public Map<String, String> getPreviousSession(final String subPluginName) {
        try {
            final Path pluginPath = getPathFromPlugin(subPluginName, PREVIOUS_SESSION);

            if (!(Files.exists(pluginPath))) {
                return new HashMap<>();
            }

            final byte[] readBytes = Files.readAllBytes(pluginPath);
            final String bytesAsString = new String(readBytes);

            return createMapFromString(bytesAsString);
        } catch (Exception ex) {
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    public String convertMapIntoString(final Map<String, String> objects) {
        return new Gson().toJson(objects);
    }

    private Map<String, String> createMapFromString(final String mapToBe) {
        final Type type = new TypeToken<Map<String, String>>(){}.getType();
        return new Gson().fromJson(mapToBe, type);
    }

    public void replacePreviousSessionWithCurrent(final String subPluginName) {
        try {
            if (!Files.exists(getPathFromPlugin(subPluginName, CURRENT_SESSION))) return;
            Files.copy(getPathFromPlugin(subPluginName, CURRENT_SESSION), getPathFromPlugin(subPluginName, PREVIOUS_SESSION), StandardCopyOption.REPLACE_EXISTING);

            if (Files.exists(getPathFromPlugin(subPluginName, SHOULD_BACKUP_FILE))) {
                backupPreviousSession(subPluginName);
            }
        } catch (Exception ex) {
            Core.LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    private void backupPreviousSession(final String subPluginName) {
        try {
            makeFolderIfNotExists(getPathFromPlugin(subPluginName, BACKUP_FOLDER));

            final Path previousSessionPath = getPathFromPlugin(subPluginName, PREVIOUS_SESSION);
            if (!Files.exists(previousSessionPath)) return;
            final Path backupFolderPath = getPathFromPlugin(subPluginName, BACKUP_FOLDER);

            Files.copy(previousSessionPath, Paths.get(backupFolderPath.toString(), getNewBackupName()));
        } catch (Exception ex) {
            Core.LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    private void makeFolderIfNotExists(final Path folderPath) {
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (Exception ex) {
                Core.LOGGER.log(Level.SEVERE, ex.getMessage());
                throw new JavaCheckedHissyFitException(ex);
            }
        }
    }

    private Path getPathFromPlugin(final String subPluginName, final FileSystemName fileSystemName) {
        return subPlugsToPaths.get(subPluginName)
                              .get(fileSystemName);
    }

    private String getNewBackupName() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    enum FileSystemName {
        CURRENT_SESSION("CurrentSession.json"),
        PREVIOUS_SESSION("PreviousSession.json"),
        BACKUP_FOLDER("Backups"),
        SHOULD_BACKUP_FILE("ShouldBackup");

        private final String name;

        FileSystemName(final String name) {
            this.name = name;
        }
    }
}
