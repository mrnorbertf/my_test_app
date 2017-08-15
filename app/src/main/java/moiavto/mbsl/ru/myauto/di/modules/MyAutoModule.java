package moiavto.mbsl.ru.myauto.di.modules;

import dagger.Module;
import dagger.Provides;
import moiavto.mbsl.ru.myauto.network.MyAutoApi;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;

import javax.inject.Singleton;

/**
 * Created by Fedor on 09.04.2017.
 */
@Module(includes = {ApiModule.class})
public class MyAutoModule {
    @Provides
    @Singleton
    public NetworkDataProvider provideMyAutoService(MyAutoApi authApi) {
        return new NetworkDataProvider(authApi);
    }
}