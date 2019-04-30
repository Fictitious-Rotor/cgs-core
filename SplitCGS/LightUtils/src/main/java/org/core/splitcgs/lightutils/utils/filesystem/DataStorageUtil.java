package org.core.splitcgs.lightutils.utils.filesystem;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dagger.Component;
import org.core.splitcgs.lightutils.ProviderModule;
import org.core.splitcgs.lightutils.metadata.MetadataHandler;

import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.core.splitcgs.lightutils.utils.filesystem.DataStorageUtil.FileSystemName.PREVIOUS_SESSION;

@Component(modules = ProviderModule.class)
@Singleton
public class DataStorageUtil {
    private static final String PLUGIN_NAME = "LightUtils";

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

    private final Map<FileSystemName, Path> folderInfo;

    public DataStorageUtil(final String location) {
        assembleFolderInfo(location);
    }

    private void assembleFolderInfo(final String curPath) {
        folderInfo = Arrays.stream(FileSystemName.values())
                           .reduce(ImmutableMap.<FileSystemName, Path>builder(),
                                   (carry, item) -> carry.put(item, Paths.get(curPath, PLUGIN_NAME, item.name)),
                                   null /* Not used */);
    }

    public Map<String, String> getPreviousSession(final String subPluginName) {
        try {
            final Path pluginPath = folderInfo.get(PREVIOUS_SESSION);

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

    public void storeCurrentSession(final Map<String, String> mapToWrite, final String subPluginName) {

    }

    private void makeFolderIfNotExists(final Path folderPath) {
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (Exception ex) {
                throw new JavaCheckedHissyFitException(ex);
            }
        }
    }
}
