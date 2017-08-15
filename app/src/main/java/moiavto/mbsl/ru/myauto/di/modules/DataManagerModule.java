package moiavto.mbsl.ru.myauto.di.modules;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.common.SharedPrefsHelper;

import javax.inject.Singleton;

/**
 * Created by Fedor on 22.06.2017.
 */

@Module(includes = {SharePrefsHelperModule.class})
public class DataManagerModule {
    @Provides
    @Singleton
    public DataManager provideDataManager(SharedPrefsHelper sharedPrefsHelper, Gson gson) {
        return new DataManager(sharedPrefsHelper, gson);
    }
}
