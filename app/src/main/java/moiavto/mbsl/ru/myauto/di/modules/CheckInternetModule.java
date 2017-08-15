package moiavto.mbsl.ru.myauto.di.modules;

import dagger.Module;
import dagger.Provides;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;

import javax.inject.Singleton;

/**
 * Created by Fedor on 10.04.2017.
 */

@Module
public class CheckInternetModule {
    @Singleton
    @Provides
    CheckInternetService provideCheckInternetService(NetworkDataProvider provider, DataManager dataManager) {
        return new CheckInternetService(provider, dataManager);
    }
}