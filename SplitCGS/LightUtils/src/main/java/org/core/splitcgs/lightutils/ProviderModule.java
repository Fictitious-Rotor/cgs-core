package org.core.splitcgs.lightutils;

import dagger.Module;
import dagger.Provides;
import org.core.splitcgs.lightutils.listeners.ShovelListener;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;
import org.core.splitcgs.lightutils.utils.filesystem.DataStorageUtil;

import javax.inject.Singleton;

@Module
public class ProviderModule {
    @Provides @Singleton static LightLocationStoredMH provideLLSMH() {
        return new LightLocationStoredMH();
    }

    @Provides @Singleton static CuboidSelectionSessionMH provideCSSMH() {
        return new CuboidSelectionSessionMH();
    }

    @Provides @Singleton static ShovelListener provideSL() {
        return new ShovelListener();
    }

    @Provides @Singleton static DataStorageUtil provideDSU() {
        return new
    }
}
