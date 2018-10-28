package net.core.cgs.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.core.cgs.Core;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

public class DataStorageUtil {
    private static final String CURRENT_SESSION_NAME = "CurrentSession.json";
    private static final String STANDARD_FILE_NAME = "InfiniteChestsStore.json";
    private static final String BACKUP_FOLDER_NAME = "Backups";

    private final Path CURRENT_SESSION_FILE_FULL_LOCATION;
    private final Path STANDARD_FILE_FULL_LOCATION;
    private final Path BACKUP_FOLDER_FULL_LOCATION;

    private final Core PLUGIN;

    public DataStorageUtil(Core instance) {
        PLUGIN = instance;

        final String curPath = PLUGIN.getDataFolder().getAbsolutePath();

        CURRENT_SESSION_FILE_FULL_LOCATION = Paths.get(curPath, CURRENT_SESSION_NAME);
        STANDARD_FILE_FULL_LOCATION = Paths.get(curPath, STANDARD_FILE_NAME);
        BACKUP_FOLDER_FULL_LOCATION = Paths.get(curPath, BACKUP_FOLDER_NAME);
    }

    public void storeCurrentSession(Map<String, String> mapToWrite) {
        try {
            Files.write(CURRENT_SESSION_FILE_FULL_LOCATION, convertMapIntoString(mapToWrite).getBytes());
        } catch (Exception ex) {
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    public Map<String, String> getStdFile() {
        try {
            if (!Files.exists(STANDARD_FILE_FULL_LOCATION)) return null;

            return createMapFromString(new String(Files.readAllBytes(STANDARD_FILE_FULL_LOCATION)));
        } catch (Exception ex) {
            PLUGIN.getLogger().log(Level.SEVERE, ex.getMessage());
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    public String convertMapIntoString(Map<String, String> objects) {
        return new Gson().toJson(objects);
    }

    public Map<String, String> createMapFromString(String string) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return new Gson().fromJson(string, type);
    }

    public void replaceStdFileWithCurrentSession() {
        try {
            if (!Files.exists(CURRENT_SESSION_FILE_FULL_LOCATION)) return;
            Files.copy(CURRENT_SESSION_FILE_FULL_LOCATION, STANDARD_FILE_FULL_LOCATION, StandardCopyOption.REPLACE_EXISTING);

            backupStdFile();
        } catch (Exception ex) {
            PLUGIN.getLogger().log(Level.SEVERE, ex.getMessage());
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    private void backupStdFile() {
        try {
            makeBackupFolderIfNotExists();

            if (!Files.exists(STANDARD_FILE_FULL_LOCATION)) return;
            Files.copy(STANDARD_FILE_FULL_LOCATION, Paths.get(BACKUP_FOLDER_FULL_LOCATION.toString(), getNewBackupName()));
        } catch (Exception ex) {
            PLUGIN.getLogger().log(Level.SEVERE, ex.getMessage());
            throw new JavaCheckedHissyFitException(ex);
        }
    }

    private void makeBackupFolderIfNotExists() {
        if (!Files.exists(BACKUP_FOLDER_FULL_LOCATION)) {
            try {
                Files.createDirectory(BACKUP_FOLDER_FULL_LOCATION);
            } catch (Exception ex) {
                PLUGIN.getLogger().log(Level.SEVERE, ex.getMessage());
                throw new JavaCheckedHissyFitException(ex);
            }
        }
    }

    private String getNewBackupName() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}
