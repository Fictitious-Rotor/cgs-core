package org.core.cgs.data;

import org.core.cgs.generic.utilities.ExceptionUtils;

import java.nio.file.Path;
import java.util.Map;

class SubPluginFolderInfo {
    private final Map<DataStorageUtil.FileSystemName, Path> folderInfo;

    SubPluginFolderInfo(final Map<DataStorageUtil.FileSystemName, Path> folderInfo) {
        this.folderInfo = folderInfo;
    }

    public Path get(final DataStorageUtil.FileSystemName fileSystemName) {
        return folderInfo.get(fileSystemName);
    }

    @Override
    public String toString() {
        return ExceptionUtils.getNullSafeMessage("SubPluginFolderInfo{folderInfo=%s}", folderInfo);
    }
}
