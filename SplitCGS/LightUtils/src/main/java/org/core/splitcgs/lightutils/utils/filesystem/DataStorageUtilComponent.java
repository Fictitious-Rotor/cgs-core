package org.core.splitcgs.lightutils.utils.filesystem;

import dagger.BindsInstance;
import dagger.Component;
import org.core.splitcgs.lightutils.ProviderModule;

@Component(modules = ProviderModule.class)
public interface DataStorageUtilComponent {
    DataStorageUtil get();

    @Component.Builder
    interface Builder {
        @BindsInstance Builder folderLocation(@FolderLocation String folderLocation);
        DataStorageUtil build();
    }
}
